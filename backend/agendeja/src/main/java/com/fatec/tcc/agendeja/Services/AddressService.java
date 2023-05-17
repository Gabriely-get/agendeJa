package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.Address.Address;
import com.fatec.tcc.agendeja.Entities.Address.City;
import com.fatec.tcc.agendeja.Entities.Address.Neighborhood;
import com.fatec.tcc.agendeja.Entities.Address.State;
import com.fatec.tcc.agendeja.Entities.CompanyBranch;
import com.fatec.tcc.agendeja.Entities.RequestTemplate.CepApi;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.Address.AddressRepository;
import com.fatec.tcc.agendeja.Repositories.Address.CityRepository;
import com.fatec.tcc.agendeja.Repositories.Address.NeighborhoodRepository;
import com.fatec.tcc.agendeja.Repositories.Address.StateRepository;
import com.fatec.tcc.agendeja.Repositories.CompanyBranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService {
    Logger logger = LoggerFactory.getLogger(AddressService.class);
    @Autowired
    public CepApiService cepService;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Autowired
    private CompanyBranchRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(CepApi addressCepApi) {
        State stateObject;
        City cityObject;
        Neighborhood neighborhoodObject;

        try {
//            if (Objects.isNull(addressCepApi.getCep())) {
//                return null;
//            }
            String state = addressCepApi.getUf();
            String city = addressCepApi.getLocalidade();
            String neighborhood = addressCepApi.getBairro();

            if (!this.stateRepository.existsByNameAbbreviation(state))
                stateObject = this.stateRepository.save(new State(state));
            else
                stateObject = this.stateRepository.findByNameAbbreviation(state);

            if (!this.cityRepository.existsByName(city))
                cityObject = this.cityRepository.save(new City(city, stateObject));
            else
                cityObject = this.cityRepository.findByName(city);

            if (!this.neighborhoodRepository.existsByName(neighborhood))
                neighborhoodObject = this.neighborhoodRepository.save(new Neighborhood(neighborhood, cityObject));
            else
                neighborhoodObject = this.neighborhoodRepository.findByName(neighborhood);
// TODO check if address exists by all fields;

            String cepFormat = addressCepApi.getCep().replace("-", "").trim();
            Optional<Address> optionalAddress =
                this.addressRepository.findByCepAndPublicPlaceAndNumberAndComplementAndNeighborhood_Id(
                    cepFormat,
                    addressCepApi.getLogradouro(),
                    addressCepApi.getNumero(),
                    addressCepApi.getComplemento(),
                    neighborhoodObject.getId()
            );

            return optionalAddress.orElseGet(() -> this.addressRepository.save(
                    new Address(
                            cepFormat,
                            addressCepApi.getLogradouro(),
                            addressCepApi.getNumero(),
                            addressCepApi.getComplemento(),
                            neighborhoodObject
                    )
            ));

        } catch (Exception e) {
            logger.error("Error on create address: " + e.getMessage());
            throw new RuntimeException("Error on create address");
        }
    }

    public List<Address> getAll(){
        return (List<Address>) this.addressRepository.findAll();
    }

    public Address getById(Long id) {
        Optional<Address> optionalAddress = this.addressRepository.findById(id);

        if (optionalAddress.isPresent()) {
            return optionalAddress.get();
        }

        throw new NotFoundException("Address does not exists");
    }

    public void updateAddress(Long companyId, Long addressId, CepApi newAddress) {
        Optional<CompanyBranch> optionalCompany = this.companyRepository.findById(companyId);

        try {
            if (optionalCompany.isPresent()) {
                Optional<Address> optionalAddress = this.addressRepository.findById(addressId);

                if (optionalAddress.isPresent()) {
                    CompanyBranch company = optionalCompany.get();

                    if (Objects.equals(company.getAddress().getId(), addressId)) {
                        Address addressToUpdate = optionalAddress.get();

                        if (!Objects.equals(company.getAddress().getCep(), newAddress.getCep())
                                || !Objects.equals(company.getAddress().getComplement(), newAddress.getComplemento())
                                || !Objects.equals(company.getAddress().getNumber(), newAddress.getNumero())
                                || !Objects.equals(company.getAddress().getPublicPlace(), newAddress.getLogradouro())
                                || !Objects.equals(company.getAddress().getNeighborhood().getName(), newAddress.getBairro())
                                || !Objects.equals(company.getAddress().getNeighborhood().getCity().getName(), newAddress.getLocalidade())
                                || !Objects.equals(company.getAddress().getNeighborhood().getCity().getState().getNameAbbreviation(), newAddress.getUf())
                        ) {

                            if (this.companyRepository.countAllByAddress_Id(company.getAddress().getId()) > 1) {
                                Address addressCreated = this.createAddress(newAddress);
                                company.setAddress(addressCreated);
                                logger.info("\u001B[32m" + "Address " + addressId + " updated by creation" + "\u001B[0m");
                            } else {
                                Neighborhood newNeighborhood = this.neighborhoodRepository.findByName(newAddress.getBairro());

                                String cepFormat= newAddress.getCep().replace("-", "").trim();
                                addressToUpdate.setCep(cepFormat);
                                addressToUpdate.setPublicPlace(newAddress.getLogradouro());
                                addressToUpdate.setComplement(newAddress.getComplemento());
                                addressToUpdate.setNumber(newAddress.getNumero());
                                addressToUpdate.setNeighborhood(newNeighborhood);
                                this.addressRepository.save(addressToUpdate);

                                company.setAddress(addressToUpdate);
                                logger.info("\u001B[32m" + "Address " + addressId + " updated by update" + "\u001B[0m");
                            }
                            this.companyRepository.save(company);
                            return;

                        }
                        logger.info("\u001B[32m" + "Nothing to do on Address " + addressId + "\u001B[0m");
                        return;
                    }
                    throw new IllegalArgumentException("An error occurred! The address does not belong to the company!");
                }
                throw new IllegalArgumentException("Address does not exists! So newAddress can not be updated");
            }
            throw new IllegalArgumentException("Company does not exists! So newAddress can not be updated");
        } catch (Exception e) {
            logger.error("Error on create newAddress: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteAddress(Long id) {
        if (this.addressRepository.existsById(id)) {
            this.addressRepository.deleteById(id);
        }
    }
}

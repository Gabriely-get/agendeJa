package com.fatec.tcc.agendeja.Repositories.Address;

import com.fatec.tcc.agendeja.Entities.Address.Address;
import com.fatec.tcc.agendeja.Entities.Address.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findByCepAndPublicPlaceAndNumberAndComplementAndNeighborhood_Id(
            String cep,
            String publicPlace,
            String number,
            String complement,
            Long neighborhoodId
    );
}

package com.fatec.tcc.agendify.Repositories.Address;

import com.fatec.tcc.agendify.Entities.Address.Address;
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

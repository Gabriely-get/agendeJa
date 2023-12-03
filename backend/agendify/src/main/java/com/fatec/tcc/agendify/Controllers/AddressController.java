package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.Address.Address;
import com.fatec.tcc.agendify.Entities.RequestTemplate.CepApi;
import com.fatec.tcc.agendify.Services.AddressService;
import com.fatec.tcc.agendify.Services.CepApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private CepApiService cepService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getAddress(@PathVariable("id") Long id) {
        try {
            Address address = this.addressService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(address).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getAll() {
        try {
            List<Address> addresses = this.addressService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(addresses).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by/{cep}")
    public ResponseEntity<ObjectNode> getAddressDetailsByCep(@PathVariable("cep") String cep) {
        try {
            CepApi user = this.cepService.getAddressByCep(cep);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(user).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createAddress(@RequestBody CepApi address) {
        try {
            Address address1 = this.addressService.createAndGetAddress(address);
            System.out.println(address1);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{company_id}/{address_id}")
    public ResponseEntity<ObjectNode> updateAddress(
            @PathVariable("company_id") Long companyId,
            @PathVariable("address_id") Long addressId,
            @RequestBody CepApi newAddress
    ) {
        try {
            this.addressService.updateAddress(companyId, addressId, newAddress);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteAddress(@PathVariable("id") Long id) {
        try {
            this.addressService.deleteAddress(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}

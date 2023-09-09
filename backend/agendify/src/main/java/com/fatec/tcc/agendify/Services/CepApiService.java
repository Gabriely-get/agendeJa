package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.Entities.RequestTemplate.CepApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class CepApiService {
    Logger logger = LoggerFactory.getLogger(CepApiService.class);

    @Value("${cep.api.uri}")
    private String cepApiUri;

    public CepApi getAddressByCep(String cep) {
        if (!cep.matches("[0-9]+")) {
            throw new  IllegalArgumentException("Invalid cep! Please only numbers.");
        }
        String url = this.cepApiUri + "/" + cep + "/json";

        try {
            ResponseEntity<CepApi> response =
                    new RestTemplate().getForEntity(url, CepApi.class, 1);

            if(response.getStatusCode() != HttpStatus.OK) {
                logger.error("Error on cep request: " + response.getBody());
                throw new RuntimeException();
            }

            if (Objects.isNull(response.getBody().getCep()))
                throw new RuntimeException();
            else
                return response.getBody();

        } catch (Exception e) {
            logger.error("Error on try cep request: " + e.getMessage());
            throw new RuntimeException("Error! Verify the cep");
        }

    }
}

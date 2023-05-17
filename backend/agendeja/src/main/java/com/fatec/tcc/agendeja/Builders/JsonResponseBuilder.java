package com.fatec.tcc.agendeja.Builders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonResponseBuilder {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode body = null;
    String list = null;
    ObjectNode withJwtResponse = null;
    ObjectNode withBody = null;
    ObjectNode withMessage = null;
    ObjectNode withError = null;
    ObjectNode withoutMessage;
    ObjectNode response;

    public JsonResponseBuilder() {

    }

    public static JsonResponseBuilder builder() {
        return new JsonResponseBuilder();
    }

    public JsonResponseBuilder withBody(Object body) {
        this.body = mapper.valueToTree(body);
        this.withBody = mapper.createObjectNode();
        this.withBody.set("data", this.body);
        this.response = this.withBody;
        return this;
    }

    public JsonResponseBuilder withList(List<?> list) throws JsonProcessingException {
        JsonNode listToJson = mapper.readTree( mapper.writeValueAsString(list) );
        this.withBody = mapper.createObjectNode();
        this.response = this.withBody.set("data", listToJson);
        return this;
    }

    public JsonResponseBuilder withMessage(String message) {
        this.withMessage = mapper.createObjectNode();
        this.withMessage.put("message", message);
        this.response = this.withMessage;
        return this;
    }

    public JsonResponseBuilder withError(String message) {
        this.withError = mapper.createObjectNode();
        this.withError.put("error", message);
        this.response = this.withError;
        return this;
    }

    public JsonResponseBuilder withoutMessage() {
        this.withMessage = null;
        this.response = this.withoutMessage;
        return this;
    }

    public ObjectNode build() {
        return this.response;
    }
}
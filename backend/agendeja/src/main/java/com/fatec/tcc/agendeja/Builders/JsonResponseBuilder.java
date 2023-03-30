package com.fatec.tcc.agendeja.Builders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonResponseBuilder {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode user = null;
    String userList = null;
    ObjectNode withJwtResponse = null;
    ObjectNode withUser = null;
    ObjectNode withMessage = null;
    ObjectNode withError = null;
    ObjectNode withoutMessage;
    ObjectNode response;

    public JsonResponseBuilder() {

    }

    public static JsonResponseBuilder builder() {
        return new JsonResponseBuilder();
    }

    public JsonResponseBuilder withUser(User user) {
        this.user = mapper.valueToTree(user);
        this.withUser = mapper.createObjectNode();
        this.withUser.set("data", this.user);
        this.response = this.withUser;
        return this;
    }

    public JsonResponseBuilder withAllUsers(List<User> user) throws JsonProcessingException {
        JsonNode userListToJson = mapper.readTree( mapper.writeValueAsString(user) );
        this.withUser = mapper.createObjectNode();
        this.response = this.withUser.set("data", userListToJson);
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
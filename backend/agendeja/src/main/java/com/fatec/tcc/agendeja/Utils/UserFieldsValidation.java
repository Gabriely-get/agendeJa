package com.fatec.tcc.agendeja.Utils;

import com.fatec.tcc.agendeja.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendeja.Entities.User;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

@UtilityClass
public class UserFieldsValidation {
    public void userPOJOValidation(User user) {
        validateComuns(user.getEmail(), user.getUsername(), user.getPassword());
        if (Strings.trimToNull(user.getCpf()) == null || user.getCpf().isEmpty() || user.getCpf().isBlank()) {
            throw new IllegalUserArgumentException("CPF is required");
        }
        if (user.getCpf().length() != 11) {
            throw new IllegalUserArgumentException("CPF length is irregular");
        }

    }

    private static void validateComuns(String email, String name, String password) {
        if (Strings.trimToNull(email) == null || email.isEmpty() || email.isBlank()) {
            throw new IllegalUserArgumentException("E-mail is required");
        }
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalUserArgumentException("Name is required");
        }
        if (Strings.trimToNull(password) == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalUserArgumentException("Password is required");
        }
    }

}

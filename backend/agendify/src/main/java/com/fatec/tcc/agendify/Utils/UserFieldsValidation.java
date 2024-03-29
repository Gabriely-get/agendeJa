package com.fatec.tcc.agendify.Utils;

import com.fatec.tcc.agendify.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Entities.User;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.util.Date;

@UtilityClass
public class UserFieldsValidation {
    public void userPOJOValidation(User user) {
        validateComuns(user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getBirthday().toString(),
                user.getPhone());

        if (Strings.trimToNull(user.getCpf()) == null || user.getCpf().isEmpty() || user.getCpf().isBlank()) {
            throw new IllegalUserArgumentException("CPF is required");
        }
        if (user.getCpf().length() != 11) {
            throw new IllegalUserArgumentException("CPF length is irregular");
        }

    }

    public void userPOJOValidation(UserBody user) {
        validateComuns(user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getBirthday().toString(),
                user.getPhone());

        if (Strings.trimToNull(user.getCpf()) == null || user.getCpf().isEmpty() || user.getCpf().isBlank()) {
            throw new IllegalUserArgumentException("CPF is required");
        }
        if (user.getCpf().length() != 11) {
            throw new IllegalUserArgumentException("CPF length is irregular");
        }

    }

    private static void validateComuns(String email, String name, String lastName, String password, String birthday, String phone) {
        if (Strings.trimToNull(email) == null || email.isEmpty() || email.isBlank()) {
            throw new IllegalUserArgumentException("E-mail is required");
        }
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalUserArgumentException("First name is required");
        }
        if (name.length() < 2) throw new IllegalUserArgumentException("Invalid username length!");
        if (Strings.trimToNull(lastName) == null || lastName.isEmpty() || lastName.isBlank()) {
            throw new IllegalUserArgumentException("Last name is required");
        }
        if (Strings.trimToNull(password) == null || password.isEmpty() || password.isBlank()) {
            throw new IllegalUserArgumentException("Password is required");
        }
        if (birthday == null || birthday.toString().isEmpty() || birthday.toString().isBlank() || LocalDate.parse(birthday).getYear() > 2008) {
            throw new IllegalUserArgumentException("Birth Day is invalid");
        }

        if (phone.length() != 11) throw new IllegalUserArgumentException("Invalid phone length!");
        if (Strings.trimToNull(phone) == null || phone.isBlank()) {
            throw new IllegalUserArgumentException("Phone is required");
        }
    }

}

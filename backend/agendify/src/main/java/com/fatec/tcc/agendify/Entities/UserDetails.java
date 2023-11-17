package com.fatec.tcc.agendify.Entities;

public record UserDetails(
        Long id,
        String email,
        String password,
        String firstName,
        String lastName,
        String birthday,
        String cpf,
        String phone,
        Boolean isActive,
        Boolean isJobProvider,
        Role role,
        String profileImage,
        String coverImage
) {
    public UserDetails(User userReturn, String image) {
        this(
                userReturn.getId(),
                userReturn.getEmail(),
                userReturn.getPassword(),
                userReturn.getFirstName(),
                userReturn.getLastName(),
                userReturn.getBirthday().toString(),
                userReturn.getCpf(),
                userReturn.getPhone(),
                userReturn.getIsActive(),
                userReturn.getIsJobProvider(),
                userReturn.getRole(),
                image,
                null
        );
    }

    public UserDetails(User user, String profile, String cover) {
        this(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday().toString(),
                user.getCpf(),
                user.getPhone(),
                user.getIsActive(),
                user.getIsJobProvider(),
                user.getRole(),
                profile,
                cover
        );
    }
}

package com.fatec.tcc.agendify.Infra;

import com.fatec.tcc.agendify.Entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(Role.ADMIN.name());
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/agenda/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/agenda/register").permitAll()
                        //usuario
                        .requestMatchers(HttpMethod.GET,"/agenda/user/").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/agenda/user/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.GET,"/agenda/user/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.DELETE,"/agenda/user/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )

                        //ENDERECO
                        .requestMatchers(HttpMethod.GET,"/agenda/address/").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/agenda/address/by/cep").permitAll()
                        .requestMatchers(HttpMethod.GET,"/agenda/address/*").hasAnyAuthority(
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.POST,"/agenda/address/").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.PUT,"/agenda/address/*").hasAnyAuthority(
                            Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.DELETE,"/agenda/address/*").hasAnyAuthority(
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )

                        // PORTFOLIO JOB
                        .requestMatchers(HttpMethod.GET,"/agenda/userjob/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/agenda/userjob/by/*").permitAll()
                        .requestMatchers(HttpMethod.POST,"/agenda/userjob/").hasAnyAuthority(
                                Role.ENTERPRISE.name(),
                                Role.ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.PUT,"/agenda/userjob/*").hasAnyAuthority(
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.DELETE,"/agenda/userjob/*").hasAnyAuthority(
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )

                        // COMPANY
                        .requestMatchers(HttpMethod.GET,"/agenda/company/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/agenda/company/user/*").permitAll()
                        .requestMatchers(HttpMethod.POST,"/agenda/company/").hasAnyAuthority(
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.POST,"/agenda/company/create").hasAnyAuthority(
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.PUT,"/agenda/company/*").hasAnyAuthority(
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers(HttpMethod.DELETE,"/agenda/company/*").hasAnyAuthority(
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )

                        // PORTFOLIO
                        .requestMatchers(HttpMethod.GET,"/agenda/portfolio/*").permitAll()

                        //CATEGORY
                        .requestMatchers("/agenda/category/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )

                        //SUBCATEGORY
                        .requestMatchers("/agenda/subcategory/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )

                        //JOB
                        .requestMatchers("/agenda/job/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        .requestMatchers("/agenda/hours/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers("/agenda/schedule/").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //o autowried vai ter o que instanciar daqui -> getAuthetc está pegando e assim, retornando o authmanager de fato
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //por ser um bean, o spring sabe que precisa usar este aqui, logo, as senhas ja serao encriptografadas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
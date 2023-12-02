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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@CrossOrigin
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(Role.ADMIN.name());
        return http.cors().and().csrf(AbstractHttpConfigurer::disable)
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
                        .requestMatchers(HttpMethod.GET,"/agenda/address/by/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/agenda/address/*").permitAll()
                        .requestMatchers(HttpMethod.POST,"/agenda/address/").permitAll()
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
                        .requestMatchers(HttpMethod.GET,"/agenda/portfolio/user/*").permitAll()

                        //CATEGORY

                        .requestMatchers(HttpMethod.GET, "/agenda/category/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/agenda/category/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.POST, "/agenda/category/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.PUT, "/agenda/category/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        //SUBCATEGORY
                        .requestMatchers(HttpMethod.GET, "/agenda/subcategory/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/agenda/subcategory/by/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/agenda/subategory/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.POST, "/agenda/subcategory/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )
                        .requestMatchers(HttpMethod.PUT, "/agenda/subcategory/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )


                        //JOB
                        .requestMatchers("/agenda/job/*").hasAnyAuthority(
                                Role.ADMIN.name()
                        )

                        //HOURS
                        .requestMatchers("/agenda/hours/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )

                        //SCHEDULE
                        .requestMatchers("/agenda/schedule/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers("/agenda/schedule/by/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers("/agenda/schedule/portfolio/*").hasAnyAuthority(
                                Role.USER.name(),
                                Role.ADMIN.name(),
                                Role.ENTERPRISE.name()
                        )
                        .requestMatchers("/agenda/schedule/accept/*").hasAnyAuthority(
                                Role.ENTERPRISE.name()
                        )

                        .requestMatchers("/agenda/schedule/confirm/*").hasAnyAuthority(
                                Role.ENTERPRISE.name()
                        )

                        //TOKEN
                        .requestMatchers("/agenda/verify").hasAnyAuthority(
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

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");  // or specify allowed origins
//        config.addAllowedMethod("*");  // or specify allowed methods
//        config.addAllowedHeader("*");  // or specify allowed headers
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    //por ser um bean, o spring sabe que precisa usar este aqui, logo, as senhas ja serao encriptografadas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

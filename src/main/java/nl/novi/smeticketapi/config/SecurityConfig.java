package nl.novi.smeticketapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Master data management, users & roles (ADMIN only)
                        .requestMatchers(HttpMethod.POST, "/categories/**", "/courses/**", "/tags/**", "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories/**", "/courses/**", "/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories/**", "/courses/**", "/tags/**", "/users/**", "/tickets/**").hasRole("ADMIN")

                        // Ticket updates & notes (SME & ADMIN)
                        .requestMatchers(HttpMethod.PATCH, "/tickets/**").hasAnyRole("SME", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/tickets/*/tags").hasAnyRole("SME", "ADMIN")
                        .requestMatchers("/tickets/*/notes/**").hasAnyRole("SME", "ADMIN")

                        // Ticket creation & attachment upload (STUDENT only)
                        .requestMatchers(HttpMethod.POST, "/tickets").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.POST, "/tickets/*/attachments").hasRole("STUDENT")

                        // Tags read access (SME & ADMIN only)
                        .requestMatchers(HttpMethod.GET, "/tags/**").hasAnyRole("SME", "ADMIN")

                        // Read access (dropdowns, tickets)
                        .requestMatchers(HttpMethod.GET, "/categories/**", "/courses/**", "/tickets/**").authenticated()

                        // Catch-all fallback
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(new org.springframework.core.convert.converter.Converter<org.springframework.security.oauth2.jwt.Jwt, java.util.Collection<org.springframework.security.core.GrantedAuthority>>() {
            @Override
            public java.util.Collection<org.springframework.security.core.GrantedAuthority> convert(org.springframework.security.oauth2.jwt.Jwt jwt) {
                java.util.Collection<org.springframework.security.core.GrantedAuthority> grantedAuthorities = new java.util.ArrayList<>();
                java.util.List<String> roles = getAuthorities(jwt);

                for (String role : roles) {
                    grantedAuthorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role));
                }
                return grantedAuthorities;
            }

            private java.util.List<String> getAuthorities(org.springframework.security.oauth2.jwt.Jwt jwt) {
                java.util.Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
                if (resourceAccess != null) {
                    if (resourceAccess.get("sme-ticket-client") instanceof java.util.Map) {
                        java.util.Map<String, Object> client = (java.util.Map<String, Object>) resourceAccess.get("sme-ticket-client");
                        if (client != null && client.containsKey("roles")) {
                            return (java.util.List<String>) client.get("roles");
                        }
                    }
                }
                return new java.util.ArrayList<>();
            }
        });

        return converter;
    }
}
package cgg.smartcontactmanager.smartcontactmanager.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import cgg.smartcontactmanager.smartcontactmanager.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    public CustomUserDetailsService service;

    @Bean
    SecurityFilterChain getSecurityFilterchain(HttpSecurity hs) throws Exception {

        hs.csrf(s -> s.disable()).authorizeHttpRequests(
                a -> a.requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/users/**").hasRole("USER")
                        .requestMatchers("/**").permitAll())
                .formLogin(
                        login -> login.loginPage("/login").loginProcessingUrl("/dologin")
                                .defaultSuccessUrl("/users/index"))

                .logout(logout -> logout.logoutUrl("/loggout").logoutSuccessUrl("/login?logout"));
        // .invalidateHttpSession(true ).deleteCookies("JSESSIONID"));

        return hs.build();

    }

    @Bean
    UserDetailsService getUserdetailsService() {

        // UserDetails userDetails =
        // User.withUsername("din").password(getencoder().encode("add")).roles("USER").build();

        // InMemoryUserDetailsManager im = new InMemoryUserDetailsManager(userDetails);

        return service;

    }

    @Bean
    BCryptPasswordEncoder getencoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authenticationprovider() {

        DaoAuthenticationProvider d1 = new DaoAuthenticationProvider();

        d1.setUserDetailsService(service);
        d1.setPasswordEncoder(getencoder());

        return d1;
    }
}

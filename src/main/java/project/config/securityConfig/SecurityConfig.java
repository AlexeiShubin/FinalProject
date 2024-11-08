package project.config.securityConfig;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up security in the application.
 * This class is responsible for configuring web security settings including authentication and authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Creates a {@link SecurityFilterChain} bean that configures HttpSecurity for the application.
     *
     * @param http the HttpSecurity to be configured
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/myUserPage").hasAnyRole("ADMIN", "USER", "DOCTOR")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/doctor/**").hasRole("DOCTOR")
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/", "/entrance", "/registration", "/medicines", "/contactInformation", "/logout").permitAll()
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/entrance")
                        .loginProcessingUrl("/login")
                        .usernameParameter("phone")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/myUserPage")
                        .failureUrl("/entrance"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());
        return http.build();
    }

    /**
     * Provides a {@link UserDetailsServiceImpl} bean that is used for retrieving user details.
     *
     * @return a new instance of UserDetailsServiceImpl
     */
    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Creates an {@link AuthenticationProvider} bean that uses a DaoAuthenticationProvider
     * with a user details service and password encoder.
     *
     * @return a configured DaoAuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Provides an {@link AuthenticationManager} bean for managing authentication.
     *
     * @param authenticationConfiguration the configuration for authentication
     * @return the AuthenticationManager configured from the provided AuthenticationConfiguration
     * @throws Exception if an error occurs while creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a {@link PasswordEncoder} bean that uses BCrypt for password encoding.
     *
     * @return a new instance of BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
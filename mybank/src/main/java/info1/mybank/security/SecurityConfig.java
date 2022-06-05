package info1.mybank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","USER");
        auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("USER");
    }
    
    // @Bean
    // public UserDetailsService userDetailsService() {
        
    //     User.UserBuilder users = User.withDefaultPasswordEncoder();
    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //     manager.createUser(users.username("user").password("1234").roles("USER").build());
    //     manager.createUser(users.username("admin").password("1234").roles("USER", "ADMIN").build());
    //     return manager;      
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin(); //.loginPage("/login");
        http.authorizeRequests().antMatchers("/operations","/consulterCompte").hasRole("USER");
        http.authorizeRequests().antMatchers("/saveOperation").hasRole("ADMIN");
        http.exceptionHandling().accessDeniedPage("/403");
    }
}

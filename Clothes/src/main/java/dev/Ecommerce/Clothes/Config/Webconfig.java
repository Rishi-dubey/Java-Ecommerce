package dev.Ecommerce.Clothes.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;




@Configuration
@EnableWebSecurity
public class Webconfig {

    @Bean
    public SecurityFilterChain chain (HttpSecurity http) throws Exception{

        http.csrf().disable().authorizeHttpRequests(a-> a
                                .requestMatchers("/home").permitAll()
                                .requestMatchers("/home/#").permitAll()
                                .requestMatchers("/product/*").permitAll()
                                .requestMatchers("/add-bag").permitAll()
                                .requestMatchers("/bag").permitAll()
                                .requestMatchers("/update/bag").permitAll()
                                .requestMatchers("/verified").permitAll()
                                .requestMatchers("/checkout").permitAll()
                                .requestMatchers("/delete").permitAll()
                                .requestMatchers("/login/*").permitAll()
                                .requestMatchers("/admin_panel").permitAll()
                                .requestMatchers("/item/*").permitAll()
                                .requestMatchers("/item/delete").permitAll()
                                .requestMatchers("/add-item").permitAll()
//                .requestMatchers("/login/admin").permitAll()
                ).httpBasic(withDefaults())
                .formLogin().loginPage("/login/admin")
                .loginProcessingUrl("/sign-in")
                .defaultSuccessUrl("/home",true)
                .permitAll()
                .failureUrl("/home")
                .and().logout().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/login");

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {


        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}

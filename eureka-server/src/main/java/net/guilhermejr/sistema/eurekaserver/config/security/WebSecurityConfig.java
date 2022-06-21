package net.guilhermejr.sistema.eurekaserver.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${sistema.eureka.login}")
    private String eurekaLogin;

    @Value("${sistema.eureka.senha}")
    private String eurekaSenha;

    @Value("${spring.boot.admin.client.instance.metadata.user.name}")
    private String actuatorLogin;

    @Value("${spring.boot.admin.client.instance.metadata.user.password}")
    private String actuatorSenha;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**").hasRole("ACTUATOR")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(eurekaLogin).password(passwordEncoder().encode(eurekaSenha)).roles("ADMIN")
                .and()
                .withUser(actuatorLogin).password(passwordEncoder().encode(actuatorSenha)).roles("ACTUATOR");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package com.project.tour.config;

import com.project.tour.service.MemberSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{

    private final MemberSecurityService memberSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.

                authorizeHttpRequests().antMatchers("/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**") //application.proper-에 있는 path이름이랑 동일해야함.
                .and()
                .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .csrf().disable().formLogin().usernameParameter("email").loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/")
                .and()	//해당 주소와 일치할 때 로그아웃
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").invalidateHttpSession(true) //true를 주면 세션 자체가 삭제됨

        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {


        return authenticationConfiguration.getAuthenticationManager();
    }



}

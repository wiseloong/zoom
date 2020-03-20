package com.zoom.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@EnableSpringHttpSession
public class ApiSessionSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//	          http
//                           .cors(withDefault());
//	    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().and()
//                .logout().and()
//                .authorizeRequests()
//                .antMatchers("/index.html", "/", "/home", "/login").permitAll()
//                .anyRequest().authenticated().and()
//          .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().authorizeRequests().anyRequest().authenticated();
//    }


//    @Bean
//    public MapSessionRepository sessionRepository() {
//        return new MapSessionRepository(new ConcurrentHashMap<>());
//    }

//    @ConditionalOnBean
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }

//    @Bean
//    public HttpSessionIdResolver httpSessionIdResolver() {
//        return HeaderHttpSessionIdResolver.xAuthToken();
//    }
}

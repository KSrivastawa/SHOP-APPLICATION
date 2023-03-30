package com.shop.config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
public class UserConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .cors().and()
                .csrf().disable()
                   /* .cors().configurationSource( new CorsConfigurationSource() {

                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                            CorsConfiguration cfg = new CorsConfiguration();

                            cfg.setAllowedOrigins(Collections.singletonList("*"));
                            //cfg.setAllowedOrigins(Arrays.asList("http://127.0.0.1/5500", "http://localhost:4500"));
                            //cfg.setAllowedMethods(Arrays.asList("GET", "POST","DELETE","PUT"));
                            cfg.setAllowedMethods(Collections.singletonList("*"));
                            cfg.setAllowCredentials(true);
                            cfg.setAllowedHeaders(Collections.singletonList("*"));
                            cfg.setExposedHeaders(Arrays.asList("Authorization"));
                            //cfg.setMaxAge(3600L);
                            return cfg;

                        }
                    })
                .and()*/
                    .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                    .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(HttpMethod.POST,"/users/register").permitAll()
                    .requestMatchers(HttpMethod.GET,"/users/welcome").hasRole("CUSTOMER")
                    .requestMatchers(HttpMethod.GET,"/users/get","users/shop/getall").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET,"/users/getbygst/{gst_number}").hasRole("SHOP_OWNER")
                    .requestMatchers(HttpMethod.POST, "users/shop/product/add/{shopId}").hasRole("SHOP_OWNER")
                    .requestMatchers(HttpMethod.PUT, "users/shop/update/{shopId}").hasRole("SHOP_OWNER")
                    .requestMatchers(HttpMethod.GET,"/users/get/{user_id}","/users/shop/get/{user_id}","users/rating/{shop_name}").hasAnyRole("ADMIN","SHOP_OWNER","CUSTOMER")
                    .requestMatchers(HttpMethod.PUT,"/users/update/{user_id}").hasAnyRole("ADMIN","SHOP_OWNER","CUSTOMER")
                    .requestMatchers(HttpMethod.DELETE,"/users/delete/{user_id}").hasAnyRole("ADMIN","SHOP_OWNER","CUSTOMER")
                    .requestMatchers(HttpMethod.DELETE,"/users/rating/{user_id}/{shop_id}/{rating_id}").hasAnyRole("SHOP_OWNER","CUSTOMER")
                    .requestMatchers(HttpMethod.DELETE,"/users/shop/delete/{shop_id}").hasRole("SHOP_OWNER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();

        return http
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}

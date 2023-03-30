package com.shop.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt= request.getHeader(SecurityConstants.JWT_HEADER);

        if(jwt != null) {

            try {
                //extracting the word Bearer
                jwt = jwt.substring(7);

                SecretKey key= Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());
                Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String username= String.valueOf(claims.get("username"));
                //System.out.println("username: " + username);
                String role= (String) claims.get("role");
                //System.out.println("role: " + role);

                List<GrantedAuthority> authorities= new ArrayList<>();
                SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(role);
                authorities.add(simpleGrantedAuthority);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                System.out.println("Exception: "+e.getMessage());
                throw new BadCredentialsException("Invalid Token received..");
            }


        }

        filterChain.doFilter(request, response);

    }


    //this time this validation filter has to be executed for all the apis except the /signIn api
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return request.getServletPath().equals("/users/signIn");
    }

    public String userName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return authentication.getName();
    }

}


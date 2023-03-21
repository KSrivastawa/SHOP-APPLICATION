package com.shop.service;

import com.shop.entity.Users;
import com.shop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private String userEmail;
    private UserRepo userRepo;

    @Autowired
    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepo.findByUserEmail(username);
        if(user.isPresent()){
            Users user1 =  user.get();

            List<GrantedAuthority> authorities = new ArrayList<>();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user1.getRole());
            authorities.add(simpleGrantedAuthority);

            userEmail = user1.getUserEmail();

            return new User(user1.getUserEmail(), user1.getUserPassword(), authorities);

        }
        throw new UsernameNotFoundException("User Details not found with this username: "+username);

    }

    public String getUserE(){
        return userEmail;
    }

}

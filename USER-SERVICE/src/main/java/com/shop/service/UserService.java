package com.shop.service;

import com.shop.entity.Users;
import com.shop.exception.UserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    public String registerUser(Users user) throws UserException;
    public Users getUserById(String user_id) throws UsernameNotFoundException, UserException;
    public List<Users> getAllUser() throws UserException;
    public Users updateUserById(String user_id, Users user) throws UserException;
    public String deleteUserById(String user_id) throws UserException;

}

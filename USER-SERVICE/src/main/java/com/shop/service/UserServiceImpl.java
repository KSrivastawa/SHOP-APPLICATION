package com.shop.service;

import com.shop.config.JwtTokenValidatorFilter;
import com.shop.entity.Users;
import com.shop.exception.UserException;
import com.shop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtTokenValidatorFilter jwtTokenValidatorFilter;
    @Autowired
    private UserRepo userRepo;
    @Override
    public String registerUser(Users user) throws UserException {

        Optional<Users> userByEmail = userRepo.findByUserEmail(user.getUserEmail());

        if(userByEmail.isPresent()){
            throw new UserException("User already exists with this email: "+user.getUserEmail());
        } else if (user.getRole().toUpperCase().equals("ROLE_SHOP_OWNER") && user.getShopGstNumber()==null) {
            throw new UserException("Shop GST number is required for shop owner");
        } else if (user.getShopGstNumber()!=null){
            Optional<Users> userByGst = userRepo.findByShopGstNumber(user.getShopGstNumber());
            if(userByGst.isPresent()){
                throw new UserException("Shop already exists with this GST number: "+user.getShopGstNumber());
            }
        } else if (!user.getRole().toUpperCase().equals("ROLE_SHOP_OWNER")) {
            user.setShopGstNumber(null);
        }

        UUID uuid = UUID.randomUUID();
        String user_Id = uuid.toString();
        user.setUserId(user_Id);

        userRepo.save(user);
        return "User registered successfully";

    }

    @Override
    public Users getUserById(String user_id) throws UsernameNotFoundException, UserException {

        String userEmail = jwtTokenValidatorFilter.userName();
        Optional<Users> signedInUser = userRepo.findByUserEmail(userEmail);

        Optional<Users> user = userRepo.findByUserId(user_id);
        if(user.isPresent()){
            if(user.get().getRole().equals("ROLE_SHOP_OWNER") || user.get().getRole().equals("ROLE_ADMIN") || user.get().getRole().equals("ROLE_CUSTOMER")){
                return user.get();
            }
            throw new UserException("You are not authorized to access this user");
        }
        throw new UsernameNotFoundException("User not found with this id: "+user_id);

    }

    @Override
    public List<Users> getAllUser() throws UserException {

        List<Users> allUser = userRepo.findAll();
        if(allUser.isEmpty()){
            throw new UserException("No user found");
        }
        return allUser;

    }

    @Override
    public Users updateUserById(String user_id, Users user) throws UserException {

        Users signedUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();
        Optional<Users> userById = userRepo.findByUserId(user_id);

        if(userById.isPresent()){
            if (signedUser.getUserEmail().equals(userById.get().getUserEmail())){
                Users user1 = userById.get();

                user1.setUserEmail(signedUser.getUserEmail());
                user1.setUserPassword(user.getUserPassword());
                user1.setRole(signedUser.getRole());
                user1.setUserFirstName(user.getUserFirstName());
                user1.setUserLastName(user.getUserLastName());
                user1.setUserMobile(user.getUserMobile());
                user1.setRole(signedUser.getRole());
                user1.setShopGstNumber(signedUser.getShopGstNumber());

                userRepo.save(user1);
                return user1;
            }
            throw new UserException("You are not authorized to update this user");

        }
        throw new UserException("User not found with this id: "+user_id);

    }

    @Override
    public String deleteUserById(String user_id) throws UserException {

        Users signedUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();
        Optional<Users> user = userRepo.findByUserId(user_id);
        if(user.isPresent()){
            if(signedUser.getUserEmail().equals(user.get().getUserEmail())){
                userRepo.delete(user.get());
                return "User deleted successfully";
            }
            throw new UserException("You are not authorized to delete this user");
        }
        throw new UserException("User not found with this id: "+user_id);

    }

}

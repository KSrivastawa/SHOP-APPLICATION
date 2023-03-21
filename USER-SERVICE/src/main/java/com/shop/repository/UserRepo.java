package com.shop.repository;

import com.shop.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {

    public Optional<Users> findByUserId(String user_id);
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByShopGstNumber(String shopGstNumber);

}

package com.shop.repository;

import com.shop.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shops, Integer> {

    public Optional<Shops> findByShopId(String shopId);
    public Optional<Shops> findByShopGstNumber(String shopGstNumber);
    public Optional<Shops> findByShopMobile(String shopMobile);
    public Optional<Shops> findByShopOwnerAadharNumber(String shopOwnerAadharNumber);
    public List<Shops> findByShopCityLocation(String shopCityLocation);
    public List<Shops> findByShopType(String shopType);
    public List<Shops> findByShopCityLocationAndShopType(String shopCityLocation, String shopType);


}

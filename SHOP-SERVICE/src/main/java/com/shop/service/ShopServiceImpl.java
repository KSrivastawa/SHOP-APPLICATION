package com.shop.service;

import com.shop.entity.Shops;
import com.shop.exception.ShopException;
import com.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public String registerShop(Shops shop) throws ShopException {

        Optional<Shops> shopGstNumber = shopRepository.findByShopGstNumber(shop.getShopGstNumber());
        Optional<Shops> shopMobile = shopRepository.findByShopMobile(shop.getShopMobile());
        Optional<Shops> shopAadharNumber = shopRepository.findByShopOwnerAadharNumber(shop.getShopOwnerAadharNumber());

        if(shopGstNumber.isPresent() || shopMobile.isPresent() || shopAadharNumber.isPresent()){
            if(shopGstNumber.isPresent()) {
                throw new ShopException("Shop with GST Number " + shop.getShopGstNumber() + " already exists");
            }else if(shopMobile.isPresent()) {
                throw new ShopException("Shop with Mobile Number " + shop.getShopMobile() + " already exists");
            }else {
                throw new ShopException("Shop with Aadhar Number " + shop.getShopOwnerAadharNumber() + " already exists");
            }
        }

        UUID uuid = UUID.randomUUID();
        String shopId = uuid.toString();
        shop.setShopId(shopId);

        shopRepository.save(shop);
        return "Shop registered successfully";

    }

    @Override
    public List<Shops> getAllShopDetails() throws ShopException {

        List<Shops> shops = shopRepository.findAll();
        if(shops.isEmpty()) {
            throw new ShopException("No shops found");
        }
        return shops;

    }

    @Override
    public Shops getShopDetailsById(String shop_Id) throws ShopException {

        Optional<Shops> shop = shopRepository.findByShopId(shop_Id);
        if(!shop.isPresent()) {
            throw new ShopException("No shop found with id " + shop_Id);
        }
        return shop.get();

    }

    @Override
    public String updateShopDetails(String shop_Id, Shops shop) throws ShopException {

        Optional<Shops> shopDetails = shopRepository.findByShopId(shop_Id);
        if(shopDetails.isPresent()) {
            Shops shops = shopDetails.get();
            shops.setShopName(shops.getShopName());
            shops.setShop_owner_first_name(shop.getShop_owner_first_name());
            shops.setShop_owner_last_name(shop.getShop_owner_last_name());
            shops.setShopGstNumber(shops.getShopGstNumber());
            shops.setAddressLine1(shop.getAddressLine1());
            shops.setShopCityLocation(shop.getShopCityLocation());
            shops.setShopType(shops.getShopType());
            shops.setShopMobile(shop.getShopMobile());
            shops.setShopOwnerAadharNumber(shops.getShopOwnerAadharNumber());
            shopRepository.save(shops);
            return "Shop details updated successfully";
        }
        throw new ShopException("No shop found with id " + shop_Id);

    }

    @Override
    public String deleteShopDetails(String shop_Id) throws ShopException {

        Optional<Shops> shop = shopRepository.findByShopId(shop_Id);
        if(shop.isPresent()) {
            shopRepository.delete(shop.get());
            return "Shop details deleted successfully";
        }
        throw new ShopException("No shop found with id " + shop_Id);

    }

    @Override
    public List<Shops> getAllShopDetailsByCity(String shopCityLocation) throws ShopException {

        List<Shops> shops = shopRepository.findByShopCityLocation(shopCityLocation);
        if(shops.isEmpty()) {
            throw new ShopException("No shops found in " + shopCityLocation);
        }
        return shops;

    }

    @Override
    public List<Shops> getAllShopDetailsByType(String shopType) throws ShopException {

        List<Shops> shops = shopRepository.findByShopType(shopType);
        if(shops.isEmpty()) {
            throw new ShopException("No shops found of type " + shopType);
        }
        return shops;

    }

    @Override
    public Shops getShopDetailsByOwnerGstNumber(String shopGstNumber) throws ShopException {

        Optional<Shops> shop = shopRepository.findByShopGstNumber(shopGstNumber);
        if(!shop.isPresent()) {
            throw new ShopException("No shop found with GST Number " + shopGstNumber);
        }
        return shop.get();

    }

    @Override
    public List<Shops> getAllShopsDetailsByCityAndType(String shopCityLocation, String shopType) throws ShopException {

        List<Shops> shops = shopRepository.findByShopCityLocationAndShopType(shopCityLocation, shopType);
        if(shops.isEmpty()) {
            throw new ShopException("No shops found in " + shopCityLocation + " of type " + shopType);
        }
        return shops;

    }


}

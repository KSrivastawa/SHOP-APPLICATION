package com.shop.service;

import com.shop.entity.Shops;
import com.shop.exception.ShopException;

import java.util.List;

public interface ShopService {

    public String registerShop(Shops shop) throws ShopException;
    public List<Shops> getAllShopDetails() throws ShopException;
    public Shops getShopDetailsById(String shop_Id) throws ShopException;
    public String updateShopDetails(String shop_Id, Shops shop ) throws ShopException;
    public String deleteShopDetails(String shop_Id) throws ShopException;
    public List<Shops> getAllShopDetailsByCity(String shopCityLocation) throws ShopException;
    public List<Shops> getAllShopDetailsByType(String shopType) throws ShopException;
    public Shops getShopDetailsByOwnerGstNumber(String shopGstNumber) throws ShopException;
    public List<Shops> getAllShopsDetailsByCityAndType(String shopCityLocation, String shopType) throws ShopException;


}

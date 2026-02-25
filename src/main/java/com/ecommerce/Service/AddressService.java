package com.ecommerce.Service;

import com.ecommerce.Entity.Address;

import java.util.List;

public interface AddressService {


    Address addAddress(Long userId, Address address);

    List<Address> getAllAddressesByUser(Long userId);

    Address updateAddress(Long addressId, Address address);

    void deleteAddress(Long userId,Long addressId);

    Address getAddressById(Long addressId);
}

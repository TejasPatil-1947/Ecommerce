package com.ecommerce.Service.Impl;

import com.ecommerce.Entity.Address;
import com.ecommerce.Entity.User;
import com.ecommerce.Repository.AddressRepository;
import com.ecommerce.Repository.UserRepository;
import com.ecommerce.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Address addAddress(Long userId, Address address) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public Address updateAddress(Long addressId, Address address) {
        Address exAddress = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found for updating"));
        exAddress.setCity(address.getCity());
        exAddress.setState(address.getState());
        exAddress.setStreet(address.getStreet());
        exAddress.setPincode(address.getPincode());
        exAddress.setCountry(address.getCountry());
        return addressRepository.save(exAddress);
    }

    @Override
    public void deleteAddress(Long userId,Long addressId) {
        Address exAddress = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found for updating"));
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        addressRepository.delete(exAddress);
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Address not found"));
    }
}

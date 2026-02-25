package com.ecommerce.Controller;

import com.ecommerce.Entity.Address;
import com.ecommerce.Service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<Address> addAddress(@PathVariable Long userId, @RequestBody Address address){
        return new ResponseEntity<>(addressService.addAddress(userId,address), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Address>> findByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(addressService.getAllAddressesByUser(userId),HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long userId,@RequestBody Address newAddress){
        return new ResponseEntity<>(addressService.updateAddress(userId,newAddress),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long userId,@PathVariable Long addressId){
        addressService.deleteAddress(userId,addressId);
        return new ResponseEntity<>("Address deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/byId/{addressId}")
    public ResponseEntity<Address> findByAddressId(@PathVariable Long addressId){
        return new ResponseEntity<>(addressService.getAddressById(addressId),HttpStatus.OK);
    }

}

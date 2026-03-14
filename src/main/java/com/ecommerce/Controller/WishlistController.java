package com.ecommerce.Controller;

import com.ecommerce.Entity.WishList;
import com.ecommerce.Service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishListService wishListService;

    @PostMapping("/add/{userId}/{productId}")
    public WishList addProduct(@PathVariable Long userId,
                               @PathVariable Long productId){

        return wishListService.addProductToWishlist(userId, productId);
    }

    @GetMapping("/user/{userId}")
    public WishList getWishlist(@PathVariable Long userId){

        return wishListService.getWishlistByUser(userId);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public WishList removeProduct(@PathVariable Long userId,
                                  @PathVariable Long productId){

        return wishListService.removeProduct(userId, productId);
    }
}

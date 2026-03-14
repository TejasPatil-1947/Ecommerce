package com.ecommerce.Service;

import com.ecommerce.Entity.Product;
import com.ecommerce.Entity.WishList;


public interface WishListService {

    WishList addProductToWishlist(Long userId, Long productId);

    WishList getWishlistByUser(Long userId);

    WishList removeProduct(Long userId, Long productId);
}

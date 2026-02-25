package com.ecommerce.Service;

import com.ecommerce.Entity.Cart;
import com.ecommerce.Entity.CartItem;

import java.util.List;

public interface CartService {

    Cart addToCart(Long userId, Long productId, int quantity);

    List<CartItem> viewCart(Long userId);

    Cart updateQuantity(Long userId, Long productId, int quantity);

    void removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);
}

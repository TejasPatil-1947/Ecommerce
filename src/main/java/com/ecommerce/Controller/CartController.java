package com.ecommerce.Controller;

import com.ecommerce.Entity.Cart;
import com.ecommerce.Entity.CartItem;
import com.ecommerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/user/{userId}/product/{productId}/quantity")
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @PathVariable Long productId,@RequestParam int quantity){
        return new ResponseEntity<>(cartService.addToCart(userId,productId,quantity), HttpStatus.OK );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> viewCart(@PathVariable Long userId){
        return new ResponseEntity<>(cartService.viewCart(userId),HttpStatus.OK);
    }

    @PutMapping("/quantity/user/{userId}/product/{productId}/quantity")
    public ResponseEntity<Cart> updateQuantity(@PathVariable Long userId, @PathVariable Long productId,@RequestParam int quantity){
        return new ResponseEntity<>(cartService.updateQuantity(userId,productId,quantity),HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long userId, @PathVariable Long productId){
        cartService.removeFromCart(userId,productId);
        return new ResponseEntity<>("Product removed from cart",HttpStatus.OK);
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return new ResponseEntity<>("Cart clear successfully",HttpStatus.OK);
    }

}

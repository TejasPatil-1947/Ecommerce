package com.ecommerce.Controller;

import com.ecommerce.Entity.Orders;
import com.ecommerce.Entity.Status;
import com.ecommerce.Service.OrderService;
import org.apache.coyote.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId) throws Exception {

        Map<String, Object> response = orderService.createOrder(userId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestParam Long orderId,
                                           @RequestParam String razorpayPaymentId,
                                           @RequestParam String razorpayOrderId,
                                           @RequestParam String razorpaySignature) {

        String result = orderService.verifyPayment(
                orderId,
                razorpayPaymentId,
                razorpayOrderId,
                razorpaySignature
        );

        return ResponseEntity.ok(result);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getByOrderId(@PathVariable Long orderId)
    {
        return new ResponseEntity<>(orderService.getOrderById(orderId),HttpStatus.OK    );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getByUserId(@PathVariable Long userId)
    {
        return new ResponseEntity<>(orderService.getOrdersByUser(userId),HttpStatus.OK    );
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allOrders")
    public ResponseEntity<List<Orders>> getAllOrders()
    {
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK    );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{orderId}")
    public ResponseEntity<Orders> updateStatus(@PathVariable Long orderId,@RequestParam Status status){
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId,status),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order deleted successfully",HttpStatus.OK);
    }
}

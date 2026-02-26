package com.ecommerce.Controller;

import com.ecommerce.Entity.Orders;
import com.ecommerce.Entity.Status;
import com.ecommerce.Service.OrderService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder/{userId}")
    public ResponseEntity<Orders> createOrder(@PathVariable Long userId){
        return new ResponseEntity<>(orderService.createOrder(userId),HttpStatus.OK);
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


    @GetMapping("/allOrders")
    public ResponseEntity<List<Orders>> getAllOrders()
    {
        return new ResponseEntity<>(orderService.getAllOrders(),HttpStatus.OK    );
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<Orders> updateStatus(@PathVariable Long orderId,@RequestParam Status status){
        return new ResponseEntity<>(orderService.updateOrderStatus(orderId,status),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order deleted successfully",HttpStatus.OK);
    }
}

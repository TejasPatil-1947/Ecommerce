package com.ecommerce.Service.Impl;

import com.ecommerce.Entity.*;
import com.ecommerce.Repository.*;
import com.ecommerce.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository  ordersRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public Orders createOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        Orders order = new Orders();
        order.setDate(LocalDate.now());
        order.setUser(user);
        order.setStatus(Status.PLACED);

        Orders savedOrder = ordersRepository.save(order);

        double totalAmount = 0;
        for(CartItem ci : cart.getCartItems()){
            OrderItem oi = new OrderItem();
            oi.setOrders(savedOrder);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());

            double price = ci.getProduct().getPrice();
            totalAmount += price * ci.getQuantity();
            orderItemRepository.save(oi);
        }
        savedOrder.setTotalAmount(totalAmount);
        ordersRepository.save(savedOrder);

        cartItemRepository.deleteAll(cart.getCartItems());

        return savedOrder;
    }

    @Override
    public Orders getOrderById(Long orderId) {
        return ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found with id: "+orderId));
    }

    @Override
    public List<Orders> getOrdersByUser(Long userId) {
        return ordersRepository.findByUserId(userId);
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public Orders updateOrderStatus(Long orderId, Status status) {
        Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setStatus(status);
        return ordersRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found with id"));
        ordersRepository.delete(orders);
    }
}

package com.ecommerce.Service;

import com.ecommerce.Entity.Orders;
import com.ecommerce.Entity.Status;

import java.util.List;

public interface OrderService {

    Orders createOrder(Long userId);

    Orders getOrderById(Long orderId);

    List<Orders> getOrdersByUser(Long userId);

    List<Orders> getAllOrders();

    Orders updateOrderStatus(Long orderId, Status status);

    void deleteOrder(Long orderId);
}

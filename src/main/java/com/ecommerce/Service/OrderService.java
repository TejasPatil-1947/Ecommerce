package com.ecommerce.Service;

import com.ecommerce.Entity.Orders;
import com.ecommerce.Entity.Status;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Map<String, Object> createOrder(Long userId) throws RazorpayException;
    Orders getOrderById(Long orderId);

    List<Orders> getOrdersByUser(Long userId);

    List<Orders> getAllOrders();

    Orders updateOrderStatus(Long orderId, Status status);

    void deleteOrder(Long orderId);
    String verifyPayment(Long orderId,
                                String razorpayPaymentId,
                                String razorpayOrderId,
                                String razorpaySignature);

    Orders cancelOrder(Long orderId);
}

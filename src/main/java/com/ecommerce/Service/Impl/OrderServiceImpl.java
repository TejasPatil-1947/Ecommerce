    package com.ecommerce.Service.Impl;

    import com.ecommerce.Entity.*;
    import com.ecommerce.Repository.*;
    import com.ecommerce.Service.OrderService;
    import com.razorpay.RazorpayException;
    import jakarta.transaction.Transactional;
    import org.json.JSONObject;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Service
    public class OrderServiceImpl implements OrderService {

        @Autowired
        private OrdersRepository  ordersRepository;
        @Autowired
        private OrderItemRepository orderItemRepository;
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private CartItemRepository cartItemRepository;

        @Autowired
        private RazorpayService razorpayService;

        @Autowired
        private PaymentRepository paymentRepository;

//        @Override
//        public Orders createOrder(Long userId) {
//            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//            Cart cart = user.getCart();
//
//            Orders order = new Orders();
//            order.setDate(LocalDate.now());
//            order.setUser(user);
//            order.setStatus(Status.PLACED);
//
//            Orders savedOrder = ordersRepository.save(order);
//
//            double totalAmount = 0;
//            for(CartItem ci : cart.getCartItems()){
//                OrderItem oi = new OrderItem();
//                oi.setOrders(savedOrder);
//                oi.setProduct(ci.getProduct());
//                oi.setQuantity(ci.getQuantity());
//
//                double price = ci.getProduct().getPrice();
//                totalAmount += price * ci.getQuantity();
//                orderItemRepository.save(oi);
//            }
//            savedOrder.setTotalAmount(totalAmount);
//            ordersRepository.save(savedOrder);
//
//            cartItemRepository.deleteAll(cart.getCartItems());
//
//            return savedOrder;
//        }

        @Override
        @Transactional
        public Map<String, Object> createOrder(Long userId) throws RazorpayException {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Cart cart = user.getCart();

            if (cart.getCartItems().isEmpty()) {
                throw new RuntimeException("Cart is empty");
            }

            Orders order = new Orders();
            order.setDate(LocalDate.now());
            order.setUser(user);
            order.setStatus(Status.PLACED);

            Orders savedOrder = ordersRepository.save(order);

            double totalAmount = 0;

            for (CartItem ci : cart.getCartItems()) {
                OrderItem oi = new OrderItem();
                oi.setOrders(savedOrder);
                oi.setProduct(ci.getProduct());
                oi.setQuantity(ci.getQuantity());

                totalAmount += ci.getProduct().getPrice() * ci.getQuantity();
                orderItemRepository.save(oi);
            }

            savedOrder.setTotalAmount(totalAmount);
            ordersRepository.save(savedOrder);

            // 🔥 Create Razorpay order
            JSONObject razorpayOrder = razorpayService.createRazorpayOrder(totalAmount);

            // 🔥 Save payment entity
            Payment payment = new Payment();
            payment.setOrders(savedOrder);
            payment.setRazorpayOrderId(razorpayOrder.getString("id"));
            payment.setAmount(totalAmount);
            payment.setStatus(PaymentStatus.CREATED);

            paymentRepository.save(payment);

            // 🔥 Return Map instead of JSONObject
            Map<String, Object> response = new HashMap<>();
            response.put("orderId", savedOrder.getId());
            response.put("razorpayOrderId", razorpayOrder.getString("id"));
            response.put("amount", totalAmount);
            response.put("key", razorpayService.getKeyId());

            return response;
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
        @Transactional
        public String verifyPayment(Long orderId,
                                    String razorpayPaymentId,
                                    String razorpayOrderId,
                                    String razorpaySignature) {

            Orders order = ordersRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            Payment payment = paymentRepository
                    .findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            boolean isValid = razorpayService.verifySignature(
                    razorpayPaymentId,
                    razorpayOrderId,
                    razorpaySignature
            );

            if (isValid) {

                payment.setRazorpayPaymentId(razorpayPaymentId);
                payment.setRazorpaySignature(razorpaySignature);
                payment.setStatus(PaymentStatus.PAID);

                order.setStatus(Status.SHIPPED);

                paymentRepository.save(payment);
                ordersRepository.save(order);

                // 🔥 CLEAR CART ONLY AFTER SUCCESS
                Cart cart = order.getUser().getCart();
                cartItemRepository.deleteAll(cart.getCartItems());

                return "Payment Successful";

            } else {

                payment.setStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);

                return "Payment Failed";
            }
        }
    }

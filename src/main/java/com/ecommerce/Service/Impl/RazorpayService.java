package com.ecommerce.Service.Impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.secret}")
    private String secret;

    private RazorpayClient razorpayClient;

    @PostConstruct
    public void init() throws Exception {
        razorpayClient = new RazorpayClient(key, secret);
    }


    public JSONObject createRazorpayOrder(double amount) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(key, secret);

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // in paise
        options.put("currency", "INR");
        options.put("payment_capture", 1);

        Order order = client.Orders.create(options);
        return new JSONObject(order.toString()); // convert to JSONObject
    }
    public RazorpayClient getClient() throws RazorpayException {
        return new RazorpayClient(key, secret);
    }
    public String getKeyId() {
        return key;
    }

    public boolean verifySignature(String razorpayPaymentId, String razorpayOrderId, String razorpaySignature) {
        try {
            JSONObject params = new JSONObject();
            params.put("razorpay_order_id", razorpayOrderId);
            params.put("razorpay_payment_id", razorpayPaymentId);
            params.put("razorpay_signature", razorpaySignature); // include signature here

            return Utils.verifyPaymentSignature(params, secret); // only 2 arguments
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
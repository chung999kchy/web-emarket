/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import entity.*;
import cart.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author CHUNG
 */
public class OrderManager {

    private CategoryBLL categoryBLL;
    private ProductBLL productBLL;
    private ProductDetailBLL productDetailBLL;
    private CustomerBLL customerBLL;
    private CustomerOrderBLL customerOrderBLL;
    private OrderedProductBLL orderedProductBLL;

    Connection conn = DAO.connectDB.getConnection();

    private Customer addCustomer(String name, String email, String phone,
            String address, String cityRegion, String ccNumber){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);

        customerBLL.create(customer);
        return customer;
    }

    private CustomerOrder addOrder(Customer customer, ShoppingCart cart) {
        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer.getCustomerId());
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));
        Random random = new Random();
        int i = random.nextInt(999999999);
        order.setConfirmationNumber(i);
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        order.setDateCreated(df.format(new Date()));

        customerOrderBLL.create(order);
        return order;
    }

    public void addOrderItems(CustomerOrder order, ShoppingCart cart) {
        List<ShoppingCartItem> items = cart.getItem();
        for (ShoppingCartItem scItem : items) {
            int productId = scItem.getProduct().getProductId();

            OrderedProductPK orderedProductPK = new OrderedProductPK();
            orderedProductPK.setOrderId(order.getOrderId());
            orderedProductPK.setProductId(productId);

            OrderedProduct orderedItem = new OrderedProduct(orderedProductPK);
            orderedItem.setQuantity(scItem.getQuantity());
            orderedProductBLL.create(orderedItem);
        }
    }

    public int placeOrder(String name, String email, String phone,
            String address, String cityRegion, String ccNumber, ShoppingCart cart){
            Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderItems(order, cart);
            return order.getOrderId();
    }

    public Map getOrderDetails(int orderId) {
        Map orderMap = new HashMap();
        CustomerOrder order = customerOrderBLL.find(orderId);
        Customer customer = customerBLL.find(order.getOrderId());
        List<OrderedProduct> orderedProducts = orderedProductBLL.findByOrderId(orderId);
        List<Product> products = new ArrayList<Product>();
        for (OrderedProduct op : orderedProducts) {
            Product p = productBLL.find(op.getProductId());
            products.add(p);
        }
        
        orderMap.put("orderRecord", order);
        orderMap.put("customer", customer);
        orderMap.put("orderedProducts", orderedProducts);
        orderMap.put("products", products);
        return orderMap;
    }

}

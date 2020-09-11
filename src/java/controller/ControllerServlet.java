/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bll.*;
import cart.ShoppingCart;
import entity.Category;
import entity.Customer;
import entity.Product;
import entity.ProductDetail;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author CHUNG
 */
@WebServlet(name = "ControllerServlet",
        loadOnStartup = 1,
        urlPatterns = {"/ControllerServlet",
            "/category",
            "/product",
            "/addToCart",
            "/viewCart",
            "/updateCart",
            "/checkout",
            "/purchase",
            "/chooseLanguage",
            "/login",
            "/logout",
            "/search-box"})

public class ControllerServlet extends HttpServlet {

    CustomerBLL customerBLL = new CustomerBLL();
    ProductBLL productBLL = new ProductBLL();
    CategoryBLL categoryBLL = new CategoryBLL();
    ProductDetailBLL productDetailBLL = new ProductDetailBLL();
    OrderManager orderManager = new OrderManager();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        getServletContext().setAttribute("newProducts", productBLL.getNewProducts(5));
        getServletContext().setAttribute("category", categoryBLL.getCategories());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        //Validator validator = new Validator();

        if (userPath.equals("/category")) {
            String categoryId = request.getQueryString();
            if (categoryId != null) {
                Category selectedCategory;
                List<Product> categoryProducts;
                selectedCategory = categoryBLL.find(Integer.parseInt(categoryId));
                session.setAttribute("selectedCategory", selectedCategory);
                categoryProducts = (List<Product>) productBLL.getCategoryProduct(Integer.parseInt(categoryId));
                session.setAttribute("categoryProducts", categoryProducts);
                RequestDispatcher rt = request.getRequestDispatcher("category.jsp");
                rt.forward(request, response);
            }

        } else if (userPath.equals("/product")) {
            String productId = request.getQueryString();
            if (productId != null) {
                Product selectedProduct;
                ProductDetail selectedProductDetail;
                selectedProduct = productBLL.find(Integer.parseInt(productId));
                selectedProductDetail = productDetailBLL.findProductDetail(Integer.parseInt(productId));
                session.setAttribute("selectedProduct", selectedProduct);
                session.setAttribute("selectedProductDetail", selectedProductDetail);
            }

        } else if (userPath.equals("/viewCart")) {
            String clear = request.getParameter("clear");
            if (clear != null && clear.equals("true")) {
                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                cart.clear();
            }

        } else if (userPath.equals("/addToCart")) {
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart == null) {
                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }
            String productId = request.getQueryString();
            if (!productId.isEmpty()) {
                Product product = (Product) productBLL.find(Integer.parseInt(productId));
                cart.addItem(product);
            }
            String userView = (String) session.getAttribute("view");
            userPath = userView;

        } else if (userPath.equals("/updateCart")) {
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");
            boolean invalidEntry = false;    //validator.validateQuantity(productId, quantity);
            if (!invalidEntry) {
                Product product = (Product) productBLL.find(Integer.parseInt(productId));
                cart.update(product, quantity);
            }
            userPath = "/viewCart";

        } else if (userPath.equals("/purchase")) {
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart != null) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String cityRegion = request.getParameter("cityRegion");
                String ccNumber = request.getParameter("creditcard");
                boolean validationErrorFlag = false;
                validationErrorFlag = true;
                //validator.validateForm(name, email,
                //phone, address, cityRegion, ccNumber, request);
                if (validationErrorFlag) {
                    request.setAttribute("validationErrorFlag", validationErrorFlag);
                    userPath = "/checkout";
                } else {
                    int orderId;
                    orderId = orderManager.placeOrder(name, email, phone,
                            address, cityRegion, ccNumber, cart);
                    if (orderId != 0) {
                        Locale locale = (Locale) session.getAttribute("javax.servlet.jsp.jstl.fmt.locale.session");
                        String language = "";
                        if (locale != null) {
                            language = (String) locale.getLanguage();
                        }
                        cart = null;
                        session.invalidate();
                        if (!language.isEmpty()) {
                            request.setAttribute("language", language);
                        }
                        Map orderMap = orderManager.getOrderDetails(orderId);
                        request.setAttribute("customer", orderMap.get("customer"));
                        request.setAttribute("products", orderMap.get("products"));
                        request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                        request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));
                        userPath = "/confirmation";
                    }
                }
                userPath = "/confirmation";

            } else {
                userPath = "/checkout";
                request.setAttribute("orderFailureFlag", true);
            }
        } else if (userPath.equals("/login")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Customer customer = new Customer();
            customer = customerBLL.findByEmail(email);
            if (customer.getPassword().equals(password) && password != null) {
                userPath = "/index";
                session.setAttribute("loginById", customer);
                System.out.println("Dang nhap thanh cong" + customer);
            } else {
                userPath = "/index";
                session.setAttribute("loginById", null);
                System.out.println("Dang nhap that bai");
            }

        } else if (userPath.equals("/logout")) {
            session.setAttribute("loginById", null);
            userPath = "/index";

        } else if (userPath.equals("/search-box")) {
            String key = request.getParameter("key-search");
            List<Product> products = null;
            if ("".equals(key) || key == null) {
                products = null;
            } else {
                products = productBLL.findBySearch(key);
            }

            session.setAttribute("key-search", key);
            session.setAttribute("result-search", products);
            userPath = "/resultsSearch";
        }
        String url =userPath+".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}

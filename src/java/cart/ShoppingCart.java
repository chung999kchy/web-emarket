/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author CHUNG
 */
public class ShoppingCart {

    List<ShoppingCartItem> items;
    int numberOfItems;
    double total;

    public ShoppingCart() {
        items = new ArrayList<ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    public synchronized void addItem(Product product) {
        boolean newItem = true;
        for (ShoppingCartItem scItem : items) {
            if (scItem.getProduct().getProductId() == product.getProductId()) {
                newItem = false;
                scItem.incrementQuantity();
                break;
            }
        }
        if (newItem) {
            ShoppingCartItem scItem = new ShoppingCartItem(product);
            items.add(scItem);
        }
    }

    public synchronized void update(Product product, String quantity) {
        int qty = -1;
        qty = Integer.parseInt(quantity);
        if (qty >= 0) {
            ShoppingCartItem item = null;
            for (ShoppingCartItem scItem : items) {
                if (scItem.getProduct().getProductId() == product.getProductId()) {
                    if (qty != 0) {
                        scItem.setQuantity(qty);
                    } else {
                        item = scItem;
                        break;
                    }
                }
            }
            if (item != null) {
                items.remove(item);
            }
        }
    }

    public synchronized List<ShoppingCartItem> getItem() {
        return items;
    }

    public synchronized int getNumberOfItems() {
        numberOfItems = 0;
        for (ShoppingCartItem scItem : items) {
            numberOfItems += scItem.getQuantity();
        }
        return numberOfItems;
    }

    public synchronized double getSubtotal() {
        double amount = 0;
        for (ShoppingCartItem scItem : items) {
            Product product = (Product) scItem.getProduct();
            amount += (scItem.getQuantity() * product.getPrice());
        }
        return amount;
    }

    public synchronized void calculateTotal(String surchange) {
        double amount = 0;
        double s = Double.parseDouble(surchange);
        amount = this.getSubtotal();
        amount += s;
        total = amount;
    }

    public synchronized double getTotal() {
        return total;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
        total = 0;
    }
}

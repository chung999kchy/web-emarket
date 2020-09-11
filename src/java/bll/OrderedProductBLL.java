/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import com.mysql.jdbc.PreparedStatement;
import entity.OrderedProduct;
import entity.OrderedProductPK;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author CHUNG
 */
public class OrderedProductBLL {
    private static String addOrderedProductSQL = "insert into ordered_product ( order_id, product_id, quantity)"
            + "values ( ?, ?, ?);";
    
    Connection conn = DAO.connectDB.getConnection();
    
    public List<OrderedProduct> getSQL(String sql){
        try {
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            ArrayList<OrderedProduct> qq = new ArrayList<>();
            while (rs.next()) {
                OrderedProduct p = new OrderedProduct();
                p.setQuantity(rs.getInt("quantity"));
                p.setProductId(rs.getInt("product_id"));
                p.setOrderId(rs.getInt("order_id"));
                qq.add(p);
            }
            return qq;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public void create(OrderedProduct orderedItem){
         try (
            PreparedStatement prep = (PreparedStatement) conn.prepareStatement(addOrderedProductSQL);
            ){
            prep.setInt(1, orderedItem.getOrderId());
            prep.setInt(2, orderedItem.getProductId());
            prep.setInt(3, orderedItem.getQuantity());
            prep.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public List<OrderedProduct> findByOrderId(int id) {
        String sql = "select * from ordered_product where order_id= " + id;
        List<OrderedProduct> qq = new ArrayList<>();
        qq = getSQL(sql);
        return qq;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import DAO.connectDB;
import entity.Category;
import entity.Product;
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
public class ProductBLL {
    Connection conn = DAO.connectDB.getConnection();
    public List<Product> getSQL(String sql){
        try {
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            ArrayList<Product> qq = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setName(rs.getString("name"));
                p.setProductId(rs.getInt("product_id"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getString("image"));
                p.setDescription(rs.getString("description"));
                p.setDescriptionDetail(rs.getString("description_detail"));
                p.setLastUpdate(rs.getDate("last_update"));
                p.setThumbImage(rs.getString("thumb_image"));
                qq.add(p);
            }
            return qq;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public List<Product> getNewProducts(int number) {
        String sql = "select * from product";
        List<Product> qq = new ArrayList<>();
        qq= getSQL(sql);
        return qq;
    }
    
    public List<Product> getCategoryProduct(int id) {
        String sql = "select * from product where category_id="+id;
        List<Product> qq = new ArrayList<>();
        qq= getSQL(sql);
        return qq;
    }
    
    public Product find(int id) {
        String sql = "select * from product where product_id= "+id;
        List<Product> qq = new ArrayList<>();
        qq= getSQL(sql);
        Product p = qq.get(0);
        return p;
    }
    
    public List<Product> findBySearch(String key) {
        String sql = "select * from product where name like '%"+key+"%'";
        List<Product> qq = new ArrayList<>();
        qq= getSQL(sql);  
        return qq;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

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
public class CategoryBLL {

    Connection conn = DAO.connectDB.getConnection();
    
    public List<Category> getSQL(String sql){
        try {
            Statement sttm = conn.createStatement();
            
            ResultSet rs = sttm.executeQuery(sql);
            ArrayList<Category> qq = new ArrayList<>();
            while (rs.next()) {
                Category p = new Category();
                p.setCategoryId(rs.getInt("category_id"));
                p.setImage(rs.getString("image"));
                p.setName(rs.getString("name"));
                qq.add(p);
            }
            return qq;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public List<Category> getCategories() {
        String sql = "select * from category";
        List<Category> qq = getSQL(sql);
        return qq;
    }
    
    public Category find(int id) {
        String sql = "select * from category where category_id ="+id;
        List<Category> qq =getSQL(sql);
        Category p = qq.get(0);
        return p;
    }


}

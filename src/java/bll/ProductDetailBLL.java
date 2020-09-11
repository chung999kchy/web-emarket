/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import entity.Product;
import entity.ProductDetail;
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
public class ProductDetailBLL {

    Connection conn = DAO.connectDB.getConnection();

    public List<ProductDetail> getSQl(String sql) {
        try {
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            ArrayList<ProductDetail> qq = new ArrayList<>();
            while (rs.next()) {
                ProductDetail p = new ProductDetail();
                p.setProductId(rs.getInt("product_id"));
                p.setImage1(rs.getString("image1"));
                p.setImage2(rs.getString("image2"));
                p.setImage3(rs.getString("image3"));
                p.setImage4(rs.getString("image4"));
                p.setImage5(rs.getString("image5"));
                p.setInformation(rs.getString("information"));
                p.setAccessories(rs.getString("accessories"));
                p.setGuaranty(rs.getString("guaranty"));
                qq.add(p);
            }
            return qq;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ProductDetail findProductDetail(int id) {
        String sql = "select * from product_detail where product_id =" + id;
        ArrayList<ProductDetail> qq = new ArrayList<>();
        qq = (ArrayList<ProductDetail>) getSQl(sql);
        ProductDetail p = qq.get(0);
        return p;
    }

}

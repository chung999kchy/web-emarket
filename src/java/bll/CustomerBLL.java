/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import com.mysql.jdbc.PreparedStatement;
import entity.Customer;
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
public class CustomerBLL {
    private static String addCustomerSQL = "insert into customer ( name, email,"
            + " phone, address, city_region, cc_number, password ) values "
            + "( ?, ?, ?, ?, ?, ?, ?);";
    
    Connection conn = DAO.connectDB.getConnection();
    public List<Customer> getSQL(String sql){
        try {
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            ArrayList<Customer> qq = new ArrayList<>();
            while (rs.next()) {
                Customer p = new Customer();
                p.setName(rs.getString("name"));
                p.setCustomerId(rs.getInt("customer_id"));
                p.setEmail(rs.getString("email"));
                p.setAddress(rs.getString("address"));
                p.setCcNumber(rs.getString("cc_number"));
                p.setCityRegion(rs.getString("city_region"));
                p.setPhone(rs.getString("phone"));
                p.setPassword(rs.getString("password"));
                qq.add(p);
            }
            return qq;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public Customer find(int id) {
        String sql = "select * from customer where customer_id= "+id;
        List<Customer> qq = new ArrayList<>();
        qq= getSQL(sql);
        Customer p = qq.get(0);
        return p;
    }
    
    public Customer findByEmail(String email) {
        String sql = "select * from customer where email= '"+ email+" '";
        List<Customer> qq = new ArrayList<>();
        qq= getSQL(sql);
        Customer p = qq.get(0);
        return p;
    }
    
    public void create(Customer customer){
        try (
            PreparedStatement prep = (PreparedStatement) conn.prepareStatement(addCustomerSQL);
            ){
            prep.setString(1, customer.getName());
            prep.setString(2, customer.getEmail());
            prep.setString(3, customer.getPhone());
            prep.setString(4, customer.getAddress());
            prep.setString(5, customer.getCityRegion());
            prep.setString(6, customer.getCcNumber());
            prep.setString(7, customer.getPassword());
            prep.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
            
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bll;

import com.mysql.jdbc.PreparedStatement;
import entity.Customer;
import entity.CustomerOrder;
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
public class CustomerOrderBLL {

    private static String addOrderSQL = "insert into customer_order "
            + "(amount, date_created, confirmation_number, customer_id )"
            + "(?, ?, ?, ?);";
    Connection conn = DAO.connectDB.getConnection();

    public List<CustomerOrder> getSQL(String sql) {
        try {
            Statement sttm = conn.createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            ArrayList<CustomerOrder> qq = new ArrayList<>();
            while (rs.next()) {
                CustomerOrder p = new CustomerOrder();
                p.setAmount(rs.getBigDecimal("amount"));
                p.setOrderId(rs.getInt("order_id"));
                p.setConfirmationNumber(rs.getInt("confirmation_number"));
                p.setDateCreated(rs.getString("date_created"));
                p.setCustomerId(rs.getInt("customer_id"));
                qq.add(p);
            }
            return qq;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public CustomerOrder find(int id) {
        String sql = "select * from customer_order where order_id= " + id;
        List<CustomerOrder> qq = new ArrayList<>();
        qq = getSQL(sql);
        CustomerOrder p = qq.get(0);
        return p;
    }

    public void create(CustomerOrder order) {
        try (
                PreparedStatement prep = (PreparedStatement) conn.prepareStatement(addOrderSQL);) {
            prep.setBigDecimal(1, order.getAmount());
            prep.setString(2, order.getDateCreated());
            prep.setInt(3, order.getConfirmationNumber());
            prep.setInt(4, order.getCustomerId());

            prep.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}


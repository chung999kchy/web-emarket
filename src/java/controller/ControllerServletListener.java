/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import session_bean.CategorySessionBean;
import session_bean.ProductSessionBean;

/**
 * Web application lifecycle listener.
 *
 * @author CHUNG
 */
public class ControllerServletListener implements ServletContextListener {
    CategorySessionBean categorySB;
    ProductSessionBean productSB;
    ServletContext context;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        context.setAttribute("newProducts", productSB.findRange(new int[] {0,5}));
        context.setAttribute("category", categorySB.findAll());
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.context = null;
    }
}

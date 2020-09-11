/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session_bean;

import entity.Product;
//import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author CHUNG
 */
public class ProductSessionBean extends AbstractSessionBean<Product> {

    @PersistenceContext(unitName = "eMarketPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductSessionBean() {
        super(Product.class);
    }
}


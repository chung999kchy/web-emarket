/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session_bean;

import entity.Category;
import javax.persistence.EntityManager;

/**
 *
 * @author CHUNG
 */
public class CategorySessionBean extends AbstractSessionBean<Category>{
    public CategorySessionBean() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

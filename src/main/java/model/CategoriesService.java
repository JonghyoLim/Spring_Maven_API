/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author hyoku
 */
@Service
public class CategoriesService {
    
    
    public Categories findByID(int id) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Categories> tq = em.createNamedQuery("Categories.findById", Categories.class).setParameter("id", id);

        Categories c = null;
        try {
            c = tq.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        return c;
    }
    
}

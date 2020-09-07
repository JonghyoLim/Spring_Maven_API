/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author hyoku
 */
@Service
public class StylesService {
    
    public Styles findByID(int id) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Styles> tq = em.createNamedQuery("Styles.findById", Styles.class).setParameter("id", id);

        Styles s = null;
        try {
            s = tq.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        return s;
    }
    
    
    public List<Styles> getAllStyles() {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Styles> tq = em.createNamedQuery("Styles.findAll", Styles.class);
        System.out.println("Display All Styless");
        List<Styles> styleList = new ArrayList<>();
        try {
            styleList = tq.getResultList();
            if (styleList == null || styleList.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return styleList;
    }
    
    
    public List<Styles> getAllStylesPagination(Integer page, Integer size) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Styles> tq = em.createNamedQuery("Styles.findAllPagination", Styles.class)
                .setMaxResults(page)
                .setFirstResult(page * size);
        
        List<Styles> stylesList = new ArrayList<>();
        try {
            stylesList = tq.getResultList();
            if (stylesList == null || stylesList.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return stylesList;
    }

    
            
    public Boolean addStyle(Styles s) { 
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(s);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    
    
    public Boolean editStyle(Styles s) {
        System.out.println(" -- Styles  -- : edit Style Service!!!!!!!!!!!!!!!!!!!!!!!!!!");
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
   
        try {
            transaction.begin();
            em.merge(s);
            transaction.commit();
            System.out.println("Edited: " + s.getId());

        } catch (Exception e) {
            System.out.println(e);
             return false;
        } finally {

            em.close();
        } 

        return true; 
    }
    
    
    
    public boolean deleteStyle(int id) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Styles> tq = em.createNamedQuery("Styles.findById", Styles.class).setParameter("id", id);
        EntityTransaction trans = em.getTransaction();

        try {
            Styles s = tq.getSingleResult();
            trans.begin();
            System.out.println("Removed: " + s.getStyleName() + ", " + s.getId());
            em.remove(em.merge(s));
            trans.commit();
          
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    
        
    
    
}

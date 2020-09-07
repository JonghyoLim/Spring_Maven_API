/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Service;

/**
 *
 * @author hyoku
 */
@Service
public class BeersService {
    
    public List<Beers> getAllBeers() {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Beers> tq = em.createNamedQuery("Beers.findAll", Beers.class);
        System.out.println("Display All Beers");
        List<Beers> beerList = new ArrayList<>();
        try {
            beerList = tq.getResultList();
            if (beerList == null || beerList.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return beerList;
    }
    
    
    public List<Beers> getAllBeersPagination(Integer page, Integer size) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Beers> tq = em.createNamedQuery("Beers.findAllPagination", Beers.class)
                .setMaxResults(page)
                .setFirstResult(page * size);
        
        List<Beers> beersList = new ArrayList<>();
        try {
            beersList = tq.getResultList();
            if (beersList == null || beersList.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return beersList;
    }

    
    public List<Beers> getBeersSearchByNameResult(String beer) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
    //      name = "Beer.findByNameLikeConcat", query = "SELECT b FROM Beer b WHERE b.name LIKE CONCAT('%, :name,'%')"
        TypedQuery<Beers> tq = em.createNamedQuery("Beers.findByNameLikeConcat", Beers.class).setParameter("name", beer);

        List<Beers> beerList = new ArrayList<>();
        try {
            beerList = tq.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return beerList;
    }



    public Beers findByID(int id) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Beers> tq = em.createNamedQuery("Beers.findById", Beers.class).setParameter("id", id);

        Beers b = null;
        try {
            b = tq.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        return b;
    }


    public Boolean addBeer(Beers b) { 
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            em.persist(b);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            em.close();
        }
        return true;
    }
    
    
    public Boolean editBeer(Beers b) {
        System.out.println(" -- Beers  -- : edit Beer Service!!!!!!!!!!!!!!!!!!!!!!!!!!");
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
   
        try {
            transaction.begin();
            em.merge(b);
            transaction.commit();
            System.out.println("Edited: " + b.getId());

        } catch (Exception e) {
            System.out.println(e);
             return false;
        } finally {

            em.close();
        } 

        return true; 
    }
    
    
    
    public boolean deleteBeer(int id) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Beers> tq = em.createNamedQuery("Beers.findById", Beers.class).setParameter("id", id);
        EntityTransaction trans = em.getTransaction();

        try {
            Beers b = tq.getSingleResult();
            trans.begin();
            System.out.println("Removed: " + b.getName() + ", " + b.getBreweryId());
            em.remove(em.merge(b));
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

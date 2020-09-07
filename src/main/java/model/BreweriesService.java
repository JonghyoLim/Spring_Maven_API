/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author hyoku
 */
@Service
public class BreweriesService {

    public List<Breweries> getAllBrews() {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Breweries> tq = em.createNamedQuery("Breweries.findAll", Breweries.class);
        System.out.println("Display All");
        List<Breweries> breweriesList = new ArrayList<>();
        try {
            breweriesList = tq.getResultList();
            if (breweriesList == null || breweriesList.isEmpty()) {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return breweriesList;
    }
    
    public List<Breweries> getAllBrewsPagination(Integer page, Integer size) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Breweries> tq = em.createNamedQuery("Breweries.findAllPagination", Breweries.class)
                .setMaxResults(page)
                .setFirstResult(page * size);
        
        List<Breweries> breweriesList = new ArrayList<>();
        try {
            breweriesList = tq.getResultList();
            if (breweriesList == null || breweriesList.isEmpty()) {
                return null;
            }
            breweriesList = tq.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }
        return breweriesList;
    }
    
    

    public Breweries findById(int id) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Breweries> tq = em.createNamedQuery("Breweries.findById", Breweries.class).setParameter("id", id);

        Breweries b = null;
        try {
            b = tq.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        return b;
    }
    
      
        
    public Boolean addBrewery(Breweries b) { 
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

    public Boolean editBrewery(Breweries b) {
        System.out.println(" -- Brewery -- : edit Breweries Service!!!!!!!!!!!!!!!!!!!!!!!!!!");
        EntityManager em = DBUtil.getEmf().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
       
        try {
            transaction.begin();
            em.merge(b);
            transaction.commit();
            System.out.println("Edited: " + b.getBreweryId());
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
       
            em.close();
        }
        return true;
    }
    
    

    public Boolean deleteBrewery(int id) {

        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<Breweries> tq = em.createNamedQuery("Breweries.findById", Breweries.class).setParameter("id", id);
        EntityTransaction trans = em.getTransaction();

        try {
            Breweries b = tq.getSingleResult();
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
    
    
   // @Value("${app.sendgrid.templateId}")
    private String templateId = "d-8ddd8bad468549d89d57bf1f504fbd74";
    @Autowired
    SendGrid sendGrid;
    public String sendEmail(String email, int id) {
        
        try{
            Mail mail = prepareMail(email, id);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            if(response != null) {
                System.out.println("Response code from sendgrid " + response.getHeaders());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occured ";
        }
        return "Test for Brewery mail has been sent to you! should firstly check junk or spam inbox ";
    }
    
    
    public Mail prepareMail(String email, int id) {
       
        Mail mail = new Mail();  
        Email fromEmail = new Email();
        //fromEmail.setEmail("hyokun07@gmail.com");
        System.out.println("BreweryID: " + findById(id).getBreweryId());
        fromEmail.setEmail(findById(id).getEmail());
        mail.setFrom(fromEmail);
        
        Email toCustomer = new Email();
        toCustomer.setEmail(email);
        
        Personalization personalization = new Personalization();
        personalization.addTo(toCustomer);
        mail.addPersonalization(personalization);
        
        mail.setTemplateId(templateId);
        
        
        return mail;
    }    
    
    
}

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
public class BreweriesGeoCodeService {
    
    public BreweriesGeocode getBreweryGeocodeById(int id) {
        EntityManager em = DBUtil.getEmf().createEntityManager();
        TypedQuery<BreweriesGeocode> tq = em.createNamedQuery("BreweriesGeocode.findByBreweryId", BreweriesGeocode.class).setParameter("breweryId", id);

        BreweriesGeocode b = null;
        try {
            b = tq.getSingleResult();
            System.out.println("Display GeoCode" + b.getLatitude() + " " + b.getLongitude());
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
        return b;
    }
    
    
}

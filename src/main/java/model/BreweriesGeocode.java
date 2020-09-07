/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author hyoku
 */
@Entity
@Table(name = "breweries_geocode")
@NamedQueries({
    @NamedQuery(name = "BreweriesGeocode.findAll", query = "SELECT b FROM BreweriesGeocode b"),
    @NamedQuery(name = "BreweriesGeocode.findById", query = "SELECT b FROM BreweriesGeocode b WHERE b.id = :id"),
    @NamedQuery(name = "BreweriesGeocode.findByBreweryId", query = "SELECT b FROM BreweriesGeocode b WHERE b.breweryId = :breweryId"),
    @NamedQuery(name = "BreweriesGeocode.findByLatitude", query = "SELECT b FROM BreweriesGeocode b WHERE b.latitude = :latitude"),
    @NamedQuery(name = "BreweriesGeocode.findByLongitude", query = "SELECT b FROM BreweriesGeocode b WHERE b.longitude = :longitude")})
public class BreweriesGeocode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "brewery_id")
    private int breweryId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private float latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private float longitude;

    public BreweriesGeocode() {
    }

    public BreweriesGeocode(Integer id) {
        this.id = id;
    }

    public BreweriesGeocode(Integer id, int breweryId, float latitude, float longitude) {
        this.id = id;
        this.breweryId = breweryId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BreweriesGeocode)) {
            return false;
        }
        BreweriesGeocode other = (BreweriesGeocode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.BreweriesGeocode[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author hyoku
 */
@Entity
@Table(name = "beers")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Beers.findAll", query = "SELECT b FROM Beers b"),
    @NamedQuery(name = "Beers.findAllPagination", query = "SELECT b FROM Beers b ORDER BY b.id"),
    @NamedQuery(name = "Beers.findById", query = "SELECT b FROM Beers b WHERE b.id = :id"),
    @NamedQuery(name = "Beers.findByBreweryId", query = "SELECT b FROM Beers b WHERE b.breweryId = :breweryId"),
    @NamedQuery(name = "Beers.findByName", query = "SELECT b FROM Beers b WHERE b.name = :name"),
    @NamedQuery(name = "Beers.findByCatId", query = "SELECT b FROM Beers b WHERE b.catId = :catId"),
    @NamedQuery(name = "Beers.findByStyleId", query = "SELECT b FROM Beers b WHERE b.styleId = :styleId"),
    @NamedQuery(name = "Beers.findByAbv", query = "SELECT b FROM Beers b WHERE b.abv = :abv"),
    @NamedQuery(name = "Beers.findByIbu", query = "SELECT b FROM Beers b WHERE b.ibu = :ibu"),
    @NamedQuery(name = "Beers.findBySrm", query = "SELECT b FROM Beers b WHERE b.srm = :srm"),
    @NamedQuery(name = "Beers.findByAddUser", query = "SELECT b FROM Beers b WHERE b.addUser = :addUser"),
    @NamedQuery(name = "Beers.findByLastMod", query = "SELECT b FROM Beers b WHERE b.lastMod = :lastMod"),
    @NamedQuery(name = "Beers.findByBuyPrice", query = "SELECT b FROM Beers b WHERE b.buyPrice = :buyPrice"),
    @NamedQuery(name = "Beers.findBySellPrice", query = "SELECT b FROM Beers b WHERE b.sellPrice = :sellPrice")})
public class Beers extends ResourceSupport implements Serializable {

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
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cat_id")
    private int catId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "style_id")
    private int styleId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abv")
    private float abv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ibu")
    private float ibu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "srm")
    private float srm;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "add_user")
    private int addUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMod;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "image")
    private String image;
    @Basic(optional = false)
    @NotNull
    @Column(name = "buy_price")
    private double buyPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sell_price")
    private double sellPrice;

    public Beers() {
    }

    public Beers(Integer id) {
        this.id = id;
    }

    public Beers(Integer id, int breweryId, String name, int catId, int styleId, float abv, float ibu, float srm, String description, int addUser, Date lastMod, String image, double buyPrice, double sellPrice) {
        this.id = id;
        this.breweryId = breweryId;
        this.name = name;
        this.catId = catId;
        this.styleId = styleId;
        this.abv = abv;
        this.ibu = ibu;
        this.srm = srm;
        this.description = description;
        this.addUser = addUser;
        this.lastMod = lastMod;
        this.image = image;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

 
    public Integer getBeerId() {
        return id;
    }

    public void setBeerId(Integer id) {
        this.id = id;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public float getAbv() {
        return abv;
    }

    public void setAbv(float abv) {
        this.abv = abv;
    }

    public float getIbu() {
        return ibu;
    }

    public void setIbu(float ibu) {
        this.ibu = ibu;
    }

    public float getSrm() {
        return srm;
    }

    public void setSrm(float srm) {
        this.srm = srm;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAddUser() {
        return addUser;
    }

    public void setAddUser(int addUser) {
        this.addUser = addUser;
    }

    public Date getLastMod() {
        return lastMod;
    }

    public void setLastMod(Date lastMod) {
        this.lastMod = lastMod;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
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
        if (!(object instanceof Beers)) {
            return false;
        }
        Beers other = (Beers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Beers[ id=" + id + " ]";
    }
    
}

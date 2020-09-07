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
@Table(name = "styles")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Styles.findAll", query = "SELECT s FROM Styles s"),
    @NamedQuery(name = "Styles.findAllPagination", query = "SELECT s FROM Styles s ORDER BY s.id"),
    @NamedQuery(name = "Styles.findById", query = "SELECT s FROM Styles s WHERE s.id = :id"),
    @NamedQuery(name = "Styles.findByCatId", query = "SELECT s FROM Styles s WHERE s.catId = :catId"),
    @NamedQuery(name = "Styles.findByStyleName", query = "SELECT s FROM Styles s WHERE s.styleName = :styleName"),
    @NamedQuery(name = "Styles.findByLastMod", query = "SELECT s FROM Styles s WHERE s.lastMod = :lastMod")})
public class Styles extends ResourceSupport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cat_id")
    private int catId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "style_name")
    private String styleName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastMod;

    public Styles() {
    }

    public Styles(Integer id) {
        this.id = id;
    }

    public Styles(Integer id, int catId, String styleName, Date lastMod) {
        this.id = id;
        this.catId = catId;
        this.styleName = styleName;
        this.lastMod = lastMod;
    }

    public Integer getStyleId() {
        return id;
    }

    public void setStyleId(Integer id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public Date getLastMod() {
        return lastMod;
    }

    public void setLastMod(Date lastMod) {
        this.lastMod = lastMod;
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
        if (!(object instanceof Styles)) {
            return false;
        }
        Styles other = (Styles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Styles[ id=" + id + " ]";
    }
    
}

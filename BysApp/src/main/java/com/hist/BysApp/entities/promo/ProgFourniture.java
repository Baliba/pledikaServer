/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class ProgFourniture extends cObj implements Serializable {
	
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    
	    @Column(unique=true)
	    private String code;
	    
	    private int type_fn;
	    private String unity;
        private int qt;
        private String name;
        @Column(nullable = false, columnDefinition=" boolean default false ")
        private boolean sell_at;
	    @ManyToOne
	    @JsonIgnoreProperties({"course", "programme"}) 
	    private Programme programme;
	    
	    @ManyToOne
	    @JsonIgnoreProperties({"progfourniture"}) 
	    private Fourniture fourniture;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.paiement.PaiementAdmission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Programme  extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String code;
    private String name;
    private String description;
    @Column(columnDefinition=" boolean default true ")
    private boolean enabled;
    private int pos;
    
    @ManyToMany
	@JsonIgnoreProperties({"programme","promo_cours"})
	private Collection<Course> course;
    
    @OneToMany(mappedBy="programme")
    @JsonIgnoreProperties({"programme"})
	private List<ProgFourniture> progfourniture;
    
	private String  niveau;
	
	   @Column(columnDefinition=" integer default 24 ")
	   private Integer max_cours;
	   @Column(columnDefinition=" integer default 24 ")
	   private Integer max_four;
}

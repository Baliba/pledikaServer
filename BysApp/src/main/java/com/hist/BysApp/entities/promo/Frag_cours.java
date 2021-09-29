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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Frag_cours  extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JsonIgnoreProperties({"frag_cours","promotion"})
    private PromoFrag promofrag;
    
    private Long id_promo;
    private Long id_cours;
    private String course;
    private String name;
    private int coef ; 
    private float note_total;
    private int limit_part; 
    @Column(unique=true)
    private String code;
    
    @Column(columnDefinition=" float default 0 ")
    private float    note_pass;
    @Column(columnDefinition=" float default 0 ")
    private float    note_rep;
    @Column(columnDefinition=" float default 0 ")
    private float    note_excel;
    
    @OneToMany(mappedBy = "frag_cours",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"frag_cours"}) 
  	private List<HCours> hcours;
    
    private String jours_examen;
    private String heure_examen;
    
    private boolean examen;
    private boolean base;
    private String  board;
    
	public void setAHcours(HCours hc) {
		 hcours = new ArrayList<HCours>();
	     hcours.add(hc);
	}
	
	@OneToMany(mappedBy = "frag_cours", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"frag_cours","parcours_frag"}) 
	private Collection<Results> results;
	
	public int getSizeResult() {
		return results.size();
	}
}

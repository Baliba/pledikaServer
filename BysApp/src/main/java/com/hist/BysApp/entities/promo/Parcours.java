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
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.PVersement;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author User
 */
@Entity
@Data
@DynamicUpdate
public class Parcours extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
    
    public Long id_student;
    @Column(unique=true)
    private String code;
    
  
    private String  nom;
    private String pnom;
    @Column(columnDefinition=" varchar (2) default 'M' ")
    private String sexe;
    private String  code_student;
    
    @ManyToOne
    @JsonIgnoreProperties({"parcours","current_class","next_class","paiement_admission","promotion","prev_class"})
    private UserEntity user;
   
    @ManyToOne
	@JsonIgnoreProperties({"parcours","promo_cours","promofrag","promo_af","niveau_rel","titulaire"})
    @JsonManagedReference
    private Promotion promotion;
    
    @Column(columnDefinition=" float default 0 ")
    private double moy_final;
    
    private String promo_name;
    private String  niv_code; 
    private String  cycle_code; 
    private Long  option_paiement;
    
    @OneToMany(mappedBy = "parcours",  cascade = CascadeType.REMOVE) 
   	@JsonIgnoreProperties("parcours")
   	private Collection<PVersement> pversement;
    private int granted;
    
    @OneToMany(mappedBy = "parcours",  cascade = CascadeType.REMOVE) 
    @JsonBackReference
   	@JsonIgnoreProperties({"parcours","promofrag"})
   	private Collection<Parcours_frag> parcours_frag;
   	
    private boolean actived=true;
    // DISCIPLINE 
    private long   absence;
    private int    retard;
    private String mention;
    private String note_1;
    private String note_2;
    private String note_3;
    private int    print;
    private boolean reprise;
    
    @Column(columnDefinition=" integer default 0 ")
    private Integer decision;
    
    @Column(nullable=false, columnDefinition=" integer default 1 ")
    private Integer mode_paiement;
    
    Long id_promo;
    
}

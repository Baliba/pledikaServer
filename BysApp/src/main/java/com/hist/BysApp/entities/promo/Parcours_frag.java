/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 *
 * @author User
 */
@Entity
@Data
public class Parcours_frag extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
    
    @Column(unique=true)
    private String code;
    
    private String  nom;
    private String pnom;
    @Column(columnDefinition=" varchar (2) default 'M' ")
    private String sexe;
    
    private String  code_student;
   
    @OneToMany(mappedBy = "parcours_frag",  cascade = CascadeType.REMOVE)
    // @JsonBackReference 
    @JsonIgnoreProperties("parcours_frag")
   	private Collection<Results> results;
    
    @ManyToOne
    @JsonIgnoreProperties({"parcours_frag","user","promotion","pversement"})
    private Parcours parcours;
    
    private String promo_name;
    
    @ManyToOne
    @JsonIgnoreProperties({"parcours_frag","promotion","frag_cours"})
    private PromoFrag promofrag;
    
    private boolean   actived;
    private long      absence;
    private int       retard;
    private String    mention;
    private String    note_1;
    private String    note_2;
    private String    note_3;
    private int       print;
}

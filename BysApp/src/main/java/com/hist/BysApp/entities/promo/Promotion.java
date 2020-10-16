/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Niveau_rel;
import com.hist.BysApp.entities.member.Maladie;
import com.hist.BysApp.entities.member.UserEntity;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 *
 * @author User
 */
@Entity
@Data
public class Promotion extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique=true)
    private String code;
    
    private String code_niveau;
    
    @Column()
    private String code_cycle;
    @ManyToOne
    @JsonIgnoreProperties({"promotion","niveau","salle"})
    @NotFound(action = NotFoundAction.IGNORE)
    private Niveau_rel  niveau_rel;
    
    private String Description;
    
    @ManyToOne
    @JsonIgnoreProperties("promotion")
    private Promo_af promo_af;
    
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE) 
	@JsonIgnoreProperties({"promotion","parcours_frag","user","prev_class","next_class","current_class"})
    @NotFound(action = NotFoundAction.IGNORE)
	private Collection<Parcours> parcours;
    
    
    @OneToMany(mappedBy = "promotion",cascade = CascadeType.REMOVE) 
    @JsonIgnoreProperties("promotion")
    @NotFound(action = NotFoundAction.IGNORE)
  	private Collection<PromoFrag> promofrag;
    
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.REMOVE) 
  	@JsonIgnoreProperties("promotion")
    @NotFound(action = NotFoundAction.IGNORE)
  	private Collection<Promo_cours> promo_cours;
  	
    private boolean setup_over = false;
    private boolean completed = false;
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties({"promotion","parcours","paiement_admission","courses","maladies","parent","role","phones"})
    private UserEntity titulaire;
    
    @Column(columnDefinition=" integer default 100 ")
    private int max_student;
    
    private boolean enabled = true;
    private int    moy_total;
    private double moy_accept;
    private double moy_reprise;
    private double moy_exc;
    private String board,next_promo_name;
    private Long next_promo;
    private String prev_promo_name;
    private Long prev_promo;
    private double  scolarite;
    private Long prog_id;
    
    @Column(columnDefinition="boolean default false")
    private boolean reprise;
}

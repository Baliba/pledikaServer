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

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

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
public class PromoFrag extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date   date_debut;
    private Date   date_fin;
    private String prev_niv;
    private String next_niv;
    private String versement;
    
    @Column(unique=true)
    private String code ;
    
    @ManyToOne
    @JsonIgnoreProperties({"promofrag","promo_cours","niveau_rel","parcours","promo_af","promo_cours"})
    private Promotion promotion;
    
   
    @OneToMany(mappedBy="promofrag",cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"promofrag","results"})
    Collection<Frag_cours> frag_cours; 
    
    @OneToMany(mappedBy="promofrag",cascade = CascadeType.REMOVE)
    @JsonBackReference
    @JsonIgnoreProperties({"promofrag","parcours"})
    Collection<Parcours_frag> parcours_frag; 
    
    private Date date_debut_ex;
    private Date date_fin_ex;
    private boolean actived;
    private boolean setup_over;
    private boolean base;
    private boolean reprise = false;
    private String  share_code;
    private Long attacher;
    private boolean final_frag = false;
}

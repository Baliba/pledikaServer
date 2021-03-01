/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;

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
import javax.persistence.OneToMany;

/**
 *
 * @author User
 */
@Entity
@Data
public class Promo_af extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date_debut, date_fin;
    private String description;
    private Long prev_year;
    private Long next_year;
    @OneToMany(mappedBy = "promo_af", cascade = CascadeType.REMOVE) 
    @JsonIgnoreProperties({"promo_af","parcours","promo_cours","titulaire","niveau_rel","promofrag"})
  	private Collection<Promotion> promotion;
    private boolean actived = false;
    
    
    private String currency;
    
    @Column(columnDefinition=" float default 1000 ")
    private float prix_admis;
    // moy 
    @Column(columnDefinition=" integer default 10 ")
    private int   moy_total;
    @Column(columnDefinition=" float default 5.0 ")
    private float moy_accept;
    @Column(columnDefinition=" float default 4.0 ")
    private float moy_reprise;
    @Column(columnDefinition=" float default 8.0 ")
    private float moy_exc;
    
    
    // info sur taxe 
    @Column(columnDefinition=" float default 6.0 ")
    private float ona;
    @Column(columnDefinition=" float default 0 ")
    private float iri;
    @Column(columnDefinition=" float default 10.0 ")
    private float iri_1;
    @Column(columnDefinition=" float default 15.0 ")
    private float iri_2;
    @Column(columnDefinition=" float default 10.0 ")
    private float iri_3;
    @Column(columnDefinition=" float default 30 ")
    private float iri_4;
    @Column(columnDefinition=" float default 1 ")
    private float cfgdct;
    
    @Column(columnDefinition=" float default 1 ")
    private float fdu;
    @Column(columnDefinition=" float default 1 ")
    private float cas;
    @Column(columnDefinition=" float default 3 ")
    private float assure_mal;
 
    @Column(columnDefinition=" boolean default true ")
    private boolean block;
    private String  msg_block;
    private Date    last_pay;
    
    // admis  -----
    @Column(columnDefinition=" boolean default true ")
    private boolean admis_is_open;
    private Date date_admis_debut;
    private Date date_admis_fin;
    // NEW DATA

    @Column(columnDefinition=" integer default 4 ")
    private int nbre_ctrl;
    
    @Column(columnDefinition=" varchar default 'Ctrl' ")
    private String frag_name;
    @Column(columnDefinition=" integer default 1 ")
    private int type_reprise;
    @Column(columnDefinition=" integer default 1 ")
    private int reprise;
    
    @Column(columnDefinition=" integer default 1 ")
    private int year_part;
    
    @Column(nullable = true, updatable=true)
    private String start_time;
    
    @Column(nullable = true, updatable=true)
    private String end_time;
    
    @Column(nullable = false, columnDefinition=" integer default 1 ")
    private int mode_paiement;
}


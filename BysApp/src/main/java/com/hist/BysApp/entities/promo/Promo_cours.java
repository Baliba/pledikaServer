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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Promo_cours  extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String  code;
    @ManyToOne
    @JsonIgnoreProperties({"promo_cours","prof","programme"})
    private Course course;
    @ManyToOne
    @JsonIgnoreProperties({"promo_cours","parcours","niveau_rel","promo_frag"})
    private Promotion promotion;
    private String name;
    private int coef ; 
    private float note_total;
    private int limit_part; 
    private String jours;
    private String heure_cours;
    private boolean base;
    @Column(columnDefinition=" float default 0 ")
    private float    note_pass;
    @Column(columnDefinition=" float default 0 ")
    private float    note_rep;
    @Column(columnDefinition=" float default 0 ")
    private float    note_excel;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.member.UserEntity;

import lombok.Data;


@Entity
@Data
public class Results extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long     id;
    
    @Column(unique=true)
    private String   code;
    
    private String   code_student;
    @Column(nullable = false, updatable=true)
    private String   nom;
    
    @Column(nullable = false, updatable=true)
    private String   pnom;
    private float    note_total;
    private float    note;
    @Column(columnDefinition=" float default 0 ")
    private float    note_pass;
    @Column(columnDefinition=" float default 0 ")
    private float    note_rep;
    @Column(columnDefinition=" float default 0 ")
    private float    note_excel;
    private int coef ; 
    private boolean validated;
    
    @ManyToOne
    @JsonIgnoreProperties({"results", "promofrag","hcours"})
    private Frag_cours frag_cours;
    
    @ManyToOne
    @JsonIgnoreProperties({"results", "parcours","promofrag"})
    private Parcours_frag parcours_frag;
    
}

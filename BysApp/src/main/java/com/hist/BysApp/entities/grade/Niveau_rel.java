/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.grade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.promo.Promotion;

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

import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Niveau_rel extends cObj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JsonIgnoreProperties("niveau_rel")
    private Niveau niveau;
    
    @ManyToOne
    @JsonIgnoreProperties("niveau_rel")
    private Salle salle;
    
    
    private Long prog_id;
    
    @ManyToOne
    @JsonIgnoreProperties("niveau_rel")
    private Vacation vacation;
    
    
    @Column(nullable=true, unique=true)
    private String  name;
   
   
    private String description;
    
    private double  scolarite;
    
    @OneToMany(mappedBy="niveau_rel",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"niveau_rel","parcours","promofrag","promo_cours","titulaire","promo_af"})
    private Collection<Promotion> promotion;
    @Column(columnDefinition="boolean default false")
    private boolean reprise;
}

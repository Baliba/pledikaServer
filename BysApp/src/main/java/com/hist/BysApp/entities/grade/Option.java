/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.grade;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.promo.Course;

import java.io.Serializable;
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
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Option extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String code;
    private String name;
    private double montant_admis;
    
    @OneToMany(mappedBy="option",cascade=CascadeType.ALL)
    @JsonIgnoreProperties("option")
    private Collection<Niveau> niveau;
    
    @ManyToOne
    @JsonIgnoreProperties("option")
    Domaine domaine;
    
    @OneToMany(mappedBy = "option")
    @JsonIgnoreProperties("option")
    private Collection<CycleOPaie> copaie;
    
    public Option(String name,String code) {
        this.name = name;
        this.code = code;
    }
    
    public Option() {};
   
}

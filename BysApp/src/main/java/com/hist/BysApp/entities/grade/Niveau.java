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
import com.hist.BysApp.entities.paiement.classe.ClasseOPaie;

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
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Niveau extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String code;
    private String name;
    private String other_name;
    private int pos;
    private boolean as_prev = true;
    private boolean as_next = true;
    private boolean enabled = true;
    private String  next;
    private String  prev;
    private double  scolarite;
    
    @Column(nullable = true,columnDefinition="double precision default '1000'")
    private double montant_admis_classe = 0.0;
    
    @Column(columnDefinition=" boolean default true ")
    private boolean  over_classe;
    
    @Column(columnDefinition="boolean default false")
    private boolean reprise;
    
    @Column(columnDefinition="boolean default false")
    private boolean examen_etat;
   
    @ManyToOne
    @JsonIgnoreProperties({"niveau"})
    private Option option;

    @OneToMany(mappedBy="niveau")
    @JsonIgnoreProperties("niveau")
    private Collection<Niveau_rel> niveau_rel;
    //-----------------------
    @OneToMany(mappedBy="niveau")
    @JsonIgnoreProperties("niveau")
    private Collection<Niv_doc> niv_doc;
    
    @Column(columnDefinition=" integer default 1 ")
    private Integer nbre_salle;
    // -----------------------
    
    @OneToMany(mappedBy = "niveau")
    @JsonIgnoreProperties("niveau")
    private Collection<ClasseOPaie> copaie;
    
    
    public Niveau() {}
    public Niveau(String name , Option o) {
        this.name = name ;
        this.option = o;
    }
    public Niveau(String name) {
        this.name = name ;
    }
    
    public Niveau(String name, String code, int pos) {
        this.name = name;
        this.code = code+"-"+pos;
        this.pos  = pos;
    }
    
    

}

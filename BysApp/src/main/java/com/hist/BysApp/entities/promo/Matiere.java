/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.promo;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Option;

import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Matiere extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String name;
    private int  coef;
    private int  note_total;

    @OneToMany(mappedBy="matiere", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"matiere"})
    Collection<SMatiere> smatiere;
   
}

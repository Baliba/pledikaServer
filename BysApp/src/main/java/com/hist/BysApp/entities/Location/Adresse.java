/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.Location;

import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.member.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.Location.Etat;
import com.hist.BysApp.entities.Location.Pays;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Adresse extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
 
    @ManyToOne
    private Pays pays;

    @ManyToOne
    private Etat etat;

    @ManyToOne
    private Ville ville;
  
    @ManyToOne
    @JsonIgnoreProperties("adresses") 
    private UserEntity user;

}

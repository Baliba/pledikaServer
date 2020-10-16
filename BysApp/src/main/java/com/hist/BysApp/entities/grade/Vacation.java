/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.grade;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Vacation extends cObj implements Serializable {

    private static final long serialVersionUID = 1L;
//  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
//  private Long id;
    @Id
    private String code; 
    private String name;

    @OneToMany(mappedBy="vacation",cascade=CascadeType.ALL)
    @JsonManagedReference
    private Collection<Niveau_rel> niveau_rel;
    
    public Vacation(){
    }

    public Vacation(String name,String code) {
        this.code = code;
        this.name = name;
    }
    
}

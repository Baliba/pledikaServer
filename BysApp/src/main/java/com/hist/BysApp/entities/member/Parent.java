package com.hist.BysApp.entities.member;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Entity
@Data
public class Parent extends cObj implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    private String nom;
    private String prof;
    private String who_is;
    private String phone;
    private String adresse; 
    private boolean resp;
    @ManyToOne
    @JsonIgnoreProperties("parent")
    private UserEntity user;
}

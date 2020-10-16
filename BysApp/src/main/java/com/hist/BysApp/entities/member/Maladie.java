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
public class Maladie extends cObj implements Serializable  {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String nom_medecin;
	private String hopital;
	private String tel;
	private String adresse;
	private boolean gen;
	@ManyToOne 
	@JsonIgnoreProperties("maladies") 
	private UserEntity user;
}

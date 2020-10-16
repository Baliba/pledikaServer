package com.hist.BysApp.entities.member;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Entity
@Data
public class Contrat extends cObj implements Serializable  {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String name;
    private String type_contrat;
    private Date date_debut;
    private Date date_fin;
    
    @OneToMany(mappedBy = "contrat")
    @JsonIgnoreProperties({"contrat"}) 
    List<ArticleContrat> articleContrat;
    
 }

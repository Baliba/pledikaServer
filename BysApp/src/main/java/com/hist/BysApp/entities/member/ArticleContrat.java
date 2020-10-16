package com.hist.BysApp.entities.member;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;

import lombok.Data;
@Entity
@Data
public class ArticleContrat extends cObj implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String noArticle;
	
	@ManyToOne
	@JsonIgnoreProperties("articleContrat") 
	Contrat contrat;
	
	private String description;
}

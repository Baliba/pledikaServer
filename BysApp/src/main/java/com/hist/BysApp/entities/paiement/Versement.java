package com.hist.BysApp.entities.paiement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Niveau;

import lombok.Data;


@Data
@Entity
public class Versement extends cObj  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String code;
	
	@Column(unique=true)
	private String name;
	private String jour_limit;
	private String mois_limit;
	
	@Column(columnDefinition=" integer default 1 ")
	public Integer type_verse;
	
	public int pos;
	@ManyToOne
	@JsonIgnoreProperties({"versement"})
	private OPaie  opaie;
	
	public Versement() {}
	public Versement(String code, String nom, String jour_limit, String mois_limit, OPaie opaie, int tv) {
		this.code = code;
		this.name = nom;
		this.jour_limit = jour_limit;
		this.mois_limit = mois_limit;
		this.opaie = opaie;
		this.type_verse = tv;
	}
	
	
}

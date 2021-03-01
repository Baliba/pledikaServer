package com.hist.BysApp.entities.paiement.classe;

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
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.CycleVersement;

import lombok.Data;


@Data
@Entity
public class ClasseVersement extends cObj  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String code;
	@Column(unique=true)
	private String name;
	private String jour_limit;
	private String mois_limit;
	private Double montant;
	
	@ManyToOne
	@JsonIgnoreProperties("cversement")
	private ClasseOPaie  copaie;
	
	@Column(columnDefinition=" boolean default true ")
	private boolean actived;
	@Column(columnDefinition=" integer default 1 ")
	public int type_verse;
	public int pos;
	
	public ClasseVersement() { }
	public ClasseVersement(String code, String nom, String jour_limit, String mois_limit,ClasseOPaie  copaie) {
		this.code = code;
		this.name = nom;
		this.jour_limit = jour_limit;
		this.mois_limit = mois_limit;
		this.copaie = copaie;
	}
	public ClasseVersement(CycleVersement cv, String code) {
		// id = cv.getId();
		this.code = cv.getCode()+"_"+code;
		name = cv.getName()+"_"+code;
		this.jour_limit = cv.getJour_limit();
		this.mois_limit = cv.getMois_limit();
		montant = cv.getMontant();
		actived = cv.isActived();
		type_verse = cv.getType_verse();
		pos = cv.getPos();
	}
	
}

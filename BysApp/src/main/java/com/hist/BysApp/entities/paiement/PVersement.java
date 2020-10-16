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
import com.hist.BysApp.entities.promo.Parcours;

import lombok.Data;


@Data
@Entity
public class PVersement extends cObj  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String name;
	private String code;
	private String jour_limit;
	private String mois_limit;
	private Double montant_to_pay;
	private Double montant_pay;
	private Double montant_init;
	@Column(columnDefinition=" boolean default true ")
	private boolean actived;
	
	private String  code_share;
	
	@Column(columnDefinition=" Integer default 1 ")
	public Integer type_verse;
	
	public int pos;
	@ManyToOne
	@JsonIgnoreProperties({"pversement","promotion","promofrag","results","user"})
	private Parcours  parcours;
	public  PVersement() {}
	public  PVersement(String code, String nom, String jour_limit, String mois_limit,Double montant_to_pay) {
		this.name = nom;
		this.jour_limit = jour_limit;
		this.mois_limit = mois_limit;
		this.montant_to_pay = montant_to_pay;
	}
	
	
}

package com.hist.BysApp.entities.paiement;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.grade.Option;

import lombok.Data;


@Entity
@Data
public class CycleOPaie extends cObj  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
    private String code;
	
	@Column(unique=true)
    private String name;
	
	@Column(columnDefinition="boolean default true ")
	private boolean actived;
	
	public CycleOPaie() {
	}
	public CycleOPaie(String name,String code, Option o) {
		super();
		this.name = name;
		this.code = code ;
		option = o;
	}
	
	@ManyToOne
	@JsonIgnoreProperties("copaie")
	@JsonBackReference
	private Option option;
	
	@OneToMany(mappedBy = "copaie")
	@JsonIgnoreProperties("copaie")
	private Collection<CycleVersement> cversement;
}

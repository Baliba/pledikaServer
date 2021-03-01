package com.hist.BysApp.entities.paiement.classe;

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
import com.hist.BysApp.entities.paiement.CycleOPaie;

import lombok.Data;


@Entity
@Data
public class ClasseOPaie extends cObj  implements Serializable {
	
	@Id
    private String code;
	
	@Column(unique=true)
    private String name;
	
	@Column(columnDefinition="boolean default true ")
	private boolean actived;
	
	public ClasseOPaie() {
	}
	public ClasseOPaie(String name,String code, Niveau n) {
		 super();
		 this.name = name;
		 this.code = code ;
		 niveau = n ;
	}
	
	public ClasseOPaie(CycleOPaie c, String code) {
		// id   = c.getId();
		this.code = c.getCode()+"_"+code;
		name = c.getName()+"_"+code;
		actived = c.isActived();
	}

	@ManyToOne
	@JsonIgnoreProperties({"copaie"})
	@JsonBackReference
	private Niveau niveau;
	
	@OneToMany(mappedBy = "copaie")
	@JsonIgnoreProperties("copaie")
	private Collection<ClasseVersement> cversement;
}

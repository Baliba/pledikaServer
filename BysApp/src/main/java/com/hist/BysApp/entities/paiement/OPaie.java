package com.hist.BysApp.entities.paiement;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Niveau;

import lombok.Data;


@Entity
@Data
public class OPaie extends cObj  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique=true)
    private String code;
	@Column(unique=true)
    private String name;
	public OPaie() {}
	
	public OPaie(String name,String code) {
		super();
		this.name = name;
		this.code = code ;
	}
	@OneToMany(mappedBy = "opaie")
	@JsonIgnoreProperties("opaie")
	private List<Versement> versement;;
}

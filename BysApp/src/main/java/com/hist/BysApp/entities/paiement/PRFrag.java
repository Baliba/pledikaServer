package com.hist.BysApp.entities.paiement;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
public class PRFrag extends cObj  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long  id;
	
	@Column(unique=true)
    private String code;
	
	private int pos;
	private String mois;
	private String jour;
	private boolean activated=true;
	public PRFrag(String code, int pos, String mois, String jour) {
		this.code = code;
		this.pos = pos;
		this.mois = mois;
		this.jour = jour;
	}
	public PRFrag() {
		super();
	}
	
	
}

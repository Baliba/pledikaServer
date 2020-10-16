package com.hist.BysApp.entities.paiement;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.member.UserEntity;

import lombok.Data;

@Entity
@Data
 public class PaiementAdmission extends cObj  implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String    code;
	
	@ManyToOne
	@JsonIgnoreProperties({"niveau_rel","option","niv_doc"})
	private Niveau  niveau;
	
	private Double  montant;
	private boolean pay=false;
	private Date    date_paiement;
	private boolean validate = false;
	// ********| |******** //
	private Date pay_date;
	private Long pay_by;
	private Date validate_date;
	private Long validate_by;

}

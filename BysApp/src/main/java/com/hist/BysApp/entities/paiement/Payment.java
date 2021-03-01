package com.hist.BysApp.entities.paiement;

import java.io.Serializable;
import java.util.Date;

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
public class Payment extends cObj  implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long     id;
	
	private  String  promotion;
	private  String  code_etudiant;
	private  String  serie;
	private  String  type_paiement;
	private  double  pay_money;
	private  double  montant;
	private  double  remain;
	private  Long    pay_by;
	private  Long    id_parcours;
	
	private  boolean validate;
	private Date     validate_date;
	private Long     validate_by;

}

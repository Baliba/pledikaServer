package com.hist.BysApp.entities.paiement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Data
@Entity
public class Caisse extends cObj  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private float  montant;
	private String raison ;
    private int  type_cs;
    private Long acad;
    private Long id_maker;
    private Long id_caissier;

}

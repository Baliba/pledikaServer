package com.hist.BysApp.Request;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Data
public class CaisseReq  {
	
	private float  montant;
	private String raison ;
    private int    type_cs;
    private Long   acad;
    private Long   id_maker;
    private Long   id_caissier;

}

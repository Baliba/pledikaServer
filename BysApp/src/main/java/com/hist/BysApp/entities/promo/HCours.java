package com.hist.BysApp.entities.promo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.paiement.PaiementAdmission;

import lombok.Data;

@Entity
@Data
public class HCours extends cObj implements Serializable {
	
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String code;
    private String jours;
    private String heure_cours;
    @OneToOne
    @JsonIgnoreProperties("hcours")
    private  Frag_cours frag_cours;
}

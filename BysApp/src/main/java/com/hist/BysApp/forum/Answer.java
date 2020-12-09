package com.hist.BysApp.forum;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.promo.Promo_af;

@Entity
public class Answer extends cObj implements Serializable  {
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    String rep;
	    private Long  creator;
	    boolean good;
	    @ManyToOne
	    @JsonIgnoreProperties("question")
	    private  Question question;
}

package com.hist.BysApp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hist.BysApp.entities.enums.CEvent;

import lombok.Data;
@Data
@Entity
public class Event extends cObj implements Serializable {
	 private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    String name;
	    private CEvent categorie;
	    private String day,month,year, fday,fmonths,fyear, hour, fhour;
	    String details;
	    String visitor;
	    String v_phone;
	    String v_email;
	    boolean has_during = false;
	    Long acad;
}

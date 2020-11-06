package com.hist.BysApp.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class Contact extends cObj implements Serializable {
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    String name, email, message,phone;
	    
	    @Column(nullable = false, columnDefinition=" boolean default false")
	    private boolean read;
}

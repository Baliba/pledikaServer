package com.hist.BysApp.entities.promo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Entity
@Data
public class Fragment  extends cObj implements Serializable{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;
    private boolean cours_setup = false;
    private boolean base;
    private boolean reprise = false;
    private boolean actived=true;
    @Column(columnDefinition=" boolean default false ")
    private boolean final_frag = false;
    public Fragment() {}
    public Fragment(String name) {
		super();
		this.name = name;
		reprise = false;
	}
    public Fragment(String name,boolean rep) {
		super();
		this.name = name;
		reprise = rep;
	}
}

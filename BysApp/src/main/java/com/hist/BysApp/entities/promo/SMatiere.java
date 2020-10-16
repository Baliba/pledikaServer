package com.hist.BysApp.entities.promo;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Option;
import com.hist.BysApp.entities.member.UserEntity;

import lombok.Data;

@Entity
@Data
public class SMatiere extends cObj implements Serializable {
	
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    
	    private Long id;
	    
	    private String name;
	    
	    @Column(unique=true)
	    private String code;
	    
	    @ManyToOne
	    @JsonIgnoreProperties("smatiere") 
	    private Matiere matiere;
	    
	    private int coef;
	    private int note_total;
	    private boolean actived;
	    private String  option;
	    private int  note_pass;
	    private int  note_rep;
	    private int  note_excel;
	    
	    @OneToMany(mappedBy="smatiere", cascade = CascadeType.REMOVE)
	    @JsonIgnoreProperties({"smatiere"})
	    Collection<Course> course;

}

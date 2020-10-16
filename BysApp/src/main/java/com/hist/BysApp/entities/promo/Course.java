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
public class Course extends cObj implements Serializable {
	
	    private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    
	    private Long id;
	    
	    private String name;
	    
	    @Column(unique=true)
	    private String code;
	    
        @ManyToOne
        @JsonIgnoreProperties("course") 
        private SMatiere  smatiere ;
	   
	    @ManyToOne
	    @JsonIgnoreProperties({"courses","promotion","parcours"})
	    private UserEntity prof;
	    
	    @ManyToMany(mappedBy="course")
	    @JsonIgnoreProperties({"course"}) 
	    private Collection<Programme> programme;
	    
	    private int coef;
	    
	    private int note_total;
	    
	    @OneToMany(mappedBy="course", cascade = CascadeType.REMOVE)
	    @JsonIgnoreProperties({"course","promotion"})
	    Collection<Promo_cours> promo_cours; 
	   
	    private boolean actived;
	    
	    private String  option;
	    
	    @Column(nullable=true)
	    private Long matiere_id;
	    
	    @Column(nullable=true)
	    private Long mprof_id;
	   
	    @Column(columnDefinition=" float default 0 ")
	    private float    note_pass;
	    @Column(columnDefinition=" float default 0 ")
	    private float    note_rep;
	    @Column(columnDefinition=" float default 0 ")
	    private float    note_excel;
	    private String book_name;
	    private String description;

}

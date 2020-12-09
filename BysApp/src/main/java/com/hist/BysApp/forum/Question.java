package com.hist.BysApp.forum;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.cObj;

@Entity
public class Question extends cObj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    private Long  creator;
    String Matiere;
    Long promo;
    Long Img;
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) 
  	@JsonIgnoreProperties({"question"})
    @NotFound(action = NotFoundAction.IGNORE)
  	private Collection<Answer> answers;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.grade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author User
 */
@Entity
@Data
public class Document extends cObj implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String code;
  
    @Column(unique=true)
    private String name;
    
    @OneToMany(mappedBy="document")
    @JsonIgnoreProperties("document")
    private Collection<Niv_doc> niv_doc;

	public Document(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public Document() {
		super();
	}
	
	
    
    
    
}

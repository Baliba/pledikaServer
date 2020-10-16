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
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Domaine extends cObj implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id
    private String code;
    
    @Column(unique=true)
    private String name;
    
    @OneToMany(mappedBy = "domaine")
    @JsonIgnoreProperties("domaine")
    private Collection<Option> option;
    
    public Domaine(String domaine, String dom_code) {
		name = domaine;
		code = dom_code;  
   	}
    
    public Domaine() { }
}

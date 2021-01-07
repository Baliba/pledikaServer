/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class Parent {
    private Long id;
    private String code;
    private String lastName;
    private String firstName;
    private String sexe;
    private Date   date_de_naiss;
    private String email;
    private boolean enable;
    private String phone;
    private String adresse;
    //                (u.id,      u.code,      u.lastName,      u.firstName,      u.sexe,    u.date_de_naiss,       u.username,   u.enabled,     u.phone,      u.adresse)//
	public Parent(Long id, String code, String lastName, String firstName, String sexe, Date date_de_naiss, String email, boolean enable, String phone, String adresse) {
		super();
		this.id = id;
		this.code = code;
		this.lastName = lastName;
		this.firstName = firstName;
		this.sexe = sexe;
		this.date_de_naiss = date_de_naiss;
		this.email = email;
		this.enable = enable;
		this.phone = phone;
		this.adresse = adresse;
	}
    
    
 
}

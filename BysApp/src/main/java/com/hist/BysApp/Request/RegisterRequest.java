/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Request;

import java.io.Serializable;
import java.util.Date;


import lombok.Data;
@Data
public final class RegisterRequest implements Serializable  {
    
    private static final long serialVersionUID = 5926468583005150707L;
    private String  username;
    private String  password;
    private String  fname,lname, sexe,nom_pere,nom_mere, reference, lieu_de_naiss;
    private String  code_class;
    private Date  date_de_naiss;
    private Long    id_ville_ln;
    private boolean arefaire,enabled;
    private String last_year, last_moyen, last_etab,fonction;
    private String nom_ass, phone_ass, adresse_ass, phone, adresse;
    private Long role;
    private double salairy;
    
    public RegisterRequest(){
    }
    public RegisterRequest(String username, String password,String fname,String lname) {
        this.setUsername(username);
        this.setPassword(password);
        this.setFname(fname);
        this.setLname(lname);
  
    }
    
    
    
  
    
}

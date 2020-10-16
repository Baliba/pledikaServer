/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.model;

import java.io.Serializable;
import java.util.Date;


import lombok.Data;
@Data
public final class NUserDTO implements Serializable  {
    
    private static final long serialVersionUID = 5926468583005150707L;
    private String  nom , prenom, code , sexe, email,lieu_de_naiss;
    private Date date_de_naiss;
}

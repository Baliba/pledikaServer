package com.hist.BysApp.Request;

import java.io.Serializable;

import lombok.Data;
@Data
public class ParcoursRequest implements Serializable  {
	 private static final long serialVersionUID = 5926468583005150707L;
	    private Long  id_student;
	    private Long  id_promo;
	    private Long  id_opaie; 
	    private String mention;
}

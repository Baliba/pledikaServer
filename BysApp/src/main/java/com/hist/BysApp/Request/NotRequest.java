package com.hist.BysApp.Request;

import java.io.Serializable;

import lombok.Data;
@Data
public class NotRequest implements Serializable  {
	 private static final long serialVersionUID = 5926468583005150707L;
	    private String  message,titre;
	    private int  cible;
}

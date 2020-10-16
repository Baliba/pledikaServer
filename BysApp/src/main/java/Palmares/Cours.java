package Palmares;

import lombok.Data;

@Data
public class Cours {  	
   Long id;
   String code;
   String name;
   private int coef ; 
   private float note_total;

public Cours(Long id, String code, String name, int coef, float note_total) {
	super();
	this.id = id;
	this.code = code;
	this.name = name;
	this.coef = coef;
	this.note_total = note_total;
}



   
}

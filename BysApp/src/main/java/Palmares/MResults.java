package Palmares;

import lombok.Data;

@Data
public class MResults {
  Long idc;	
  String nom;
  float note;
  int   coef;
  float note_total;
  Long idr;
  String code_student;
public MResults(Long idc, String nom, float note,float nt) {
	super();
	this.idc = idc;
	this.nom = nom;
	this.note = note;
	note_total= nt;
}
public MResults(Long idc, String nom, float note, int coef, float note_total,Long id) {
	super();
	this.idc = idc;
	this.nom = nom;
	this.note = note;
	this.coef = coef;
	this.note_total = note_total;
	idr=id;
}

}

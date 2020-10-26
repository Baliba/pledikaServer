package Palmares;

import javax.persistence.Column;

import lombok.Data;
@Data
public class NResults {
	
    private Long     id;
    private String   code;
    private String   code_student;
    private String   nom;
    private String   pnom;
    private float    note_total;
    private float    note;
    private float    note_pass;
    private float    note_rep;
    private float    note_excel;
    private int coef ;
	public NResults(Long id, String code_student, String nom, String pnom, float note_total, float note,  int coef) {
		super();
		this.id = id;
		this.code_student = code_student;
		this.nom = nom;
		this.pnom = pnom;
		this.note_total = note_total;
		this.note = note;
		this.note_excel = note_excel;
		this.coef = coef;
	} 

}

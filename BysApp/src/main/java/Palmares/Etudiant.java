package Palmares;

import java.util.Date;
import java.util.List;


import lombok.Data;

@Data
public class Etudiant {
	Long id;
	String nom, pnom;
	List<MResults> results ;
	List<MResults> mresults;
	String code;
	float total;
	Date date_naiss;
	public Etudiant(Long id, String nom, String pnom) {
		super();
		this.id = id;
		this.nom = nom;
		this.pnom = pnom;
	}
	public Etudiant(Long id, String nom, String pnom,String code) {
		super();
		this.id = id;
		this.nom = nom;
		this.pnom = pnom;
		this.code = code;
	}
	public Etudiant(String nom, String pnom, String code, Date date_naiss) {
		super();
		this.nom = nom;
		this.pnom = pnom;
		this.code = code;
		this.date_naiss = date_naiss;
	}
	String sexe ; 
	public Etudiant(Long id, String nom, String pnom,String code,String s) {
		super();
		this.id = id;
		this.nom = nom;
		this.pnom = pnom;
		this.code = code;
		sexe=s;
	}
	
	

}

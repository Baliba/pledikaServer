package Palmares;

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

}

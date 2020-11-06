package dto;

import java.io.Serializable;
import java.util.Date;

import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.PVersement;

import lombok.Data;

@Data
public class User implements Serializable {
	private Long id;
	private String code, sexe,  lastName,firstName, classe, role;
	boolean checkbox;
	int bourse;
	Long idu;
	double moy_pass;
	Date date_naiss;
	String lieu_de_naiss;
	String matricule, identifiant, nom_mere , annee_six, annee_neuv, annee_rheto, annee_philo;
	public User(Long id, String code, String sexe, String lastName, String firstName, String classe, String role,
			boolean checkbox, int bourse) {
		super();
		this.id = id;
		this.code = code;
		this.sexe = sexe;
		this.lastName = lastName;
		this.firstName = firstName;
		this.classe = classe;
		this.role = role;
		this.checkbox = checkbox;
		this.bourse = bourse;
	}
	public User(Long id, String code, String sexe, String lastName, String firstName, String classe, String role,
		boolean checkbox) {
		super();
		this.id = id;
		this.code = code;
		this.sexe = sexe;
		this.lastName = lastName;
		this.firstName = firstName;
		this.classe = classe;
		this.role = role;
		this.checkbox = checkbox;
	}
	
	public User(Long id, String code, String sexe, String lastName, String firstName, String classe, String role,
			boolean checkbox, int bourse, Long idu) {
		super();
		this.id = id;
		this.code = code;
		this.sexe = sexe;
		this.lastName = lastName;
		this.firstName = firstName;
		this.classe = classe;
		this.role = role;
		this.checkbox = checkbox;
		this.bourse = bourse;
		this.idu = idu;
	}
	public User(Long id, String code, String sexe, String lastName, String firstName, String classe, String role,boolean checkbox, int bourse, Long idu, double moy_pass, Date date_naiss,String lieu_de_naiss,String matricule, String identifiant, String nom_mere, String annee_six, String annee_neuv,String annee_rheto, String annee_philo) {
		super();
		this.id = id;
		this.code = code;
		this.sexe = sexe;
		this.lastName = lastName;
		this.firstName = firstName;
		this.classe = classe;
		this.role = role;
		this.checkbox = checkbox;
		this.bourse = bourse;
		this.idu = idu;
		this.moy_pass = moy_pass;
		this.date_naiss = date_naiss;
		this.lieu_de_naiss = lieu_de_naiss;
		this.matricule = matricule;
		this.identifiant = identifiant;
		this.nom_mere = nom_mere;
		this.annee_six = annee_six;
		this.annee_neuv = annee_neuv;
		this.annee_rheto = annee_rheto;
		this.annee_philo = annee_philo;
	}
	
	boolean actived;
	PVersement pversement;
	public User(Long id, String code, String sexe, String lastName, String firstName, String classe, String role,boolean checkbox, int bourse, Long idu, double moy_pass, Date date_naiss,String lieu_de_naiss,String matricule, String identifiant, String nom_mere, String annee_six, String annee_neuv,String annee_rheto, String annee_philo,boolean act) {
		super();
		this.id = id;
		this.code = code;
		this.sexe = sexe;
		this.lastName = lastName;
		this.firstName = firstName;
		this.classe = classe;
		this.role = role;
		this.checkbox = checkbox;
		this.bourse = bourse;
		this.idu = idu;
		this.moy_pass = moy_pass;
		this.date_naiss = date_naiss;
		this.lieu_de_naiss = lieu_de_naiss;
		this.matricule = matricule;
		this.identifiant = identifiant;
		this.nom_mere = nom_mere;
		this.annee_six = annee_six;
		this.annee_neuv = annee_neuv;
		this.annee_rheto = annee_rheto;
		this.annee_philo = annee_philo;
		actived = act;
	}
	
	
	
	

}

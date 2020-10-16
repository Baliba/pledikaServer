package models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class MUser implements Serializable {
  Long id;
  String code; 
  String lastName, firstName,sexe;
  Date date_de_naiss;
  String username;
  String current_promo;
  boolean enabled;
  String  phone;

public MUser(Long id, String code, String lastName, String firstName, String sexe, Date date_de_naiss, String username,
		String current_promo, boolean enabled, String phone) {
	super();
	this.id = id;
	this.code = code;
	this.lastName = lastName;
	this.firstName = firstName;
	this.sexe = sexe;
	this.date_de_naiss = date_de_naiss;
	this.username = username;
	this.current_promo = current_promo;
	this.enabled = enabled;
	this.phone = phone;
}

}

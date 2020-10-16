package models;

import com.hist.BysApp.entities.member.UserEntity;

import lombok.Data;

@Data 
public class MParcours {
   String cprom_name;
   String promo_name;
   Long id;
public MParcours(String cprom_name, String promo_name, Long id) {
	super();
	this.cprom_name = cprom_name;
	this.promo_name = promo_name;
	this.id = id;
}
   
   
   
}

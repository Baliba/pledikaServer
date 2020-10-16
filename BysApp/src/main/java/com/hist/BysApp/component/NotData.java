package com.hist.BysApp.component;

import com.hist.BysApp.entities.member.UserEntity;

public class NotData {
	public static  String TFPASS = "Demande de réinitialisation de mot de passe";
	public static  String NEW_USER_TO_ADMIN="Nouvelle admission {user}" ;
	public static  String PAYMENT="Nouvelle admission {user}" ;
	public static  String PAYROLL_TO_ADMIN="Nouvelle admission {user}" ;
	public static  String PAYROLL_TO_PROF="{user} le payroll pour le mois de {mois} est pret." ;
	public static  String FPASS ="Bonjour, je suis {user}, j'aimerait reinitialiser mon mot de passe";
	public static String getFPASS(UserEntity user){
		return FPASS.replace("{user}",user.getFirstName()+" "+user.getLastName());
	}
	public static String getBtn(String url,String text,String cls) {
		// TODO Auto-generated method stub
		return "<a class='btn text-light "+cls+"' target='_blank' href='"+url+"' >"+text+"</a>";
	}
}

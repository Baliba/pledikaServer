package com.hist.BysApp.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.mapping.Collection;

import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.member.UserEntity;

public class StaticData {
	public static String client = "http://localhost:4200/";
	
	// DOMAINE 
	public static  List<String> TYPE_PAYROLL = Arrays.asList("CASH","CHEQUE","VIREMENT");
	
	// USER-INFO
	public static String fname = "Master";
	public static String lname = "Pledika";
	public static String pass  = "Loppe@2017;";
	public static String email = "master@pledika.com";
	
	// DOMAINE 
    public static  List<DocConfig> docConfig = Arrays.asList(
				new DocConfig("Acte de naissance","AN"),
				new DocConfig("Carte Vaccination","CV"),
				new DocConfig("Ancienne Relevée de note","AR"),
				new DocConfig("Concours D'examen","CDE")
	);
	// DOMAINE 
	public static  List<DomConfig> domConfig = Arrays.asList(
			new DomConfig("Etude Classique","EC")
	);
	//OPTION
	public static List<OptionConfig> optionConfig =  Arrays.asList (
			new OptionConfig("Kindergarten","JE",3,"MA","EC",3,1000),
			new OptionConfig("Premier Cycle","PC",3,"AF","EC",1,2000),
			new OptionConfig("Deuxieme Cycle","DC",3,"AF","EC",1,2000),
			new OptionConfig("Troisieme Cycle","TC",3,"AF","EC",1,2000),
			new OptionConfig("Nouveau Secondaire","NS",4,"AF","EC",2,2000)
    );
	public static HashMap<String,List<OptionConfig>> domopt = new HashMap<>();
    // NIVEAU 
	public static HashMap<String,List<Niveau>> niveau = new HashMap<>();
	// ETAB 
	public static String code_etab   = "E1";
	public static String name_etab   = "College Pledika";
	public static String cat_etab    = "Ecole Classique";
	public static String annee_fond  = "2009";
	public static String email_etab  = "***@***.***";
	public static String phone_a     = "(509)2227-1616";
	public static String phone_b     = "";
	public static String phone_c     = "";
	public static String description = "";
    public static String website     = "www.pledika.com";
    public static String adresse     = "13# Delmas 83";
    
    // ng serve --host 172.20.10.2 --port 4201
    
    public static HashMap<String,List<Niveau>> setNiveau() {
	
    	  int pos = 1;
    	  for(OptionConfig oc : optionConfig) {
    	     List<Niveau> nivs = new ArrayList<Niveau> ();
    	     
    	     for(int i = 1; i<= oc.getOnc(); i++) {
	    	     Niveau n;
	    	     if(oc.getMethod()==1) {
	    	    	 n = new Niveau(setopt(pos),i+"-"+oc.getOpt_prefix(),pos); 
	    	    	 pos++;
	    	     } else if (oc.getMethod()==2) {
	    	    	 n = new Niveau(oc.getOption()+" "+i, i+"-"+oc.getOpt_prefix(),pos); 
	    	    	 pos++;
	    	     }else {
	    	    	 n = new Niveau(oc.getOpt_prefix()+" "+i, i+"-"+oc.getOpt_prefix(),pos);  
	    	     }
    	   
    	      nivs.add(n);
    	     }
    	     niveau.put(oc.getOpt_code(),nivs);
    	  }
    	return niveau ;
   }
    public static String  setopt(int i) {
    	if(i>1) {
    	 return i+"eme Année Fondamentale";
    	}
    	 return i+"ere Année Fondamentale";
    }
    
    public static HashMap<String,List<OptionConfig>> setupOption() {
    	
       for(DomConfig dc : domConfig) {
    	   
    	   List<OptionConfig> ocl =  new ArrayList<OptionConfig> ();
    	   for(OptionConfig oc : optionConfig) {	
    	      if(oc.getDom_code().equals(dc.getDom_code())) {
    	    	  ocl.add(oc);
    	      }
    	   }
    	   
           domopt.put(dc.getDom_code(),ocl);
       }
       return domopt;
    }
    

}

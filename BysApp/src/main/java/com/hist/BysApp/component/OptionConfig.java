package com.hist.BysApp.component;


import lombok.Data;
@Data
public class OptionConfig {
	public  String  option;
	public  String  opt_code;
	public  int     onc; 
	public  String  opt_prefix ;
	public  String  dom_code;
	public  int     method;
	public  double  montant_admis;
	public OptionConfig() {}
	public OptionConfig(String option, String opt_code, int onc, String opt_prefix, String code_classe, int method,double m) {
		this.option = option;
		this.opt_code = opt_code;
		this.onc = onc;
		this.opt_prefix = opt_prefix;
		this.dom_code = code_classe;
		this.method = method;
		this.setMontant_admis(m);
	}
	
	 
}

package com.hist.BysApp.component;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
@Data
public class DomConfig {
	
	public String  domaine;
	public String  dom_code;
	public DomConfig(String domaine, String dom_code) {
		this.domaine = domaine;
		this.dom_code = dom_code;
	}
	

}

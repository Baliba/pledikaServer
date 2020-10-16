package com.hist.BysApp.component;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
@Data
public class DocConfig {
	
	public String  document;
	public String  doc_code;
	public DocConfig(String domaine, String doc_code) {
		this.document = domaine;
		this.doc_code = doc_code;
	}
	

}

package com.hist.BysApp.Response;

import java.util.List;

import com.hist.BysApp.entities.paiement.Caisse;

import lombok.Data;

@Data
public class CaisseResp {
	 List<Caisse> csm;
     List<Caisse> csc;
	public CaisseResp(List<Caisse> csm, List<Caisse> csc) {
		super();
		this.csm = csm;
		this.csc = csc;
	}
	
}

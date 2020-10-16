package com.hist.BysApp.Response;

import java.util.List;

import com.hist.BysApp.entities.paiement.PVersement;

import lombok.Data;

@Data
public class RPayment {
	private Long id_payment; 
	private double  remain;
	private List<PVersement> pv;

}

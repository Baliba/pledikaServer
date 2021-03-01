package com.hist.BysApp.Response;

import java.util.List;

import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.promo.Promo_af;

import lombok.Data;

@Data
public class RStat {
	private int total_student;
	private int total_prof;
	private int total_fille;
	private int total_garcon;
	private int total_pers;
	private Long total_cours;
	private int total_promo;
	private Promo_af af;
	private int total_parent;

}

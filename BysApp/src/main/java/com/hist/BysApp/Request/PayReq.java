package com.hist.BysApp.Request;

import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.PRFrag;

import lombok.Data;
@Data
public class PayReq {
	public Long user;
	public Long id_mois;
	public String code;
	public String tpay;
	
	// UPDATE `user_entity` SET `salairy`=100 WHERE 1
	
}

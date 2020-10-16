package com.hist.BysApp.model;

import java.util.HashMap;
import java.util.List;

import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.PromoFrag;
import com.hist.BysApp.entities.promo.Results;

import lombok.Data;

@Data
public class BulletinGeneral {
	
	  HashMap<Long,HashMap<Long,List<Results>>>  results;
	  HashMap<Long,Parcours> parcours = new HashMap<>(); 
	  HashMap<String,PromoFrag> promofrag = new HashMap<>();
	  int npu ;

}

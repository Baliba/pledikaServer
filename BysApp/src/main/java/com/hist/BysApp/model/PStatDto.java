
package com.hist.BysApp.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.hist.BysApp.entities.promo.Parcours;

import lombok.Data;
@Data
public class PStatDto  {
    
	private  String promotion;
    public PStatDto(String promotion, Long f, Long  m) {
		super();
		this.promotion = promotion;
		this.f = f;
		this.m = m;
	}
    public PStatDto(String promotion) {
		super();
		this.promotion = promotion;
		this.f = 0L;
		this.m = 0L;
	}
	private  Long f;
    private  Long m;
}

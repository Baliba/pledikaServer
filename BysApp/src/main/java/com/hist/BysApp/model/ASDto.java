
package com.hist.BysApp.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.hist.BysApp.entities.promo.Parcours;

import lombok.Data;
@Data
public class ASDto  {
    public ASDto(String code, Collection<Parcours> parcours) {
		super();
		this.code = code;
		this.parcours = parcours;
	}
	private  String code;
    private  Collection<Parcours> parcours;
}

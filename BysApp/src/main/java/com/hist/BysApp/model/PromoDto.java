package com.hist.BysApp.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.entities.grade.Niveau_rel;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.PromoFrag;
import com.hist.BysApp.entities.promo.Promo_af;
import com.hist.BysApp.entities.promo.Promo_cours;

import lombok.Data;
@Data
public class PromoDto {
	
    public PromoDto(Long id, String code, String code_niveau, boolean reprise) {
		super();
		this.id = id;
		this.code = code;
		this.code_niveau = code_niveau;
		this.reprise = reprise;
	}
    
    public PromoDto(Long id, String code, String code_niveau, boolean reprise, Long prev_promo,
    		String prev_promo_name,String code_cycle, double moy_accept,  int moy_total, Long np,String npn) {
		super();
		this.id = id;
		this.code = code;
		this.code_niveau = code_niveau;
		this.reprise = reprise;
		this.prev_promo=prev_promo;
		this.prev_promo_name = prev_promo_name;
		this.code_cycle = code_cycle;
		this.moy_accept = moy_accept;
		this.moy_total = moy_total;
		this.next_promo= np;
		this.next_promo_name = npn;
	}
    
    public PromoDto(Long id, String code, String code_niveau, boolean reprise, Long prev_promo, String prev_promo_name,String code_cycle, double moy_accept,  int moy_total) {
		super();
		this.id = id;
		this.code = code;
		this.code_niveau = code_niveau;
		this.reprise = reprise;
		this.prev_promo=prev_promo;
		this.prev_promo_name = prev_promo_name;
		this.code_cycle = code_cycle;
		this.moy_accept = moy_accept;
		this.moy_total = moy_total;
	}
		private Long id;
	    @Column(unique=true)
	    private String code;
	    private String code_niveau;
	    boolean reprise;
	    private String board,next_promo_name;
	    private Long next_promo;
	    private String prev_promo_name;
	    private Long prev_promo;
	    private String code_cycle;
	    private double moy_accept; int  moy_total;
		public PromoDto(Long id, String code) {
			super();
			this.id = id;
			this.code = code;
		}
		public PromoDto(Long id, String code, String code_niveau) {
			super();
			this.id = id;
			this.code = code;
			this.code_niveau = code_niveau;
		}
	    
}

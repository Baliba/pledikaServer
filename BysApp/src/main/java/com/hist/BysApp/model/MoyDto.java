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
public class MoyDto {

	    private double note, total;
        private String nom,pnom,code;
        private Long id;
        private boolean isSelected = false;
        private boolean actived;
        private Long idp;
        int  moy_total ; double moy_accept; double moy_rep;
        double moy_after_rep;
        double note_ar, total_ar;
		public MoyDto(float note, float total) {
			super();
			this.note = note;
			this.total = total;
		}
		boolean lover;
		public MoyDto(double note, double total, String nom, String pnom, String code, Long i, boolean actived) {
			super();
			this.note = note;
			this.total = total;
			this.nom = nom;
			this.pnom = pnom;
			this.code = code;
			id = i;
			this.actived = actived;
		}
		public MoyDto(double note, double total, String nom, String pnom, String code, Long i, boolean actived, Long idu, int moy_total, double moy_accept,boolean l) {
			super();
			this.note = note;
			this.total = total;
			this.nom = nom;
			this.pnom = pnom;
			this.code = code;
			id = i;
			this.actived = actived;
			this.idp = idu;
			this.moy_total = moy_total;
			this.moy_accept = moy_accept;
			lover = l;
		}
		
		public MoyDto(double note, double total, String nom, String pnom, String code, Long i, boolean actived, Long idu, int moy_total, double moy_accept,boolean l,double mr) {
			super();
			this.note = note;
			this.total = total;
			this.nom = nom;
			this.pnom = pnom;
			this.code = code;
			id = i;
			this.actived = actived;
			this.idp = idu;
			this.moy_total = moy_total;
			this.moy_accept = moy_accept;
			lover = l;
			moy_rep = mr;
		}
		
		
		
		
		
		 
}

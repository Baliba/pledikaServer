/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import java.util.List;

import javax.validation.constraints.Past;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.hist.BysApp.entities.paiement.OPaie;
import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.paiement.Versement;
import com.hist.BysApp.entities.promo.Parcours;

@CrossOrigin("*")
@RepositoryRestResource
public interface PVDao extends JpaRepository<PVersement, Long> {
	PVersement findByName( String name);
	
	@Query("SELECT p FROM PVersement p where p.parcours.id=:id ORDER BY p.pos ASC ")
    List<PVersement> getVerseForParcours(@Param("id") Long id); 
	
	@Query("SELECT p FROM PVersement p where  p.parcours.promotion.id=:idp AND  p.parcours.user.id=:idu ORDER BY p.pos ASC ")
    List<PVersement> getVerseForPromoAndUser(@Param("idp") Long idp, @Param("idu") Long idu); 
	
	@Query("SELECT p FROM PVersement p where p.id=:id ORDER BY p.pos ASC ")
    PVersement getVerse(@Param("id") Long id); 
	
	@Query("SELECT SUM(p.montant_to_pay) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id ")
	float  rapportGain(@Param("id") Long id);
	
	@Query("SELECT SUM(p.montant_pay) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id ")
	float  rapportRealGain(@Param("id") Long id);
	
	@Query("SELECT SUM(p.montant_init) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id AND p.parcours.granted!=0 ")
	float  rapportBourse(@Param("id") Long id);
	
	
	@Query("SELECT SUM(p.montant_to_pay) FROM PVersement p WHERE p.actived=true AND   p.parcours.promotion.id=:id AND p.type_verse!=3 ")
	float  rapportGainPromo(@Param("id") Long id);
	
	@Query("SELECT SUM(p.montant_pay) FROM PVersement p WHERE p.actived=true AND  p.parcours.promotion.id=:id AND p.type_verse!=3 ")
	float  rapportRealGainPromo(@Param("id") Long id);
	
	@Query("SELECT SUM(p.montant_pay) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id AND p.parcours.granted=2")
	float  rapportRealGainDB(@Param("id") Long id);
	
	@Query("SELECT SUM(p.montant_to_pay) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id AND p.parcours.granted=2")
	float  rapportGainDB(@Param("id") Long id);
	
	@Query("SELECT SUM(p.montant_init) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id AND p.parcours.granted=2")
	float  rapportToWinDB(@Param("id") Long id);
	
	
	@Query("SELECT SUM(p.montant_init) FROM PVersement p WHERE p.actived=true AND p.type_verse!=3 AND p.parcours.promotion.promo_af.id=:id AND p.parcours.granted=1")
	float  rapportGainCB(@Param("id") Long id);
	
}


package com.hist.BysApp.dao;

import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.OPaie;
import com.hist.BysApp.entities.promo.Promotion;
import com.hist.BysApp.model.ASDto;
import com.hist.BysApp.model.PromoDto;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author User
 */
@CrossOrigin("*")
@RepositoryRestResource
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	 Promotion findByCode( String code);
	 
	 @Query("SELECT  p FROM Promotion p WHERE p.titulaire.id=:id AND   enabled=true")
	 List<Promotion> getPromoForProf(@Param("id") Long id);
	 
	 @Query("SELECT  new com.hist.BysApp.model.PromoDto(p.id,p.code) FROM Promotion p WHERE p.id!=:idp AND enabled=true AND  p.niveau_rel.niveau.code=:id")
	 List<PromoDto> getPromo(@Param("idp") Long idp,@Param("id") String id);
	 
	 @Query("SELECT new com.hist.BysApp.model.PromoDto(p.id,p.code,p.code_niveau,p.reprise,p.prev_promo,p.prev_promo_name,p.code_cycle,p.moy_accept, p.moy_total, p.next_promo, p.next_promo_name) FROM Promotion p WHERE p.promo_af.id=:id ORDER BY p.code ASC ")
	 List<PromoDto> getPromoByAF(@Param("id") Long id);
	 
	 @Query("SELECT new com.hist.BysApp.model.PromoDto(p.id,p.code,p.code_niveau,p.reprise) FROM Promotion p WHERE p.promo_af.id=:id AND p.niveau_rel.id=:nr ORDER BY p.code ASC  ")
	 List<PromoDto> getPromoByAFV2(@Param("id") Long id, @Param("nr") Long nr );
	 
	 @Query("SELECT  new com.hist.BysApp.model.PromoDto(p.id,p.code) FROM Promotion p WHERE p.id!=:idp AND enabled=true AND  p.niveau_rel.niveau.code=:id AND p.promo_af.id=:ida ")
	 List<PromoDto> getPromoV2(@Param("idp") Long idp,@Param("id") String id, @Param("ida") Long ida);
	 
	 @Query("SELECT  new com.hist.BysApp.model.PromoDto(p.id,p.code) FROM Promotion p WHERE p.id!=:id  AND  p.niveau_rel.niveau.code=:code AND p.promo_af.id=:py AND p.next_promo IS NULL  ")
	 List<PromoDto> getPrevPromo(@Param("py") Long prev_year,@Param("id") Long id, @Param("code") String code);
	 
	 @Query("SELECT  p FROM Promotion p WHERE  enabled=true AND  p.niveau_rel.id=:idn AND p.promo_af.id=:id ")
	 Promotion getSamePromo(@Param("id") Long id, @Param("idn") Long idn);
	
	 
	 @Query("SELECT p.promo_af.next_year FROM Promotion p Where p.id=:id ")
	 Long getAFByPromo(@Param("id") Long id);
	 
}

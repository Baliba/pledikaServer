/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.Response.ResultRespose;
import com.hist.BysApp.entities.paiement.OPaie;
import com.hist.BysApp.entities.promo.Parcours;

import models.MParcours;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface ParcoursRepository extends JpaRepository<Parcours, Long> {
	Parcours findByCode( String code);
	
	@Modifying
	@Transactional
	@Query("UPDATE Parcours p SET p.actived = false WHERE p.id!=:id")
    void close(@Param("id") Long id);
	
	@Query("SELECT p FROM Parcours p where p.promotion.id=:id ORDER BY p.nom ASC")
    List<Parcours> getParcours(@Param("id") Long id); 
	
	@Query("SELECT p FROM Parcours p where p.promotion.id=:id AND p.actived = true ORDER BY p.nom ASC")
    List<Parcours> getActParcours(@Param("id") Long id); 
	
	@Query("SELECT new models.MParcours(p.user.current_promo,p.promo_name,id_student) FROM Parcours p where  p.actived = true ORDER BY p.nom ASC")
    List<MParcours> getAllActParcours();   
	
	@Transactional
	@Query("DELETE FROM Parcours p  WHERE p.id=:id")
    void del(@Param("id") Long id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Parcours p SET p.actived=:state WHERE p.promotion.id=:id")
    void closeByPromo(@Param("id") Long id,@Param("state") boolean state);
	
	@Modifying
	@Transactional
	@Query("UPDATE Parcours p SET p.actived = :state WHERE p.code_student=:code AND p.id != :id  ")
    void closeOne(@Param("id")  Long id, @Param("code") String code, @Param("state") boolean state );
	
	@Query("SELECT p FROM Parcours p where p.actived = :state AND p.promotion.niveau_rel.niveau.code=:code ")
    List<Parcours> getAllParcours(@Param("state") boolean state, @Param("code") String code); 
	
	@Query("SELECT p FROM Parcours p where p.id =:id ")
    Parcours getOneParcours(@Param("id") Long id);
	
	@Query("SELECT p FROM Parcours p where p.code_student=:code ")
	List<Parcours> getParcoursByCodeStudent(@Param("code") String  code);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE  Parcours p SET p.pnom=:fname, p.nom =:lname WHERE p.code_student=:code ")
    void editFullName(@Param("code") String code,@Param("fname") String firstName ,@Param("lname") String lastName);
	    
    @Query("SELECT p  FROM Parcours p  WHERE p.promotion.promo_af.id =:id AND p.promotion.enabled=true ")
    List<Parcours> getAllParcours(Long id);
    
    @Query("SELECT  COUNT(p)  FROM Parcours p  WHERE p.promotion.id =:id  AND p.sexe=:sexe  ")
	Long  getBySexe(Long id, String sexe);
    
    @Modifying
	@Transactional
	@Query("UPDATE Parcours p SET p.actived=:state WHERE p.promotion.id=:id AND p.code_student=:code  ")
    void closeOneByPromo(@Param("id") Long id,@Param("code") String code, @Param("state") boolean state);
}

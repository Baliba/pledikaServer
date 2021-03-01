/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.Response.ResultRespose;
import com.hist.BysApp.entities.paiement.OPaie;
import com.hist.BysApp.entities.promo.Parcours;

import dto.User;
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
	
	@Modifying
	@Transactional
	@Query("UPDATE Parcours p SET p.actived =:s WHERE p.id=:id")
    void closeOneForEnd(@Param("id") Long id,@Param("s") boolean  s );
	
	@Query("SELECT p FROM Parcours p where p.promotion.id=:id ORDER BY p.nom ASC")
    List<Parcours> getParcours(@Param("id") Long id); 
	
	
	@Query("SELECT p FROM Parcours p where p.promotion.id=:id AND p.id=:idu ORDER BY p.nom ASC")
    List<Parcours> getOParcours(@Param("id") Long id, @Param("idu") Long idu); 
	
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
	
	@Query("SELECT new dto.User(p.id, p.user.code, p.user.sexe, p.user.lastName,p.user.firstName, p.promo_name,'STUDENT', false, p.user.granted, p.user.id) FROM Parcours p where p.actived = :state AND p.promotion.niveau_rel.niveau.code=:code AND p.promotion.promo_af.id=:id ")
    List<User> getAllParcoursNew(@Param("state") boolean state, @Param("code") String code, @Param("id") Long Long); 
	
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
    @Modifying
   	@Transactional
	@Query("UPDATE Parcours p SET id_promo=:id WHERE p.promotion.id=:id")
	void arrageId(@Param("id")Long id);
    
    @Modifying
	@Transactional
	@Query("UPDATE Parcours p SET p.moy_final =:s, p.decision=:d WHERE p.id=:id")
    void setDec(@Param("id") Long id,@Param("d") Integer  d,@Param("s") double s );
    
	
	@Query("SELECT new dto.User(p.id, p.user.code, p.user.sexe, p.user.lastName,p.user.firstName, p.promo_name,'STUDENT', false, p.user.granted, p.user.id, p.moy_final, p.user.date_de_naiss, p.user.pob ,p.user.matricule,p.user.identifiant, p.user.nom_mere, p.user.annee_six, p.user.annee_neuv, p.user.annee_rheto, p.user.annee_philo,p.actived) FROM Parcours p WHERE  p.promotion.id=:idp ORDER BY p.user.lastName, p.user.firstName ")
    List<User> getDecisionFinale(@Param("idp") Long  idp); 
	
	@Query("SELECT new dto.User(p.id, p.user.code, p.user.sexe, p.user.lastName,p.user.firstName, p.promo_name,'STUDENT', false, p.user.granted, p.user.id, p.moy_final, p.user.date_de_naiss, p.user.pob ,p.user.matricule,p.user.identifiant, p.user.nom_mere, p.user.annee_six, p.user.annee_neuv, p.user.annee_rheto, p.user.annee_philo, p.actived) FROM Parcours p WHERE  p.promotion.id=:idp ORDER BY p.user.lastName, p.user.firstName ")
    List<User> getParcoursNew(@Param("idp") Long  idp); 
	
	 @Query("SELECT new dto.User(p.id, p.user.code, p.user.sexe, p.user.lastName,p.user.firstName, p.promo_name,'STUDENT', false, p.user.granted, p.user.id, p.moy_final, p.user.date_de_naiss, p.user.pob ,p.user.matricule,p.user.identifiant, p.user.nom_mere, p.user.annee_six, p.user.annee_neuv, p.user.annee_rheto, p.user.annee_philo,p.actived)  FROM Parcours p  WHERE p.promotion.promo_af.id =:id AND p.promotion.enabled=true ")
	 List<User> getAllParcoursU(Long id);
	 
	 
	 @Query("SELECT COUNT(p) FROM Parcours p WHERE p.promotion.id=:idp")
	 Long  countStudent(@Param("idp") Long idp);
	 
	 @Query("SELECT COUNT(p) FROM Parcours p WHERE p.promotion.id=:idp AND p.user.sexe='M' ")
	 Long  countStudentBySexe(@Param("idp") Long idp);

}

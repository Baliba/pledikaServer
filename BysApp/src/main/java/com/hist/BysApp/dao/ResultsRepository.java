/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.Response.ResultRespose;
import com.hist.BysApp.entities.promo.Frag_cours;
import com.hist.BysApp.entities.promo.Results;
import com.hist.BysApp.model.MoyDto;

import Palmares.MResults;
import Palmares.NResults;


/**
 *
 * @author User
 */
@CrossOrigin("*")
@RepositoryRestResource
public interface ResultsRepository extends JpaRepository<Results, Long> {
	
	
	
	
	
	
	@Query("SELECT new Palmares.NResults(r.id,r.code_student,r.parcours_frag.parcours.user.lastName,r.parcours_frag.parcours.user.firstName,r.note_total,r.note,r.coef)  FROM Results r where r.frag_cours.id=:id ORDER BY r.parcours_frag.parcours.user.lastName,r.parcours_frag.parcours.user.firstName ")
    List<NResults> getEtudiants(@Param("id") Long id); 
	
    public  Results findByCode(String code);
	 
	 @Query("SELECT r  FROM Results r where r.parcours_frag.id=:id AND  r.frag_cours.examen=true ORDER BY r.frag_cours.name ASC ")
	 List<Results> getBulletin(@Param("id") Long id); 
	 
	 @Query("SELECT  SUM(r.coef*r.note_total)  FROM Results r where r.frag_cours.promofrag.id=:id ")
	 float getNoteTotalGen(@Param("id") Long id); 
	 
	 @Query("SELECT  COUNT(r) FROM Results r where r.frag_cours.promofrag.id=:id ")
	 int isReady(@Param("id") Long id); 
	 
	 @Query("SELECT  SUM(r.coef*r.note)   FROM Results r where r.frag_cours.promofrag.id=:id ")
	 float getNoteGen(@Param("id") Long id); 
	 
//	 @Modifying(flushAutomatically = true, clearAutomatically = true)
//	
//	 @Query("UPDATE Results r SET r.pnom=:fname, r.nom =:lname WHERE r.code_student=':code' ")
//	 public int editFullName(@Param("code") String code,@Param("fname") String firstName ,@Param("lname") String lastName);
//	
	 @Modifying(flushAutomatically = true, clearAutomatically = true)
	 @Transactional
	 @Query(value = "update Results set pnom=:fname, nom =:lname where  code_student=:code", nativeQuery = true)
	 public int editFullName(@Param("code") String code,@Param("fname") String firstName ,@Param("lname") String lastName);
		
	 
	 @Query("SELECT r  FROM Results r where r.parcours_frag.promofrag.id=:frag AND r.parcours_frag.parcours.id=:user AND  r.frag_cours.examen=true ORDER BY r.frag_cours.name ASC ")
	 List<Results> getBulletinGen(@Param("user") Long user, @Param("frag") Long frag); 
	 //@Query("SELECT new com.hist.BysApp.model.MoyDto(SUM(r.coef*r.note),SUM(r.coef*r.note_total),r.nom,r.pnom,r.code_student,r.parcours_frag.parcours.id_student) FROM Results r where  r.parcours_frag.parcours.actived=true AND r.parcours_frag.promofrag.actived=true AND r.parcours_frag.promofrag.base=true AND r.parcours_frag.parcours.promotion.id=:id GROUP BY r.code_student  ")
	 
	 @Query("SELECT new com.hist.BysApp.model.MoyDto(SUM(r.coef*r.note),SUM(r.coef*r.note_total),r.nom,r.pnom,r.code_student,r.parcours_frag.parcours.id_student,r.parcours_frag.parcours.actived) FROM Results r where  r.parcours_frag.parcours.actived=true AND r.parcours_frag.promofrag.actived=true AND r.parcours_frag.promofrag.base=true AND r.parcours_frag.parcours.promotion.id=:id  GROUP BY r.nom, r.pnom,r.code_student,r.parcours_frag.parcours.id_student,r.parcours_frag.parcours.actived  ")
	 List<MoyDto> getEtudiantsToUpgrade(@Param("id") Long id);
	 
	 
	 @Query("SELECT new Palmares.MResults(r.frag_cours.id, r.frag_cours.name,r.note, r.coef, r.note_total,r.id)  FROM Results r where r.parcours_frag.promofrag.id=:frag AND r.parcours_frag.id=:user ORDER BY r.frag_cours.name ASC ")
	 List<MResults> getResults(@Param("user") Long user, @Param("frag") Long frag); 
 
}

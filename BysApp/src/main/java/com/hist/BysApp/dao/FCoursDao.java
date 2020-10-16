/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.hist.BysApp.entities.promo.Frag_cours;

import Palmares.Cours;

@CrossOrigin("*")
@RepositoryRestResource
public interface FCoursDao extends JpaRepository<Frag_cours, Long> {
	 public  Frag_cours findByCode(String code);
	
	 @Query("SELECT c FROM Frag_cours c WHERE c.id_cours=:id AND c.examen = true AND c.promofrag.actived=true AND  c.promofrag.promotion.enabled=true ")
	 List<Frag_cours> getCoursByProf(@Param("id") Long id);
	 
	 
	 @Query("SELECT new Palmares.Cours(c.id, c.code, c.name,c.coef,c.note_total) FROM Frag_cours c WHERE  c.promofrag.id = :id ORDER BY c.name ASC   ")
	 List<Cours> getCoursByFrag(@Param("id") Long id);

}

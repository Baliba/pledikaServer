/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.promo.Parcours_frag;

import Palmares.Etudiant;

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
public interface FParcoursRepository extends JpaRepository<Parcours_frag, Long> {
	Parcours_frag findByCode( String code);
	@Modifying
	@Transactional
	@Query("UPDATE Parcours_frag p SET p.actived = false WHERE p.id!=:id")
    void close(@Param("id") Long id);
	
    @Query("SELECT p FROM Parcours_frag p where p.parcours.promotion.id=:id")
    List<Parcours_frag> getFParcours(@Param("id") Long id); 
   
    @Query("SELECT p FROM Parcours_frag p where p.promofrag.id=:id")
    List<Parcours_frag> getRFParcours(@Param("id") Long id); 
    
    @Query("SELECT new  Palmares.Etudiant (p.id, p.parcours.user.lastName, p.parcours.user.firstName,p.code_student)  FROM Parcours_frag p where p.promofrag.id=:id ORDER BY p.nom, p.pnom")
    List<Etudiant> getEtudiants(@Param("id") Long id); 
}

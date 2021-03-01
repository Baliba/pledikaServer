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

import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.classe.ClasseOPaie;

@CrossOrigin("*")
@RepositoryRestResource
public interface CLOPaieDao extends JpaRepository<ClasseOPaie, String> {
	
	ClasseOPaie findByCode( String code);
	
	@Query("SELECT c FROM ClasseOPaie c WHERE c.niveau.code=:code")
	List<ClasseOPaie> getCops(@Param("code") String code);
}

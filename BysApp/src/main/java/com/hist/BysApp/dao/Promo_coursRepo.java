/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.grade.Option;
import com.hist.BysApp.entities.promo.Promo_cours;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author User
 */
@CrossOrigin("*")
@RepositoryRestResource
public interface Promo_coursRepo extends JpaRepository<Promo_cours, Long> {
	
	@Query("SELECT pc FROM Promo_cours pc WHERE promotion.id=:idp")
    List<Promo_cours> getCoursByPromo(@Param("idp") Long idp);
    public  Promo_cours findByCode(String code);
    
}

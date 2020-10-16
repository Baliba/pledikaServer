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

import com.hist.BysApp.entities.promo.Programme;

@CrossOrigin("*")
@RepositoryRestResource
public interface ProgDao extends JpaRepository<Programme, Long> {
	 public  Programme findByCode(String code);
	 @Query("SELECT p FROM Programme p WHERE p.niveau =:n  ")
     List<Programme> getProg(@Param("n") String n);
}

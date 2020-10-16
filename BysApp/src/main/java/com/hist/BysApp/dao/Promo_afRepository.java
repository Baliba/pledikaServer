/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.promo.Promo_af;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface Promo_afRepository extends JpaRepository<Promo_af, Long> {
	@Modifying
	@Transactional
	@Query("UPDATE Promo_af p SET p.actived = false")
    void close();
	
	@Modifying
	@Transactional
	@Query("UPDATE Promo_af p SET p.actived = true WHERE p.id=:id ")
    void open(@Param("id") Long id);
	
	@Query("SELECT  p FROM Promo_af p WHERE p.actived = true")
	Promo_af getActived();
}

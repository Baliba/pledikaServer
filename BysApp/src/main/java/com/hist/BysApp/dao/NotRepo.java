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

import com.hist.BysApp.entities.config.Notification;
import com.hist.BysApp.entities.paiement.OPaie;

@CrossOrigin("*")
@RepositoryRestResource
public interface NotRepo extends JpaRepository<Notification,Long> {
	
	 @Query("SELECT n FROM Notification n WHERE ( id_receiver=:id OR id_receiver=0)  AND statut=false ORDER BY date_rec DESC  ")
     List<Notification> getNotAdmin(@Param("id") Long id);
	
	 @Query("SELECT n FROM Notification n WHERE id_receiver=:id AND statut=false  ORDER BY date_rec DESC ")
     List<Notification> getNot(@Param("id") Long id);
	 
	 @Query("SELECT n FROM Notification n WHERE id_receiver=:id AND code_not=:code")
	 Notification findByCodeAndUserId( @Param("id") Long id,@Param("code") Long code);
	 
	 @Modifying
     @Transactional
	 @Query("UPDATE  Notification n SET n.statut=true WHERE id_receiver=:id AND id=:not AND n.statut=false")
	 void readNot(@Param("id") Long id,@Param("not") Long not);
}

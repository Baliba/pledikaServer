/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.grade.Domaine;
import com.hist.BysApp.entities.paiement.PaiementAdmission;

import antlr.collections.List;
@CrossOrigin("*")
@RepositoryRestResource
public interface AdmisPaieRepo extends JpaRepository<PaiementAdmission,String> {
    public PaiementAdmission findByCode(String name);
    Boolean existsByCode(String code);
    
    @Query("SELECT p FROM PaiementAdmission p WHERE p.pay_by=:id AND :NOW-pay_date<=30")
	public java.util.List<PaiementAdmission> getMyPayment(@Param("id") Long id, @Param("NOW") Date d );
    
}

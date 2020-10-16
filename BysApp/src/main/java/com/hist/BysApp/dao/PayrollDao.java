/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.PRFrag;
import com.hist.BysApp.entities.paiement.Payment;
import com.hist.BysApp.entities.paiement.Payroll;

@CrossOrigin("*")
@RepositoryRestResource
public interface PayrollDao extends JpaRepository<Payroll, Long> {
	Payroll findByCode( String code);
	
	@Query("SELECT p FROM Payroll p WHERE p.id_mois=:mois AND p.id_acad=:year AND receive=:rec ")
	List<Payroll> getPayroll(@Param("mois") Long mois,@Param("year") Long year, @Param("rec")  boolean rec);
	
	@Modifying
	@Transactional
	@Query("UPDATE   Payroll p SET p.pnom=:fname, p.nom =:lname WHERE p.code_user=:code ")
    void editFullName(@Param("code") String code,@Param("fname") String firstName ,@Param("lname") String lastName);

}

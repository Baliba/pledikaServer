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
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.promo.Promotion;

/**
 *
 * @author User
 */
@CrossOrigin("*")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName( String name);
    
	@Query("SELECT  r FROM Role r WHERE  r.name=:name ")
	Role getProfRole(@Param("name") String  name );
	
	@Query("SELECT  r FROM Role r WHERE  r.name= 'ADMIN' OR  r.name= 'PROF'  OR r.name= 'ACCOUNTING' OR r.name= 'MANAGER' ")
	List<Role> getUsers();
}

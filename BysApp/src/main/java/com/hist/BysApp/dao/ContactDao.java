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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.Contact;
import com.hist.BysApp.entities.member.UserEntity;

@CrossOrigin("*")
@RepositoryRestResource
public interface ContactDao extends JpaRepository<Contact, Long> {
	
	@Query("SELECT c FROM Contact c WHERE read=:r ORDER BY id DESC")
	Page<Contact> getContact(Pageable paging,@Param("r") boolean read);



}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.Location.Pays;
import com.hist.BysApp.entities.grade.Document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author User
 */
@CrossOrigin("*")
@RepositoryRestResource
public interface PaysRepository extends JpaRepository<Pays, Long> {
	public  Pays findByCode(String code);
}

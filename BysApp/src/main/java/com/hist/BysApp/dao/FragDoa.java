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

import com.hist.BysApp.entities.grade.Document;
import com.hist.BysApp.entities.grade.Domaine;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.promo.Fragment;
@CrossOrigin("*")
@RepositoryRestResource
public interface FragDoa extends JpaRepository<Fragment, Long> {
	 public  Fragment findByName(String name);
	
	 @Query("SELECT f FROM Fragment f where f.actived=true ")
	 List<Fragment> getFrag(); 
}

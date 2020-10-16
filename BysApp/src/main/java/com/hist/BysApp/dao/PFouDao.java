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

import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.promo.Frag_cours;
import com.hist.BysApp.entities.promo.ProgFourniture;

@CrossOrigin("*")
@RepositoryRestResource
public interface PFouDao extends JpaRepository<ProgFourniture, Long> {
	    Boolean existsByCode(String code);
	    public  ProgFourniture findByCode(String code);
}

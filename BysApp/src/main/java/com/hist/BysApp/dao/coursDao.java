/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.promo.Course;
import com.hist.BysApp.projection.CourseView;

import dto.DCours;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface coursDao extends JpaRepository<Course, Long> {
	 @Query("SELECT c FROM Course c WHERE c.id NOT IN (SELECT pc.course.id FROM Promo_cours pc WHERE pc.promotion.id=:id) ")
      List<Course> getCours(@Param("id") Long id);
	 
	 
	 
	 
	 @Query("SELECT new dto.DCours(c.id,c.code) FROM Course c WHERE c.option=:o ")
     List<DCours> getListCours(@Param("o") String o);
	 
	 @Query("SELECT new dto.DCours(c.id,c.code) FROM Course c WHERE c.option = :o  AND c.id NOT IN (:ids) ")
     List<DCours> getPCours(@Param("ids") List<Long> ids , @Param("o") String o);
	 
	 @Query("SELECT c FROM Course c WHERE c.prof.id=:id ")
     List<Course> getCoursByProf(@Param("id") Long id);
}

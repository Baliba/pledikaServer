/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.promo.Course;
import com.hist.BysApp.entities.promo.HCours;
import com.hist.BysApp.projection.CourseView;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface HCoursDao extends JpaRepository<HCours, Long> {
	public  HCours findByCode(String code);
}

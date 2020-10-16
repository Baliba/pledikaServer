/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.grade.Vacation;
@CrossOrigin("*")
@RepositoryRestResource
public interface VacationRepository extends JpaRepository<Vacation, String> {
    public  Vacation findByName(String name);
    Boolean existsByCode(String code);
}

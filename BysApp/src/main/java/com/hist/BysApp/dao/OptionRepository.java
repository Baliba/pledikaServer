/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.grade.Option;
@CrossOrigin("*")
@RepositoryRestResource
public interface OptionRepository extends JpaRepository<Option,String> {

   public  Option findByName(String name);
   Boolean existsByCode(String code);
   public  Option findByCode(String code);
  
    
}

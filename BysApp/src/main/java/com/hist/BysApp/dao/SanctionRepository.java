/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import com.hist.BysApp.entities.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author User
 */
@CrossOrigin("*")
public interface SanctionRepository extends JpaRepository<Sanction, Long> {
    
}

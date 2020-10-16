/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;
import com.hist.BysApp.entities.paiement.Caisse;
import com.hist.BysApp.entities.promo.Parcours_frag;
import com.hist.BysApp.projection.CourseView;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@RepositoryRestResource
public interface CaisseDao extends JpaRepository<Caisse, Long> {
	
	@Query("SELECT c FROM Caisse c where c.acad = :af AND c.id_maker =:id ")
	List<Caisse> getCaisseForMaker(@Param("id") Long id,@Param("af") Long af);
	
	@Query("SELECT c FROM Caisse c where c.acad = :af AND c.id_caissier =:id ")
	List<Caisse> getCaisseForCassier(@Param("id") Long id,@Param("af") Long af);
	
	@Query("SELECT SUM(c.montant) FROM Caisse c where c.acad = :af AND c.id_caissier=:id AND type_cs=1")
	float  getCaisseCassierMontantEnc(@Param("id") Long id,@Param("af") Long af);
	
	@Query("SELECT SUM(c.montant) FROM Caisse c where c.acad = :af AND c.id_caissier=:id AND type_cs=1 ")
	float  getCaisseMakerMontantEnc(@Param("id") Long id,@Param("af") Long af);
	
	@Query("SELECT SUM(c.montant) FROM Caisse c where c.acad = :af AND c.id_caissier=:id AND type_cs=2")
	float  getCaisseCassierMontantDenc(@Param("id") Long id,@Param("af") Long af);
	
	@Query("SELECT SUM(c.montant) FROM Caisse c where c.acad = :af AND c.id_caissier=:id AND type_cs=2 ")
	float  getCaisseMakerMontantDenc(@Param("id") Long id,@Param("af") Long af);

}

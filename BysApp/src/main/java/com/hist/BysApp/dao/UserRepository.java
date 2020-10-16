/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.hist.BysApp.entities.member.UserEntity;

import models.MUser;

/**
 *
 * @author User
 * 95bb5b3190
 */

@CrossOrigin("*")
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    UserEntity findByCode(String code);
    Optional<UserEntity> findById(Long id);
    Boolean existsByUsername(String username);
    @Query("Select u from UserEntity AS u JOIN u.role r WHERE r.name= 'STUDENT' AND u.lover=false ")
    Page<UserEntity> getStudents(Pageable paging);
    
    @Query("Select u from UserEntity AS u JOIN u.role r WHERE r.name= 'STUDENT' "
    	  + "AND ( u.username LIKE %:q%  OR u.firstName LIKE %:q% OR u.lastName LIKE %:q% "
    	  + "OR  u.code LIKE %:q% OR u.date_de_naiss LIKE %:q% ) AND u.lover=false ")
    Page<UserEntity> getStudents(Pageable paging,@Param("q") String query);
   // OR u.current_class.code like %:q% 
    @Query("Select u from UserEntity AS u JOIN u.role r WHERE r.name= 'PROF' ")
    Page<UserEntity> getProf(Pageable paging);
    
     @Query("Select u from UserEntity AS u JOIN u.role r WHERE r.name= 'PROF' "
      	  + "AND ( u.username LIKE %:q%  OR u.firstName LIKE %:q% OR u.lastName LIKE %:q% "
      	  + "OR  u.code LIKE %:q% OR u.date_de_naiss LIKE %:q% ) ")
      Page<UserEntity> getProf(Pageable paging,@Param("q") String query);
     
     @Query("Select u from UserEntity AS u JOIN u.role r WHERE r.name= 'PROF' ")
     List<UserEntity> getProfForCours();
     
     @Query("SELECT u FROM UserEntity AS u JOIN u.role r WHERE r.name='STUDENT' AND  u.current_class.code=:code ")
     List<UserEntity> getStudentForPromo(@Param("code") String code);
     
     @Query("SELECT u FROM UserEntity AS u JOIN u.role r WHERE r.name='STUDENT'  AND u.enabled=true  AND u.code NOT IN (:code) AND u.lover=false  ")
     List<UserEntity> getStudentForPromoV2(@Param("code") List<String> code);
     
     @Query("SELECT u FROM UserEntity AS u JOIN u.role  r WHERE  r.name='STUDENT' AND u.enabled=true AND u.lover=false  ")
     List<UserEntity> getStudentForPromoV2();
     
     
     @Query("SELECT u FROM UserEntity AS u JOIN u.role r WHERE r.name='STUDENT'  AND u.enabled=true  AND u.code NOT IN (:code) AND u.lover=false AND u.current_class.code = :classe AND u.current_promo IS NULL ")
     List<UserEntity> getStudentForPromoV3(@Param("code") List<String> code, @Param("classe") String classe);
     
     @Query("SELECT u FROM UserEntity AS u JOIN u.role  r WHERE  r.name='STUDENT' AND u.enabled=true AND u.lover=false  AND u.current_class.code = :classe  AND u.current_promo IS NULL ")
     List<UserEntity> getStudentForPromoV3(@Param("classe") String classe);
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE r.name!= 'STUDENT' AND r.name!= 'PROF' AND r.name!= 'MASTER' ")
     List<UserEntity> getUsers();
     
     @Query("SELECT COUNT(u) fROM UserEntity AS u JOIN  u.role  r  WHERE r.name= 'STUDENT' AND u.lover=false  AND u.enabled=true ")
     int getNbreStudent();
     
     @Query("SELECT COUNT(u) fROM UserEntity AS u JOIN  u.role  r  WHERE r.name= 'STUDENT' AND u.sexe='F' AND u.lover=false  AND u.enabled=true ")
     int getNbreFille();
     
     @Query("SELECT COUNT(u) fROM UserEntity AS u JOIN  u.role  r  WHERE r.name= 'STUDENT' AND u.sexe='M' AND u.lover=false  AND u.enabled=true ")
     int getNbreGarcon();
     
     @Query("SELECT COUNT(u) fROM UserEntity AS u JOIN  u.role  r  WHERE r.name= 'PROF' ")
     int getNbreProf();
     
     @Query("SELECT COUNT(u) fROM UserEntity AS u JOIN  u.role  r  WHERE r.name!='PROF' AND r.name!='STUDENT'  ")
     int getNbrePers();
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name= 'ADMIN' OR  r.name= 'PROF'  OR r.name= 'ACCOUNTING' OR r.name= 'MANAGER' ")
     List<UserEntity> getUserForPayroll();
     
     @Query("SELECT u FROM UserEntity u  WHERE  username=:u ")
     UserEntity isUsername(@Param("u")  String username);
     
     @Query("SELECT u FROM UserEntity u  WHERE  nif=:u ")
     UserEntity isNif(@Param("u")  String username);
     
     
     @Query("SELECT u FROM UserEntity u  WHERE  cin=:u ")
     UserEntity isCin(@Param("u")  String username);
     
     @Query("SELECT u FROM UserEntity u  WHERE  code=:u ")
     UserEntity findbyCode(@Param("u")  String username);
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE r.name= 'STUDENT' OR  r.name= 'ADMIN' OR  r.name= 'PROF'  OR r.name= 'ACCOUNTING' OR r.name= 'MANAGER' ")
	 List<UserEntity> findAllUser();
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name= 'PROF'  ") 
	 List<UserEntity> findAllTeacher();
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name= 'STUDENT' AND u.lover=false  AND u.enabled=true  ")  
	 List<UserEntity> findAllStudent(); 
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE   r.name= 'ADMIN' OR r.name= 'ACCOUNTING' OR r.name= 'MANAGER' ")
	 List<UserEntity> findAllWorker();
     
     @Query("SELECT new models.MUser(u.id,u.code,u.lastName,u.firstName,u.sexe,u.date_de_naiss,u.username,u.current_promo,u.enabled,u.phone) fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name=:role") 
     List<MUser> getPeopleV2(@Param("role")  String role);
     
     @Query("SELECT new models.MUser(u.id,u.code,u.lastName,u.firstName,u.sexe,u.date_de_naiss,u.username,u.current_promo,u.enabled,u.phone) fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name=:role AND u.current_class.code = :code ") 
	 List<MUser> getPeopleV3(@Param("role")  String role, @Param("code")  String code);
 
     @Query("SELECT  new models.MUser(u.id,u.code,u.lastName,u.firstName,u.sexe,u.date_de_naiss,u.username,u.current_promo,u.enabled,u.phone) fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name=:role   AND ( LOWER(u.username) LIKE %:q% OR LOWER(u.firstName) LIKE %:q% OR LOWER(u.lastName) LIKE %:q% OR  LOWER(u.code) LIKE %:q%) AND u.lover=false ") 
	 List<MUser> getPeopleV4(@Param("role")  String role, @Param("q") String title);
     
     @Query("SELECT u fROM UserEntity AS u JOIN  u.role  r  WHERE  r.name=:role AND u.enabled=true AND u.lover=false  AND u.current_promo IS NULL") 
	 List<UserEntity> getStudentForPromoV5(@Param("role")  String role);
     
     @Modifying
 	 @Transactional
 	 @Query("UPDATE UserEntity AS p SET p.current_promo=:pn WHERE p.id=:id")
	 void setPromoName(@Param("pn")  String promo_name,@Param("id")  Long id );
}

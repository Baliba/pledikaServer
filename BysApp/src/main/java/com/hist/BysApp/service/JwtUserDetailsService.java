/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.service;

import com.hist.BysApp.Helper.RoleName;
import com.hist.BysApp.dao.EtabRepo;
import com.hist.BysApp.dao.UserRepository;
import com.hist.BysApp.entities.config.Etablissement;
import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.member.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userInfoRepository;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    public Page<UserEntity> getAllProfs(Integer pageNo, Integer pageSize, String sortBy)
    {
         Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
         
         Page<UserEntity> pagedResult= userInfoRepository.getProf(paging);

        return  pagedResult;
         
    }

    public Page<UserEntity> getAllProfs(Integer pageNo, Integer pageSize, String sortBy,String query)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
         
        
        Page<UserEntity> pagedResult  = userInfoRepository.getProf(paging,query);
      
        return  pagedResult;
         
    }
    
    
    public Page<UserEntity> getAllEtudiants(Integer pageNo, Integer pageSize, String sortBy)
    {
         Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
         
         Page<UserEntity> pagedResult= userInfoRepository.getStudents(paging);

        return  pagedResult;
         
    }

    public Page<UserEntity> getAllEtudiants(Integer pageNo, Integer pageSize, String sortBy,String query)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
         
        
        Page<UserEntity> pagedResult  = userInfoRepository.getStudents(paging,query);
      
        return  pagedResult;
         
    }
    
    public  Optional<UserEntity>  userId(Long id)  {
        Optional<UserEntity> user = userInfoRepository.findById(id);
        return user;
  }
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userInfoRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.getAuthorities());
    }
    
    public UserEntity getUserInfo(String username) {
    	return userInfoRepository.findByUsername(username);
    }
    
    @Autowired
    EtabRepo etab;
    
    public boolean  autoLogin (UserDetails user, String username, String password) {
    	Collection<? extends GrantedAuthority> auth =  user.getAuthorities();
    	String r ="";
    	Etablissement e= etab.findAll().get(0);
       for( GrantedAuthority role:auth) {
    	    r = role.getAuthority();
    	break;
       }
       if( (!e.isTeacher() && r.equals(RoleName.PROF)) || (!e.isStudent() && r.equals(RoleName.STUDENT))) {
    	   return false;
       }
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationManager.authenticate(upat);
        if (upat.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(upat);
            return true;
        }
        return false;
    }

	

}

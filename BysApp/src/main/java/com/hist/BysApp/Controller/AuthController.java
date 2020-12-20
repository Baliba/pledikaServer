/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Controller;

import com.hist.BysApp.Helper.ValidationException;
import com.hist.BysApp.Request.JwtRequest;
import com.hist.BysApp.Response.ErrorResponse;
import com.hist.BysApp.Response.JwtResponse;
import com.hist.BysApp.component.JwtToken;
import com.hist.BysApp.dao.UserRepository;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.service.JwtUserDetailsService;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService; 
    
    @Autowired
    private UserRepository userInfoRepository;
   
	
    
    
     @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();
        boolean error =true;
        
        if (!userInfoRepository.existsByUsername(username)){
            return ResponseEntity.ok(new ErrorResponse("Email invalid", true));
        }
        UserDetails user = jwtUserDetailsService.loadUserByUsername(username);
        UserEntity u = null;
        if(jwtUserDetailsService.autoLogin(user, username, password)){
            u = jwtUserDetailsService.getUserInfo(username);
            u.setToken(jwtToken.generateToken(user));
            error =false;
        }
        return ResponseEntity.ok(new JwtResponse<UserEntity>(error, u,"Success"));
    }
     
    
}

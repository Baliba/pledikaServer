/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Controller;

import com.hist.BysApp.Helper.RoleName;
import com.hist.BysApp.Request.RegisterRequest;
import com.hist.BysApp.Response.ErrorResponse;
import com.hist.BysApp.dao.RoleRepository;
import com.hist.BysApp.dao.UserRepository;
import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.factories.Helper;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class UserInfoController {
    
 
    @Autowired
    private UserRepository userInfoRepository;
    
    @Autowired
    private RoleRepository role;
   
    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<?>  create(@RequestBody RegisterRequest user) throws NoSuchAlgorithmException {
        String email = user.getUsername();
        Long nu = userInfoRepository.count();
        if (userInfoRepository.existsByUsername(email)){
            return ResponseEntity.ok(new ErrorResponse("Email invalide",true));
        }
        
        String password = user.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        // String hashedPassword = hashData.get_SHA_512_SecurePassword(password);
        String fname = user.getFname();
        String lname = user.getLname();
       
        UserEntity ut = new UserEntity(email, encodedPassword,fname,lname);
        Role r;
        r =  role.findByName(RoleName.TEST);
        ut.setRole(r);
        ut.setCode(Helper.generateCode(fname, lname, nu));
        ut.setAvatar("default.png");
        ut = userInfoRepository.save(ut);
        return ResponseEntity.ok(new ErrorResponse("Compte créé avec succès",false));
    }
    
}

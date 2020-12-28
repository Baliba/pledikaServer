/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Controller;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.hist.BysApp.Helper.RoleName;
import com.hist.BysApp.Request.CPassRequest;
import com.hist.BysApp.Request.EditNameRequest;
import com.hist.BysApp.Request.ParcoursRequest;
import com.hist.BysApp.Request.PayReq;
import com.hist.BysApp.Request.RegisterRequest;
import com.hist.BysApp.Response.AppResponse;
import com.hist.BysApp.Response.ErrorResponse;
import com.hist.BysApp.Response.JwtResponse;
import com.hist.BysApp.Response.RPayment;
import com.hist.BysApp.Response.RStat;
import com.hist.BysApp.Response.ResultRespose;
import com.hist.BysApp.component.StaticData;
import com.hist.BysApp.dao.*;
import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.config.Etablissement;
import com.hist.BysApp.entities.grade.Document;
import com.hist.BysApp.entities.grade.Domaine;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.CycleVersement;
import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.paiement.PaiementAdmission;
import com.hist.BysApp.entities.paiement.Payment;
import com.hist.BysApp.entities.paiement.Payroll;
import com.hist.BysApp.entities.promo.Course;
import com.hist.BysApp.entities.promo.Frag_cours;
import com.hist.BysApp.entities.promo.HCours;
import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.Parcours_frag;
import com.hist.BysApp.entities.promo.Programme;
import com.hist.BysApp.entities.promo.PromoFrag;
import com.hist.BysApp.entities.promo.Promo_af;
import com.hist.BysApp.entities.promo.Promo_cours;
import com.hist.BysApp.entities.promo.Promotion;
import com.hist.BysApp.entities.promo.Results;
import com.hist.BysApp.factories.Helper;
import com.hist.BysApp.model.VilleAndDoc;
import com.hist.BysApp.model.VilleAndNiveau;
import com.hist.BysApp.projection.CourseView;
import com.hist.BysApp.service.FileStorageService;
import com.hist.BysApp.service.JwtUserDetailsService;

@Controller 
public class BackdoorController {
	
	
	@Autowired 
	PVDao pvd;
	
	@Autowired 
	ParcoursRepository pars;
	
	@Autowired 
	FParcoursRepository fpDao;
	
	@Autowired
	COPaieDao cop;
	
	@Autowired 
	PromotionRepository promo;
	
	@Autowired
    NiveauRepository nivRep;
	
	@Autowired 
	coursDao cDao;
	
	@Autowired 
	DocRepo rDoc;
	
	@Autowired 
	Promo_afRepository  pafDao;
	
	@Autowired
	DomRepo dRep;
	
	@Autowired
	UserRepository user;
	
	@Autowired
	VilleRepository vDao;
	
	    
	@Autowired
	private RoleRepository role;
	
	@Autowired
	private AdmisPaieRepo adp;
	
	@Autowired
	private JwtUserDetailsService UserDetails;
	
	@Autowired
    private UserRepository u;
	
	@Autowired
	Promo_coursRepo pcDao;
	
	@Autowired
	PFragDao pfDao;
	
	@Autowired
	FCoursDao fcDao;
	
	@Autowired
	ParcoursRepository pDao;
	@Autowired
	ResultsRepository rDao;
	
	@Autowired
	PayrollDao payDao;
	   
	@Autowired
	EtabRepo etabDao;
	   
	@Autowired
	PRFragDao   prfDao;
	
	private String token = "fasfyyAh129999sdg7fsafeGSGgjajhha127SHHwetyHwihd828yudbzbBbdavh378bsdbfjfhdf8834rbbdfmsdfn78";
	
	public UserEntity  getUser (Authentication authentication){
		   UserDetails me = (UserDetails) authentication.getPrincipal();
	       return  this.UserDetails.getUserInfo(me.getUsername());
	   }
	  
	public  String  now() {
	        Date date = new Date();
	        String strDateFormat = "hh:mm:ss a";
	        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	        String formattedDate = dateFormat.format(date);
	        return formattedDate.trim();
	  }
	  
	  @RequestMapping(value = "/api/backdoor/checkDomain/{token}")
	  public ResponseEntity<?> checkDomain(@PathVariable("token") String token) {
		if(token.equals(this.token)) {    
		   return ResponseEntity.ok(new JwtResponse<Boolean>(false,true, "Succès"));
		  } else {
		   return ResponseEntity.ok(new JwtResponse<Boolean>(false,false, "Non authorisé"));
		}
	  }


}

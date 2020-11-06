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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
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
import com.hist.BysApp.Request.NotRequest;
import com.hist.BysApp.Request.ParcoursRequest;
import com.hist.BysApp.Request.PayReq;
import com.hist.BysApp.Request.RegisterRequest;
import com.hist.BysApp.Request.SetNoteRequest;
import com.hist.BysApp.Request.UserRequest;
import com.hist.BysApp.Response.AppResponse;
import com.hist.BysApp.Response.ErrorResponse;
import com.hist.BysApp.Response.JwtResponse;
import com.hist.BysApp.Response.RPayment;
import com.hist.BysApp.Response.RStat;
import com.hist.BysApp.Response.ResultRespose;
import com.hist.BysApp.component.NotData;
import com.hist.BysApp.component.StaticData;
import com.hist.BysApp.dao.*;
import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.config.Etablissement;
import com.hist.BysApp.entities.config.LNot;
import com.hist.BysApp.entities.config.Notification;
import com.hist.BysApp.entities.grade.Document;
import com.hist.BysApp.entities.grade.Domaine;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.grade.Niveau_rel;
import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.CycleVersement;
import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.paiement.PaiementAdmission;
import com.hist.BysApp.entities.paiement.Payment;
import com.hist.BysApp.entities.paiement.Payroll;
import com.hist.BysApp.entities.promo.Course;
import com.hist.BysApp.entities.promo.Fourniture;
import com.hist.BysApp.entities.promo.Frag_cours;
import com.hist.BysApp.entities.promo.Fragment;
import com.hist.BysApp.entities.promo.HCours;
import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.Parcours_frag;
import com.hist.BysApp.entities.promo.ProgFourniture;
import com.hist.BysApp.entities.promo.Programme;
import com.hist.BysApp.entities.promo.PromoFrag;
import com.hist.BysApp.entities.promo.Promo_af;
import com.hist.BysApp.entities.promo.Promo_cours;
import com.hist.BysApp.entities.promo.Promotion;
import com.hist.BysApp.entities.promo.Results;
import com.hist.BysApp.factories.Helper;
import com.hist.BysApp.model.ASDto;
import com.hist.BysApp.model.MoyDto;
import com.hist.BysApp.model.PromoDto;
import com.hist.BysApp.model.RPaie;
import com.hist.BysApp.model.ResultDTO;
import com.hist.BysApp.model.NUserDTO;
import com.hist.BysApp.model.PStatDto;
import com.hist.BysApp.model.VilleAndDoc;
import com.hist.BysApp.model.VilleAndNiveau;
import com.hist.BysApp.projection.CourseView;
import com.hist.BysApp.service.FileStorageService;
import com.hist.BysApp.service.JwtUserDetailsService;
import com.hist.BysApp.service.NotService;

@Controller 
public class IndexController {
	@Autowired 
	PVDao pvd;
	
	@Autowired 
	ParcoursRepository pars;
	
	@Autowired 
	FParcoursRepository fpDao;
	
	@Autowired 
	NotRepo notDao;
	@Autowired 
	LNotRepo lnotDao;
	
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
	private NotService   sNot;
	
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
	
	@Autowired
	FragDoa  fDao;
	
	@Autowired
	Niveau_relRepository nRelDao;
	
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
	    
    
	   @RequestMapping(value = "/api/getPayroll/{id}")
	   public ResponseEntity<?>  makePayroll(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  ut = getUser(auth);
		   Promo_af year =  pafDao.getActived();
		   if(ut.getRole().getName().equals(RoleName.MANAGER) || ut.getRole().getName().equals(RoleName.ACCOUNTING) || ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
			   List<Payroll> p = payDao.getPayroll(id,year.getId(),false);
		       return ResponseEntity.ok(new JwtResponse<List<Payroll>>(false,p,"Payroll")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
	   
	   }
	   
	   @RequestMapping(value = "/api/getTypePay")
	   public ResponseEntity<?>  getTypePay(Authentication auth) {
		   UserEntity  ut = getUser(auth);
		   if(ut.getRole().getName().equals(RoleName.MANAGER) || ut.getRole().getName().equals(RoleName.ACCOUNTING) || ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
		       return ResponseEntity.ok(new JwtResponse<List<String>>(false,StaticData.TYPE_PAYROLL,"Type Paiement")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
	   
	   }
	   
	   @RequestMapping(value = "/api/historic/{id}")
	   public ResponseEntity<?>  historicPayroll(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  ut = getUser(auth);
		   Promo_af year =  pafDao.getActived();
		   if(ut.getRole().getName().equals(RoleName.MANAGER) || ut.getRole().getName().equals(RoleName.ACCOUNTING) || ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
			   List<Payroll> p = payDao.getPayroll(id,year.getId(),true);
		       return ResponseEntity.ok(new JwtResponse<List<Payroll>>(false,p,"Payroll")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
	   
	   }
	   
	   @RequestMapping(value = "/api/recPayroll/{id}")
	   public ResponseEntity<?>  recuPayroll(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  ut = getUser(auth);
		   Promo_af year =  pafDao.getActived();
		   if(ut.getRole().getName().equals(RoleName.MANAGER) || ut.getRole().getName().equals(RoleName.ACCOUNTING) || ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
			   Payroll p = payDao.findById(id).get();
			   p.setReceive(true);
			   Date d = new Date();
			   p.setDate_rec(d);
			   p.setRec_by(ut.getId());
			   p = payDao.save(p);
		       return ResponseEntity.ok(new JwtResponse<Payroll>(false,p,"Payroll "+p.getCode()+" reçu")); 
		     }else{
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
	   
	   }
	   
	   @Modifying
	   @Transactional
	   @RequestMapping(value = "/api/editName", method = RequestMethod.POST)
	   public ResponseEntity<?>  editName(@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  ut = getUser(auth);
		   ut.setFirstName(u.getFirstName());
		   ut.setLastName(u.getLastName());
		   ut = user.save(ut);
		   pars.editFullName(ut.getCode(),u.getFirstName(),u.getLastName());
		   payDao.editFullName(ut.getCode(),u.getFirstName(),u.getLastName());
		   rDao.editFullName(ut.getCode(),u.getFirstName(),u.getLastName());
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Modifier nom complet")); 
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/editName/{id}", method = RequestMethod.POST)
	   public ResponseEntity<?>  editNameByAdmin(@PathVariable("id") Long id,@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   UserEntity  ut = user.findById(id).get();
		   
		   ut.setFirstName(u.getFirstName());
		   ut.setLastName(u.getLastName());
		   ut = user.save(ut);
		   rDao.editFullName(ut.getCode(),u.getFirstName(),u.getLastName());
		   pars.editFullName(ut.getCode(),u.getFirstName(),u.getLastName());
		   payDao.editFullName(ut.getCode(),u.getFirstName(),u.getLastName());
		 
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Modifier nom complet ")); 
		   }else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   
	   
	   @Transactional
	   @RequestMapping(value = "/api/editEmail/{id}", method = RequestMethod.POST)
	   public ResponseEntity<?>  editEmailByAdmin(@PathVariable("id") Long id,@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   if(user.isUsername(u.getUsername())==null) {
		   UserEntity  ut = user.findById(id).get();
		   ut.setUsername(u.getUsername());
		   ut = user.save(ut);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Payroll")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Email existe déjà."));
		   }
		   }else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   @Transactional
	   @RequestMapping(value = "/api/editEmail", method = RequestMethod.POST)
	   public ResponseEntity<?>  editEmail(@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		    if(user.isUsername(u.getUsername())==null) {
		        utt.setUsername(u.getUsername());
		        utt = user.save(utt);
		        return ResponseEntity.ok(new JwtResponse<UserEntity>(false,utt,"Changement email")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Email existe déjà."));
		   }
		   }else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/editNif/{id}", method = RequestMethod.POST)
	   public ResponseEntity<?>  editNifByAdmin(@PathVariable("id") Long id,@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   if(user.isNif(u.getNif())==null) {
		   UserEntity  ut = user.findById(id).get();
		   ut.setNif(u.getNif());
		   ut = user.save(ut);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Nif")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Nif existe déjà."));
		   }
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/editNif", method = RequestMethod.POST)
	   public ResponseEntity<?>  editNif(@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   if(user.isNif(u.getNif())==null) {
		   utt.setNif(u.getNif());
		   utt = user.save(utt);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,utt,"Nif")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Nif existe déjà."));
		   }
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/editCin/{id}", method = RequestMethod.POST)
	   public ResponseEntity<?>  editCinByAdmin(@PathVariable("id") Long id,@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   if(user.isCin(u.getCin())==null) {
				   UserEntity  ut = user.findById(id).get();
				   ut.setCin(u.getCin());
				   ut = user.save(ut);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Nif")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Cin existe déjà."));
		   }
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   @Transactional
	   @RequestMapping(value = "/api/editCin", method = RequestMethod.POST)
	   public ResponseEntity<?>  editCinByAdmin(@RequestBody EditNameRequest u, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   if(user.isCin(u.getCin())==null) {
				   utt.setCin(u.getCin());
				   utt = user.save(utt);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,utt,"Nif")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Cin existe déjà."));
		   }
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   
	   @RequestMapping(value = "/api/editPOB/{id}/{pob}")
	   public ResponseEntity<?>  editPOB(@PathVariable("pob") Long pob, @PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   Ville v = vDao.findById(pob).get();
			   if(utt.getId()==id) {
				   utt.setLieu_de_naiss(v);
	    	       utt = user.save(utt);
	    	       return ResponseEntity.ok(new JwtResponse<UserEntity>(false,utt,"Lieu de naissance modifié")); 
			   } else {
	    	       UserEntity  ut = user.findById(id).get();
	    	       ut.setLieu_de_naiss(v);
	    	       ut = user.save(ut);
	    	       return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Lieu de naissance modifié")); 
			       }
		   } else {
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   
	   @RequestMapping(value = "/api/editSal/{id}/{sal}")
	   public ResponseEntity<?>  editSal(@PathVariable("sal") double sal, @PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   Etablissement etab =  etabDao.findAll().get(0); 
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			    if(utt.getId()==id) {
				    return ResponseEntity.ok(new JwtResponse<UserEntity>(true,utt,"Vous ne pouvez pas modifier votre salaire.")); 
			       } else {
		    	   UserEntity  ut = user.findById(id).get();
		    	   if(sal>=etab.getSalairy_min() && sal<=etab.getSalairy_max()) {
					    	   if(!ut.getRole().getName().equals(RoleName.STUDENT) || sal==0 ) {
					    	       ut.setSalairy(sal);
					    	       ut = user.save(ut);
					    	       return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Salaire modifié")); 
					    	    } else {
					    		   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,ut,"Vous ne pouvez pas modifier votre salaire.")); 
					    	    }
			        } else {
			    		   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,ut,"Salaire non valide.")); 
			    	 }
			       }
		   } else {
			    
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		 
	   }
	   
	   @RequestMapping(value = "/api/findUserByCode/{code}")
	   public ResponseEntity<?>  findUserByCode(@PathVariable("code") String code, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.MANAGER)  || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   UserEntity  ut = user.findByCode(code);
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,ut,"Etudiants infos.")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @RequestMapping(value = "/api/editBourse/{id}/{b}")
	   public ResponseEntity<?>  findUserByCode(@PathVariable("b") int b,@PathVariable("id") Long code, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   UserEntity  ut = user.findById(code).get();
			   ut.setGranted(b);
			   ut=user.save(ut);
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,ut,"Bourse d'etude.")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @RequestMapping(value = "/api/editEtat/{id}/{b}")
	   public ResponseEntity<?>  expulsion(@PathVariable("b") boolean b,@PathVariable("id") Long code, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   UserEntity  ut = user.findById(code).get();
			   ut.setEnabled(b);
			   ut=user.save(ut);
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Modification affectuée avec succès")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @RequestMapping(value = "/api/editExc/{id}/{b}")
	   public ResponseEntity<?>  exc(@PathVariable("b") boolean b,@PathVariable("id") Long code, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   UserEntity  ut = user.findById(code).get();
			   if(b) {
				  ut.setEnabled(false); 
			   }
			   ut.setExclude(b);
			   ut=user.save(ut);
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut,"Modification affectuée avec succès")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/setBoard/{id}", method = RequestMethod.POST)
	   public ResponseEntity<?>  editBoard(@RequestBody EditNameRequest u,@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
		   Promotion   ut = promo.findById(id).get();
		   ut.setBoard(u.getBoard());
		   ut = promo.save(ut);
		   return ResponseEntity.ok(new JwtResponse<Promotion>(false,ut,"Modification affectuée avec succès")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/ParcoursForBulletinGen/{id}")
	   public ResponseEntity<?>  getParcoursByPromoId(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
		   List<Parcours>  ut = pars.getParcours(id);
		   return ResponseEntity.ok(new JwtResponse<List<Parcours>>(false,ut,"Liste Parcours")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		  
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/getGenResult/{user}/{frag}")
	   public ResponseEntity<?>  fullResult( @PathVariable("user") Long user, @PathVariable("frag") Long frag, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
				   List<Results>  ut = rDao.getBulletinGen(user, frag);
				   return ResponseEntity.ok(new JwtResponse<List<Results>>(false,ut,"Results ")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		  
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/setMentionPars/{user}", method = RequestMethod.POST)
	   public ResponseEntity<?>  fullResult(@RequestBody ParcoursRequest u, @PathVariable("user") Long user, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
				   Parcours ut = pars.findById(user).get();
				   ut.setMention(u.getMention());
				   pars.save(ut);
				   return ResponseEntity.ok(new JwtResponse<Parcours>(false,ut,"Modification effectuée avec succès")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		  
	   }
	   
	   @RequestMapping(value = "/api/getNot")
	   public ResponseEntity<?> getNot(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
				   List<Notification> not = notDao.getNotAdmin(utt.getId());
				   return ResponseEntity.ok(new JwtResponse<List<Notification>>(false,not,"Notificatons")); 
		   } else {
			   List<Notification> not = notDao.getNot(utt.getId());
			   return ResponseEntity.ok(new JwtResponse<List<Notification>>(false,not,"Notifications")); 
	      }
		  
	   }
	   
	   @RequestMapping(value = "/api/addNot",method = RequestMethod.POST)
	   public ResponseEntity<?> addNot(Authentication auth,@RequestBody NotRequest not) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
				   LNot ln = new LNot();
				   ln.setId_sender(utt.getId());
				   ln.setMsg(not.getMessage());
				   ln.setTitre(not.getTitre());
				   ln.setTo_who(not.getCible());
				   ln = lnotDao.save(ln);
				   boolean rep = sNot.sendNotTo(not,utt,ln);
				   return ResponseEntity.ok(new JwtResponse<String>(false,"","Notificatons")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		  
	   }
	   
	   @RequestMapping(value = "/api/addNot/{id}",method = RequestMethod.POST)
	   public ResponseEntity<?> addNotForPromo(@PathVariable("id") Long id,Authentication auth,@RequestBody NotRequest not) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.PROF) || utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
				   LNot ln = new LNot();
				   ln.setId_sender(utt.getId());
				   ln.setMsg(not.getMessage());
				   ln.setTitre(not.getTitre());
				   ln.setTo_who(not.getCible());
				   ln = lnotDao.save(ln);
				   boolean rep = sNot.sendNotToPromo(not,utt,ln,id);
				   return ResponseEntity.ok(new JwtResponse<String>(false,"","Notificatons")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		  
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/takeNot/{id}")
	   public ResponseEntity<?> takeNot(Authentication auth,@PathVariable("id") Long id) {
		           UserEntity  utt = getUser(auth);
		           if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		           Notification not = notDao.findById(id).get();
		           if(not.getId_receiver()==0) {
		           not.setId_receiver(utt.getId());
		           not = notDao.save(not);
				   return ResponseEntity.ok(new JwtResponse<Notification>(false,not,"Vous avez pris cette notification.")); 
		           } else {
				       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Cette notification est déjà pris."));
		 	      }
		           }  else {
				       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		 	      }
	   }
	   @Transactional
	   @RequestMapping(value = "/api/readNot/{id}")
	   public ResponseEntity<?> readNot(Authentication auth,@PathVariable("id") Long id) {
		           UserEntity  utt = getUser(auth);
				   notDao.readNot(utt.getId(),id);
				   return ResponseEntity.ok(new JwtResponse<String>(false,"","Notificatons")); 
	   }
	    @Autowired
	    private JwtUserDetailsService jwtUserDetailsService; 
	   @RequestMapping(value = "/api/fpass/{email}")
	   public ResponseEntity<?> fpass(Authentication auth,@PathVariable("email") String username) { 
	        boolean error =true;
	        if (!user.existsByUsername(username)){
	            return ResponseEntity.ok(new ErrorResponse("Email invalid", true));
	        }
	        UserEntity  user = jwtUserDetailsService.getUserInfo(username);
	        String  msg = NotData.getFPASS(user);
	        String  t   = NotData.TFPASS;
	        String  url = "/app/cpass/"+user.getId();
	        String  btn =  NotData.getBtn(url,"ré-initialiser le mot de passe ici"," btn-primary");
	        boolean rep =  sNot.sendNotToOneUser(user,msg,t,btn);
			return ResponseEntity.ok(new JwtResponse<String>(false,"","Nous sommes entrain d'analyser votre demande dans moins de 24H l'administrateur du systeme vous contactera pour vous communiquer votre nouveau mot passe.")); 
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/setListResults",method = RequestMethod.POST)
	   public ResponseEntity<?> setListResults(@RequestBody SetNoteRequest results,Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if(utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			 for(ResultDTO res: results.getResults()) {
				Optional<Results> r = rDao.findById(res.getId());
				if(r.isPresent()) {
					Results nr = r.get();
					if(nr.getNote_total()>=res.getNote()) {
					  nr.setNote(res.getNote());
					  rDao.save(nr);
					}
				} else {
					continue;
				}
			 }  
			 return ResponseEntity.ok(new JwtResponse<String>(false,"","Les resultats ont été modifiés avec succès")); 
				
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	//   add46a571483582e0997bc80a7d62667
	   
	   @Transactional
	   @RequestMapping(value = "/api/getPromoToUpgrade/{id}")
	   public ResponseEntity<?> getPromo(Authentication auth,@PathVariable("id") Long id ) {
		   UserEntity  utt = getUser(auth);
		   Promotion  p = promo.findById(id).get();
		   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) ) || ( utt.getRole().getName().equals(RoleName.PROF) && p.getTitulaire().getId()==utt.getId()) ) {
			   String niv = p.getNiveau_rel().getNiveau().getNext();
			   Long af = p.getPromo_af().getNext_year();
			   List<PromoDto> ps = promo.getPromoV2(p.getId(),niv,af);
			 return ResponseEntity.ok(new JwtResponse<List<PromoDto>>(false,ps,"Promotions")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/getStudentToUpgrade/{id}")
	   public ResponseEntity<?> getStudent(Authentication auth,@PathVariable("id") Long id) {
		   UserEntity  utt = getUser(auth);
		   Promotion  p = promo.findById(id).get();
		   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) ) || ( utt.getRole().getName().equals(RoleName.PROF) && p.getTitulaire().getId()==utt.getId()) ) {
			   List<MoyDto> ps = rDao.getEtudiantsToMG(p.getId());
			   return ResponseEntity.ok(new JwtResponse<List<MoyDto>>(false,ps,"Etudiants de la  promotion ("+id+")")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   

	   @RequestMapping(value = "/api/getStudentToOver")
	   public ResponseEntity<?> getStudent(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   Etablissement e = etabDao.findAll().get(0);
		   String code = e.getCode_philo();
		   if(code==null) {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous devez configurer la classe terminale dans la configuration de l'etablissement"));
		   }
		   Promo_af p = pafDao.getActived();
		   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
			   List<MoyDto> ps = rDao.getEtudiantsToOver(p.getId(), code);
			   return ResponseEntity.ok(new JwtResponse<List<MoyDto>>(false,ps,"Etudiants finissants")); 
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/getrPaiement")
	   public ResponseEntity<?> getRapport(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   Promo_af year =  pafDao.getActived();
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.MANAGER) ) {
			   Long id = year.getId();
			   float  mgain = pvd.rapportGain(id);
			   float  gain  =  pvd.rapportRealGain(id);
			   float  bgain  = 0;
			   try {  bgain = pvd.rapportBourse(id); } catch(Exception e) {}
			   float  tgain = mgain-gain;
			   RPaie pr = new RPaie(mgain,gain,tgain,bgain);
			   return ResponseEntity.ok(new JwtResponse<RPaie>(false,pr,"Rapport de caisse")); 
		     } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/getrPaiementPromo/{id}")
	   public ResponseEntity<?> getRapport(Authentication auth,@PathVariable("id") Long id) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.MANAGER) ) {
			   Promotion p = promo.getOne(id);
			   RPaie pr;
			   if(p.getParcours().size()>0) {
			   float mgain = pvd.rapportGainPromo(id);
			   float  gain =  pvd.rapportRealGainPromo(id);
			   float  tgain = mgain-gain;
			   pr = new RPaie(mgain,gain,tgain);
		      } else {
		    	 pr = new RPaie(0,0,0);  
		      }
			   return ResponseEntity.ok(new JwtResponse<RPaie>(false,pr,"Rapport de caisse")); 
		     } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   
	   
	   @Transactional
	   @RequestMapping(value = "/api/closeByPromo/{id}")
	   public ResponseEntity<?>  closeByPromo(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
		   pars.closeByPromo(id,false);
		   return ResponseEntity.ok(new JwtResponse<String>(false,null,"Les etudiants de cette promotion sont deactivés")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @SuppressWarnings("deprecation")
	   @Transactional
	   @RequestMapping(value = "/api/setListUsers/{code}/{index}",method = RequestMethod.POST)
	   public ResponseEntity<?> setListUsers(@RequestBody UserRequest results,Authentication auth,@PathVariable("index") Long index, @PathVariable("code") String code) throws ParseException {
		   UserEntity  utt = getUser(auth);
		   List<NUserDTO>   users = Arrays.asList();
		   if(utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			 Niveau niv =    nivRep.findByCode(code);
			 Niveau next = null;
			 Niveau prev = null;
			 if(niv.getNext()!=null) {
			   next =   nivRep.findByCode(niv.getNext());
			 }
			 if( niv.getPrev()!=null) {
			  prev =   nivRep.findByCode(niv.getPrev());
			 }
			 Role rl;
 			 rl = role.findByName(RoleName.STUDENT);
 			 int np = 0;
			 for(NUserDTO res: results.getResults()) {
				   if(res.getPrenom()!=null && res.getNom()!=null) {
					UserEntity nr = new UserEntity();
					np++;
					String cd = Helper.generateCode(res.getPrenom(),res.getNom(),np+index);
					nr.setCode(cd);
					nr.setFirstName(res.getPrenom());
					nr.setLastName(res.getNom());
					nr.setSexe(res.getSexe());
					String email = cd.toLowerCase()+"@"+"pledika.com";;
					nr.setUsername(email);
					nr.getPrev_class();
					nr.setCurrent_class(niv);
					nr.setPrev_class(prev);
					nr.setNext_class(next);
					nr.setAvatar("default.png");
                    nr.setDate_de_naiss(res.getDate_de_naiss());
        			nr.setRole(rl);
                    nr.setEnabled(true);
					user.save(nr);
				   } else {
					   continue;
				   }
			 }  
			 return ResponseEntity.ok(new JwtResponse<List<NUserDTO>>(false,users,np+ " etudiants ont été ajouté avec succès")); 
				
		   } else {
			   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @Transactional
	   @RequestMapping(value = "/api/genPromo")
	   public ResponseEntity<?>  genPromo(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
			     // Etablissement etab = etabDao.findAll().get(0);
			      Promo_af pf= pafDao.getActived();
			      if(pf==null) {
			    	  return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous devez ajouter au moins une <a href='/app/acad'> année academique <a/>. "));
			      }
			      List<Niveau_rel> nrels = nRelDao.findAll();
			      for(Niveau_rel n: nrels) {
			    	String year = pf.getDate_debut().toString().split("T")[0].split("-")[0] + '-' + pf.getDate_fin().toString().split("T")[0].split("-")[0];
                    String code = n.getName()+"-"+year;
                    if(promo.findByCode(code)==null) {
                    Promotion p = new Promotion();
                    p.setCode_cycle(n.getNiveau().getOption().getCode());
                    p.setCode_niveau(n.getNiveau().getCode());
                    p.setNiveau_rel(n);
                    p.setProg_id(n.getProg_id());
                    p.setCode(code);
                    p.setPromo_af(pf);
                    p.setMoy_total(pf.getMoy_total());
                    p.setMoy_accept(pf.getMoy_accept());
                    p.setMoy_reprise(pf.getMoy_reprise());
                    p.setMoy_exc(pf.getMoy_exc());
                    p.setReprise(n.isReprise());
                    p.setMax_student(50);
                    promo.save(p);
                    }
			      }
		          return ResponseEntity.ok(new JwtResponse<String>(false,null,"Les promotions ont été crée avec succès")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   
	   @RequestMapping(value = "/api/getAf")
	   public ResponseEntity<?>  getAf() {
               Promo_af pf= pafDao.getActived();
		       return ResponseEntity.ok(new JwtResponse<Promo_af>(false,pf,"Année encours")); 
	   }
	   @RequestMapping(value = "/api/getFrag")
	   public ResponseEntity<?>  getFrag() {
               List<Fragment> f = fDao.getFrag();
		       return ResponseEntity.ok(new JwtResponse<List<Fragment>>(false,f,"Fragment")); 
	   }
	   
	   @RequestMapping(value = "/api/getAllStudent")
	   public ResponseEntity<?> getAllStudent() {
		       Promo_af pf= pafDao.getActived();
		      // List<ASDto> asd = promo.getAllParcours(pf.getId());
		       List<Parcours> asd = pars.getAllParcours(pf.getId());
		       return ResponseEntity.ok(new JwtResponse<List<Parcours>>(false,asd,"Listes des etudiants par promotion")); 
	   }
	   
	   @RequestMapping(value = "/api/getSexeStat")
	   public ResponseEntity<?> getSexeStat() {
		       Promo_af pf= pafDao.getActived();
		       List<PStatDto> ps = new ArrayList<PStatDto>();
		       for(Promotion p : pf.getPromotion()) {
		    	   if(p.getParcours().size()>0) {
		    	   Long  F = pars.getBySexe(p.getId(),"F");
		    	   Long  M = pars.getBySexe(p.getId(),"M");
		    	   if (F==null) { F = 0L;}
		    	   if (M==null) { M = 0L;}
		    	   PStatDto psd = new PStatDto(p.getCode(),F,M);
		    	   ps.add(psd);
		    	   } else {
			    	  ps.add(new PStatDto(p.getCode()));
		    	   }
		       }
		       return ResponseEntity.ok(new JwtResponse<List<PStatDto>>(false,ps,"Nombres etudiants par promotion")); 
	   }
	   
	   
	  
	   @Transactional
	   @RequestMapping(value = "/api/getParcoursFrags/{id}")
	   public ResponseEntity<?>  getParcoursFrags(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   List<Parcours_frag> pf = fpDao.getRFParcours(id);
		   if(pf.size()>0) {
		    return ResponseEntity.ok(new JwtResponse<List<Parcours_frag>>(false,pf,"Les etudiants de ce controle | trimestre ")); 
		   }else { return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"aucun  etudiant affecté a cet controle | trimestre.")); }
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	 
	   @RequestMapping(value = "/api/initResults/{id}")
	   public ResponseEntity<?> initResults(@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		   PromoFrag pf = pfDao.getOne(id);
		   Collection<Frag_cours> fcs = pf.getFrag_cours();
		   if(fcs.size()>0) {
		   Collection<Parcours_frag> pcs = pf.getParcours_frag();
		   if(pcs.size()>0) {
		   for(Parcours_frag p :pcs) {
			   for(Frag_cours fc :fcs) {
				    String code = p.getId() + "-" + fc.getId();
					Results fct = rDao.findByCode(code);
					if (fct == null) {
						Results r = new Results();
						r.setCode(code);
						r.setParcours_frag(p);
						r.setFrag_cours(fc);
						r.setNom(p.getNom());
						r.setPnom(p.getPnom());
						r.setNote_total(fc.getNote_total());
						r.setNote_total(fc.getNote_total());
						r.setNote_pass( fc.getNote_pass());
						r.setNote_excel(fc.getNote_excel());
						r.setCode_student(p.getCode_student());
						r.setCoef(fc.getCoef());
						r.setNote(0f);
						rDao.save(r);
					} 
				  
			   }
		    }
		   } else { return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"aucun  etudiant affecté.")); }
		   } else { return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"aucun  cours affecté.")); }
		   
		   return ResponseEntity.ok(new JwtResponse<String>(false,null,"Les resultats ont été ajoutés avec succès.")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   

	   
	   
	   @RequestMapping(value = "/api/getPromoByAF")
	   public ResponseEntity<?> getPromoByAF(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		       Promo_af pf= pafDao.getActived();
		       List<PromoDto> ps = promo.getPromoByAF(pf.getId());
		       return ResponseEntity.ok(new JwtResponse<List<PromoDto>>(false,ps,"Nombres etudiants par promotion")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/getPromoByAFAndNivRel/{id}")
	   public ResponseEntity<?> getPromoByAFNivrel(Authentication auth,@PathVariable("id") Long id) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		       Promo_af pf= pafDao.getActived();
		       List<PromoDto> ps = promo.getPromoByAFV2(pf.getId(),id);
		       return ResponseEntity.ok(new JwtResponse<List<PromoDto>>(false,ps,"Nombre etudiants par promotion")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/checkAF")
	   public ResponseEntity<?> countAF(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		       Long ps = pafDao.count();
		       boolean next = false;
		       if (ps>1) {
		    	   Promo_af pf= pafDao.getActived(); 
		    	   if (pf.getPrev_year()!=null) {
		    		   next = true;
		    	   }
		       }
		       return ResponseEntity.ok(new JwtResponse<Boolean>(false,next,"Nombre d'années academiques")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/progression/{id}/{prev}")
	   public ResponseEntity<?> progression(Authentication auth,@PathVariable("id") Long id, @PathVariable("prev") Long prev) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		       Promotion cpromo = promo.findById(id).get();
		       Promotion ppromo = promo.findById(prev).get();
		       cpromo.setPrev_promo(prev);
		       cpromo.setPrev_promo_name(ppromo.getCode());
		       ppromo.setNext_promo(id);
		       ppromo.setNext_promo_name(cpromo.getCode());
		       cpromo =  promo.save(cpromo);
		       promo.save(ppromo);
		       return ResponseEntity.ok(new JwtResponse<String>(false,null,"Succès")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/getPromoPrev/{id}")
	   public ResponseEntity<?> getPromoForPrev(Authentication auth,@PathVariable("id") Long id) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		       Promo_af pf= pafDao.getActived();
		       Promotion cpromo = promo.findById(id).get();
		       String code = cpromo.getNiveau_rel().getNiveau().getPrev();
		       List<PromoDto> ps = promo.getPrevPromo(pf.getPrev_year(),id, code);
		       return ResponseEntity.ok(new JwtResponse<List<PromoDto>>(false,ps,"Liste des promotions precedentes")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/resetProg/{id}")
	   public ResponseEntity<?> progression(Authentication auth,@PathVariable("id") Long id) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
		       Promotion cpromo = promo.findById(id).get();
		       Promotion ppromo = promo.findById(cpromo.getPrev_promo()).get();
		       cpromo.setPrev_promo(null);
		       cpromo.setPrev_promo_name(null);
		       ppromo.setNext_promo(null);
		       ppromo.setNext_promo_name(null);
		       cpromo =  promo.save(cpromo);
		       promo.save(ppromo);
		       return ResponseEntity.ok(new JwtResponse<String>(false,null,"Succès")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/closePars/{id}/{code}/{state}")
	   public ResponseEntity<?>  closePars(@PathVariable("state") boolean state,@PathVariable("code") String code,@PathVariable("id") Long id, Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
		   pars.closeOneByPromo(id,code,state);
		   return ResponseEntity.ok(new JwtResponse<String>(false,null,"Etudiant deactivé")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	   
	   @RequestMapping(value = "/api/getFreeStudent")
	   public ResponseEntity<?>  getFreeStudent(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
			   
				List<UserEntity> u;
				u = user.getStudentForPromoV5("STUDENT");
				return ResponseEntity.ok(new JwtResponse<List<UserEntity>>(true, u, "Student")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
	   }
	    @RequestMapping(value = "/api/getParcoursForEC")
		public ResponseEntity<?> getParcoursForEC(Authentication auth) {

			  UserEntity  utt = getUser(auth);
			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
				   Promo_af pf= pafDao.getActived();
				   List<Parcours> p = pDao.getAllParcours(pf.getId()); 
				   RPaie pr;
				   if(p.size()>0) {
				   float  mgain =  pvd.rapportGain(pf.getId());
				   float  gain =  pvd.rapportRealGain(pf.getId());
				   float  tgain = mgain-gain;
				   pr = new RPaie(mgain,gain,tgain);
			       } else {
			    	 pr = new RPaie(0,0,0);  
			      }
				   pr.setP(p);
				   return ResponseEntity.ok(new JwtResponse<RPaie>(true, pr, "Etat de compte")); 
			   } else {
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		      }
		}
	    
	    @Autowired
		FouDao fouDao;
		@Autowired
		PFouDao pfouDao;
		@Autowired
		ProgDao pgDao;
		
		@RequestMapping(value = "/api/addFoursToProg/{idp}/{idf}/{qt}")
		public ResponseEntity<?> addFoursToProg(Authentication auth, @PathVariable("idp") Long idp, @PathVariable("idf") Long idf, @PathVariable("qt") int qt) {
			UserEntity  utt = getUser(auth);
			if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {	  
			Programme p = pgDao.findById(idp).get();
			Fourniture f = fouDao.findById(idf).get();
			String code = p.getId()+"-"+f.getId();
			if(pfouDao.findByCode(code)==null) {
			ProgFourniture pf = new ProgFourniture();
			pf.setProgramme(p);
			pf.setFourniture(f);
			pf.setCode(code);
			pf.setName(f.getName());
			pf.setType_fn(f.getType_fn());
			pf.setUnity(f.getUnity());
			pf.setQt(qt);
			pf.setSell_at(f.isSell_at());
			pf = pfouDao.save(pf);
			return ResponseEntity.ok(new JwtResponse<ProgFourniture>(false, pf, "Programme fourniture")); 
			} else {
			return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Fourniture déjà ajouté"));
			}
			} else {
			  return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
		}
	    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Controller;

import java.nio.file.Paths;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import org.springframework.aop.AopInvocationException;
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
import com.hist.BysApp.entities.Contact;
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
import com.hist.BysApp.model.MoyDto;
import com.hist.BysApp.model.VilleAndDoc;
import com.hist.BysApp.model.VilleAndNiveau;
import com.hist.BysApp.projection.CourseView;
import com.hist.BysApp.service.FileStorageService;
import com.hist.BysApp.service.JwtUserDetailsService;

import Palmares.MResults;
import dto.User;
import models.MParcours;

@Controller 
public class SystemController {
	
	
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
	
	@Autowired
	ContactDao   contDao;
	
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
	  
       @RequestMapping(value = "/api/getContacts")
		public ResponseEntity<?> getContacts(Authentication auth) {
      	    List<Page<Contact>> lc = new ArrayList<Page<Contact>>();
      	    lc.add(getContacts(0,100,true));
      	    lc.add(getContacts(0,100,false));
  			return ResponseEntity.ok(new JwtResponse<List<Page<Contact>>>(false,lc, ""));
      }
       
       public Page<Contact> getContacts(Integer pageNo, Integer pageSize,boolean query) {
           Pageable paging = PageRequest.of(pageNo, pageSize);
           Page<Contact> pagedResult  = contDao.getContact(paging,query);
         
           return  pagedResult;
            
       }
       
       @RequestMapping(value = "/api/countContacts")
		public ResponseEntity<?> countContacts(Authentication auth) {
    	   UserEntity  utt = getUser(auth);
    	   int c = 0;
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
     	     
			   try{
				   c= contDao.countContact(false);
				   
			   } catch(AopInvocationException e) {
				   
			   }
		   }
		   return ResponseEntity.ok(new JwtResponse<String>(false,String.valueOf(c), ""));
     }
       
       
	   @RequestMapping(value = "/api/getExclu")
	   public ResponseEntity<?>  expulsion(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   List<User> users = new ArrayList();
			   try {users = user.getExclude(true);} catch(Exception e) {}
			   return ResponseEntity.ok(new JwtResponse<List<User>>(false,users,"La liste des entités exlues")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @RequestMapping(value = "/api/toExclu")
	   public ResponseEntity<?>  toExclude(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   List<User> users = new ArrayList();
			   try {users = user.getExclude(false);} catch(Exception e) {}
			   return ResponseEntity.ok(new JwtResponse<List<User>>(false,users,"La liste des entités non  exlues")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @RequestMapping(value = "/api/getBoursier")
	   public ResponseEntity<?>  getBoursier(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   List<User> users = new ArrayList(); 
			   
			   try {users = user.getBoursier(RoleName.STUDENT);} catch(Exception e) {}
			   return ResponseEntity.ok(new JwtResponse<List<User>>(false,users,"La liste des boursiers")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   @RequestMapping(value = "/api/getFinissant")
	   public ResponseEntity<?>  getFinnisant(Authentication auth) {
		   UserEntity  utt = getUser(auth);
		   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
			   List<User> users = new ArrayList(); 
			   try {users = user.getFinissant(RoleName.STUDENT);} catch(Exception e) {}
			   return ResponseEntity.ok(new JwtResponse<List<User>>(false,users,"La liste des finissants")); 
		   }
		   else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	      }
		   
	   }
	   
	   
	    @RequestMapping(value = "/api/getAllParcoursNew/{state}/{code}")
		public ResponseEntity<?> getAllPcNew(@PathVariable("code") String code, @PathVariable("state") boolean state) {
		    Promo_af paf = pafDao.getActived();
			List<User> p = pDao.getAllParcoursNew(state, code, paf.getId());
			return ResponseEntity.ok(new JwtResponse<List<User>>(true, p, "parcours"));
		}
	       @Modifying
	 	   @Transactional
	       @RequestMapping(value = "/api/setLO/{idu}/{idp}/{state}")
		   public ResponseEntity<?>  setLO(Authentication auth,@PathVariable("idu") Long idu,@PathVariable("idp") Long idp, @PathVariable("state") boolean s) {
			   UserEntity  utt = getUser(auth);
			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
				    user.setLover(idu,s);
				    if(s) {
				      pars.closeOneForEnd(idp,!s);
				    }
				   return ResponseEntity.ok(new JwtResponse<String>(false,"","La liste des finissants")); 
			   }
			   else {
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		      }
			   
		   }
	    @Modifying
	 	@Transactional
	    @RequestMapping(value = "/api/closeOnePars/{idp}/{state}")
		   public ResponseEntity<?>  setLO(Authentication auth,@PathVariable("idp") Long idp, @PathVariable("state") boolean s ) {
			   UserEntity  utt = getUser(auth);
			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)) {
				    pars.closeOneForEnd(idp,s);
				   return ResponseEntity.ok(new JwtResponse<String>(false,"","La liste des finissants")); 
			   }
			   else {
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		      }
			   
		   }
	    
	    
	    @Modifying
	 	@Transactional
	    @RequestMapping(value = "/api/setMoyenGenFini")
		   public ResponseEntity<?>  setMoyenGenFini(Authentication auth) {
	    	 UserEntity  utt = getUser(auth);
			   Etablissement e = etabDao.findAll().get(0);
			   String code = e.getCode_philo();
			   Promo_af p = pafDao.getActived();
			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
				   List<MoyDto> ps = rDao.getEtudiantsToOver(p.getId(), code);
				   for(MoyDto o : ps) {
	                      double moy = (o.getNote()/o.getTotal())*o.getMoy_total();
	                      moy = round(moy, 2);
	                      Integer dec = 3;
	                      if(moy!=0.0) {
	                      if(moy>=o.getMoy_accept()) {
	                    	  dec = 1;
	                      }
	                      pars.setDec(o.getIdp(), dec, moy);
	                      }else { pars.setDec(o.getIdp(), dec,0); continue;}
					   }
				   return ResponseEntity.ok(new JwtResponse<String>(false,"","Etudiants finissants")); 
			   } else {
				   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		      }
			   
		  }
	    
	           @Modifying
	 	       @Transactional
	 	       @RequestMapping(value = "/api/setMoyenGen/{id}")
	 		   public ResponseEntity<?>  setMoyenGen (Authentication auth,@PathVariable("id") Long id) {
	 	    	 UserEntity  utt = getUser(auth);
	 			 
	 			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
	 				   List<MoyDto> ps = rDao.getEtudiantsToMG(id);
	 				  for(MoyDto o : ps) {
	                      double moy = (o.getNote()/o.getTotal())*o.getMoy_total();
	                      moy = round(moy, 2);
	                      Integer dec = 3;
	                      if(moy!=0.0) {
	                      if(moy>=o.getMoy_accept()) {
	                    	  dec = 1;
	                      }else if(moy>= o.getMoy_rep() && moy<o.getMoy_accept()) {
	                    	  dec=2;
	                      } 
	                      pars.setDec(o.getIdp(), dec, moy);
	                      }else { pars.setDec(o.getIdp(), dec,0); continue;}
					   }
	 				   return ResponseEntity.ok(new JwtResponse<String>(false,"","Etudiants finissants")); 
	 			   } else {
	 				   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	 		      }
	 			   
	 		  }
	           
	        
	           public static double round(double value, int places) {
	        	    if (places < 0) throw new IllegalArgumentException();
	        	    BigDecimal bd = BigDecimal.valueOf(value);
	        	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	        	    return bd.doubleValue();
	        	}
	           
	        @RequestMapping(value = "/api/addOneStudentToPromoFrag/{idf}/{idp}")
	       	public ResponseEntity<?> addOneStudenToPromoFrag(Authentication auth,@PathVariable("idf") Long idf, @PathVariable("idp") Long idp) {
	        	 UserEntity  utt = getUser(auth);
	 		 if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
	       		Parcours pc = pars.getOne(idp);
	       		PromoFrag pfg = pfDao.findById(idf).get();
	       				Parcours_frag fc = new Parcours_frag();
	       				String code = pc.getCode() + "-" + idf;
	       				Parcours_frag fct = fpDao.findByCode(code);
	       				if (fct == null) {
	       					fc.setCode(code);
	       					fc.setCode_student(pc.getCode_student());
	       					fc.setNom(pc.getNom());
	       					fc.setPnom(pc.getPnom());
	       					fc.setParcours(pc);
	       					fc.setSexe(pc.getSexe());
	       					fc.setPromofrag(pfg);
	       					fc.setPromo_name(pc.getPromo_name());
	       					fc = fpDao.save(fc);
	       				   return ResponseEntity.ok(new JwtResponse<Parcours>(false, null, "parcours par fragment"));
	       		        } else {
		 				   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous avez déjà ajouté cet(te) etudiant en reprise"));
		 		      }
	       		
	 			  } else {
	 				   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	 		      }
	       	}
	        
	        @RequestMapping(value = "/api/getStudentForReprise/{id}")
	       	public ResponseEntity<?> getStudentForReprise(Authentication auth, @PathVariable("id") Long id) {
	        	 UserEntity  utt = getUser(auth);
	 			 
	 			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
	        	        List<MoyDto> ps = rDao.getEtudiantsToFR(id);
	        	        return ResponseEntity.ok(new JwtResponse<List<MoyDto>>(false,ps,"Etudiants en reprise")); 
	 			   }
	       		
	 			  return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	       	}
	        
	        
	        @RequestMapping(value = "/api/getResultatAfterRep/{id}")
	       	public ResponseEntity<?> getResultatAfterRep(Authentication auth, @PathVariable("id") Long id) {
	        	   UserEntity  utt = getUser(auth);
	 			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
	        	        List<MoyDto> ps = rDao.getEtudiantsToFR(id);
	        	        for(int i=0; i<ps.size();i++) {
	        	        	//System.out.print(ps.get(i).getNote()+"<=====================|"+ps.get(i).getNom()+" "+ps.get(i).getPnom()+"|=====================>"+ps.get(i).getTotal()+"\n");
	        	        	MoyDto nr = rDao.getMoyenAfterRep(id,ps.get(i).getIdp());
	        	        	if(nr!=null) {
	        	        	// System.out.print(nr.getNote()+"<=====================|"+nr.getNom()+" "+nr.getPnom()+"|=====================>"+nr.getTotal()+"\n");
	        	        	 ps.get(i).setNote_ar(nr.getNote());
	        	        	 ps.get(i).setTotal_ar(nr.getTotal());
	        	        	} else {
	        	        		ps.get(i).setNote_ar(0);
	        	        	    ps.get(i).setTotal_ar(0);
	        	        	}
	        	        }
	        	        return ResponseEntity.ok(new JwtResponse<List<MoyDto>>(false,ps,"Resultats des etudiants en reprise")); 
	 			   }
	       		
	 			  return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	       	}
	        
	        @RequestMapping(value = "/api/getRepCond/{id}")
	       	public ResponseEntity<?> getRepCond(Authentication auth, @PathVariable("id") Long id) {
	        	   UserEntity  utt = getUser(auth);
	 			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
	        	        Integer pf = promo.findById(id).get().getPromo_af().getType_reprise();
	        	        return ResponseEntity.ok(new JwtResponse<Integer>(false,pf,"Type reprise")); 
	 			   }
	 			  return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	       	}
	        
	           @Modifying
	 	       @Transactional
	 	       @RequestMapping(value = "/api/setMoyenGenAR/{id}")
	 		   public ResponseEntity<?>  setMoyenGenAF (Authentication auth,@PathVariable("id") Long id) {
	 	    	 UserEntity  utt = getUser(auth);
	 			 
	 			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
	 				   Integer pf = promo.findById(id).get().getPromo_af().getType_reprise();
	 				   List<MoyDto> ps = rDao.getEtudiantsToFR(id);
	 				   for(int i=0; i<ps.size();i++) {
	        	        	MoyDto nr = rDao.getMoyenAfterRep(id,ps.get(i).getIdp());
	        	        	if(nr!=null) {
	        	             ps.get(i).setNote_ar(nr.getNote());
	        	        	 ps.get(i).setTotal_ar(nr.getTotal());
	        	        	} else {
	        	        		ps.get(i).setNote_ar(0);
	        	        	    ps.get(i).setTotal_ar(0);
	        	        	}
	        	        }
	 				  for(MoyDto o : ps) {
	 					  double moy =0;
	 					  if(o.getNote_ar()==0) {
	                         moy = (o.getNote()/o.getTotal())*o.getMoy_total();
	 					   }else {
	 						double  ma = (o.getNote()/o.getTotal())*o.getMoy_total();
	 						double  mb = (o.getNote_ar()/o.getTotal_ar())*o.getMoy_total();
	 						if(pf==1) {
	 							moy = mb;
	 						}else if(pf==2) {
	 							if(ma+mb>0) {
	 							 moy = (ma+mb)/2;
	 							} else {
	 							   moy = (o.getNote()/o.getTotal())*o.getMoy_total();
	 							}
	 						}
	 					  }
	                      moy = round(moy, 2);
	                      Integer dec = 3;
	                      if(moy!=0.0) {
	                      if(moy>=o.getMoy_accept()) {
	                    	  dec = 1;
	                      }else if(moy>= o.getMoy_rep() && moy<o.getMoy_accept()) {
	                    	  dec=2;
	                      } 
	                      pars.setDec(o.getIdp(), dec, moy);
	                      }else { pars.setDec(o.getIdp(), dec,0); continue;}
					   }
	 				   return ResponseEntity.ok(new JwtResponse<String>(false,"","Etudiants finissants")); 
	 			   } else {
	 				   return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
	 		      }
	 			   
	 		  }
	           
	           
	            @RequestMapping(value = "/api/getDecisionFinale/{id}")
		       	public ResponseEntity<?> getDecisionFinale(Authentication auth,  @PathVariable("id") Long id) {
		        	   UserEntity  utt = getUser(auth);
		 			   if( (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) )) {
		 				    //System.out.print(id);
		        	        List<User> users = pars.getDecisionFinale(id);
		        	        return ResponseEntity.ok(new JwtResponse<List<User>>(false,users,"Decision finale")); 
		 			   }
		 			  return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		       	}
       
       
       

}

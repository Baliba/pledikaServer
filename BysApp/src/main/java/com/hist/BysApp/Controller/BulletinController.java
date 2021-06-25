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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;


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
import com.hist.BysApp.Request.CaisseReq;
import com.hist.BysApp.Request.EditNameRequest;
import com.hist.BysApp.Request.NotRequest;
import com.hist.BysApp.Request.ParcoursRequest;
import com.hist.BysApp.Request.PayReq;
import com.hist.BysApp.Request.RegisterRequest;
import com.hist.BysApp.Request.SetNoteRequest;
import com.hist.BysApp.Request.UserRequest;
import com.hist.BysApp.Response.AppResponse;
import com.hist.BysApp.Response.CaisseResp;
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
import com.hist.BysApp.entities.paiement.Caisse;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.CycleVersement;
import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.paiement.PaiementAdmission;
import com.hist.BysApp.entities.paiement.Payment;
import com.hist.BysApp.entities.paiement.Payroll;
import com.hist.BysApp.entities.promo.Course;
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
import com.hist.BysApp.model.BulletinGeneral;
import com.hist.BysApp.model.BulletinGeneralPF;
import com.hist.BysApp.model.FicheFourniture;
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

import Palmares.Cours;
import Palmares.Etudiant;
import Palmares.MResults;
import Palmares.PFModel;
import Palmares.Palmares;
import models.MParcours;

@Controller 
public class BulletinController {
	// Bulletin
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
	ProgDao progDao;
	
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
    
        @RequestMapping(value = "/api/getBasePeriode/{id}")
  		public ResponseEntity<?> getParcoursForEC(Authentication auth,@PathVariable("id") Long id) {
  			   UserEntity  utt = getUser(auth);
  			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
  				   List<PromoFrag> pf = pfDao.findBasePeriod(id);
  				   return ResponseEntity.ok(new JwtResponse<List<PromoFrag>>(true, pf, "Liste des periodes basic")); 
  			   } else {
  			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
  		      }
  		}
        
        @RequestMapping(value = "/api/resultsForBG/{id}")
  		public ResponseEntity<?> resultsForBG(Authentication auth,@PathVariable("id") Long id) {
  			   UserEntity  utt = getUser(auth);
  			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
  				   List<PFModel> pfs = pfDao.findBasePeriodPF(id);
  				   List<Parcours>  ps =  pars.getParcours(id);
  				   List<List<Results>>   lor = Arrays.asList();
  				   HashMap<Long,HashMap<Long,List<Results>>> map = new HashMap<>(); 
  				   HashMap<Long,Parcours> pr = new HashMap<>(); 
  				   HashMap<String,PFModel> fg = new HashMap<>();
  				   for (Parcours user: ps) {
  					  HashMap<Long,List<Results>> res = new HashMap<>(); 
  					  for (PFModel frag: pfs) {
  					      res.put(frag.getId(),rDao.getBulletinGen(user.getId(), frag.getId()));
  					      fg.put(""+frag.getId()+"",frag);
    				   } 
  					 map.put(user.getId(),res);
  					 pr.put(user.getId(),user);
  				   }
  				    BulletinGeneralPF bg = new BulletinGeneralPF();
  				    bg.setResults(map);
  				    bg.setParcours(pr);
  				    bg.setPromofrag(fg);
  				   return ResponseEntity.ok(new JwtResponse<BulletinGeneralPF>(true, bg, "Bulletin")); 
  			   } else {
  			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
  		      }
  		}
        
        
        @RequestMapping(value = "/api/resultsForOBG/{id}/{idu}")
  		public ResponseEntity<?> resultsForOBG(Authentication auth,@PathVariable("id") Long id, @PathVariable("idu") Long idu) {
        	UserEntity  utt = getUser(auth);
			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
				   List<PFModel> pfs = pfDao.findBasePeriodPF(id);
				   List<Parcours>  ps =  pars.getOParcours(id,idu);
				   List<List<Results>>   lor = Arrays.asList();
				   HashMap<Long,HashMap<Long,List<Results>>> map = new HashMap<>(); 
				   HashMap<Long,Parcours> pr = new HashMap<>(); 
				   HashMap<String,PFModel> fg = new HashMap<>();
				   for (Parcours user: ps) {
					  HashMap<Long,List<Results>> res = new HashMap<>(); 
					  for (PFModel frag: pfs) {
					      res.put(frag.getId(),rDao.getBulletinGen(user.getId(), frag.getId()));
					      fg.put(""+frag.getId()+"",frag);
 				   } 
					 map.put(user.getId(),res);
					 pr.put(user.getId(),user);
				   }
				    BulletinGeneralPF bg = new BulletinGeneralPF();
				    bg.setResults(map);
				    bg.setParcours(pr);
				    bg.setPromofrag(fg);
				   return ResponseEntity.ok(new JwtResponse<BulletinGeneralPF>(true, bg, "Bulletin")); 
			   } else {
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		      }
  		}
        
        @RequestMapping(value = "/api/getProgrammes")
  		public ResponseEntity<?> getProgrammes(Authentication auth) {
  			   UserEntity  utt = getUser(auth);
  			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
  				   List<Programme> pf = progDao.findAll();
  				   return ResponseEntity.ok(new JwtResponse<List<Programme>>(true, pf, "Liste des programmes")); 
  			   } else {
  			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
  		      }
  		}
	    
        
        @RequestMapping(value = "/api/getFicheFournitures")
  		public ResponseEntity<?> getFicheFournitures(Authentication auth) {
  			   UserEntity  utt = getUser(auth);
  			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
  				   List<Niveau> nivs = nivRep.findAll();
  				   List<FicheFourniture> ff = new ArrayList<FicheFourniture>();
  				   for(Niveau niv : nivs) {
  					List<Programme> prog = progDao.getProg(niv.getCode());
  					FicheFourniture f = new FicheFourniture();
  					List<List<ProgFourniture>> pfs = new ArrayList<List<ProgFourniture>>();
  					f.setCopaie(niv.getOption().getCopaie()); 
  					for(Programme pg : prog) {
  						List<ProgFourniture> pff = pg.getProgfourniture();
  						pfs.add(pff);
  					}
  					f.setName(niv.getName());
  					f.setLpf(pfs);
  					f.setProgramme(prog);
  					ff.add(f);
  				   }
  				   return ResponseEntity.ok(new JwtResponse<List<FicheFourniture> >(true, ff, "Liste des programmes")); 
  			   } else {
  			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
  		      }
  		}
        
        @RequestMapping(value = "/api/setCurrentYear/{id}")
  		public ResponseEntity<?> setCurrentYear(Authentication auth,@PathVariable("id") Long id) {
        	   UserEntity  utt = getUser(auth);
			   if( utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER) || utt.getRole().getName().equals(RoleName.PROF)) {
				   Etablissement e = etabDao.findAll().get(0);
				   e.setCurrent_year(id);
				   etabDao.save(e);
				   return ResponseEntity.ok(new JwtResponse<String>(true,null, "")); 
			   } else {
			       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		      }
        }
        
        @RequestMapping(value = "/api/changeMyPin/{pin}")
  		public ResponseEntity<?> changePin(Authentication auth, @PathVariable("pin") int pin) {
        	   UserEntity ut = getUser(auth);
    			ut.setPin(pin);
    			ut = u.save(ut);
    			return ResponseEntity.ok(new JwtResponse<UserEntity>(false,ut, "Pin modifié"));
        }
        
        
 
        @Autowired
        CaisseDao csDao;
        
        
        @RequestMapping(value = "/api/caisse/{pin}/{code}/{tcs}", method = RequestMethod.POST)
  		public ResponseEntity<?> caisse(@PathVariable("tcs") int tcs,@RequestBody CaisseReq cr,Authentication auth,@PathVariable("pin") String pin, @PathVariable("code") String  code) {
        	       UserEntity u = user.findByCode(code);
        	       if (u!=null) {
        	       if(u.getRole().getName().equals(RoleName.MASTER)) {
        	       UserEntity  ou = getUser(auth);
        	       Promo_af af = pafDao.getActived();
        	       if(ou.getId()!=u.getId()) {
        	    	   int npin  =  Integer.parseInt(pin);
	        	       if(u.getPin()==npin) {
	        	    	   Caisse c = new Caisse();
	        	    	   c.setMontant(cr.getMontant());
	        	    	   c.setRaison(cr.getRaison());
	        	    	   c.setType_cs(tcs);
	        	    	   c.setAcad(af.getId());
	        	    	   c.setId_maker(u.getId());
	        	    	   c.setId_caissier(ou.getId());
	        	    	   csDao.save(c);
	        	    	   return ResponseEntity.ok(new JwtResponse<String>(false,null, "Encaissement reussit")); 
	        	       }else {
	        	    	   return ResponseEntity.ok(new JwtResponse<String>(true,null, "Pin incorrect")); 
	        	       }
        	       }
        	       else {
        	    	   return ResponseEntity.ok(new JwtResponse<String>(true,null, "Transaction non autorisée")); 
        	       }
        	       }else {
        	    	   return ResponseEntity.ok(new JwtResponse<String>(true,null, "Code incorrect")); 
        	       }
        	       }else {
        	    	   return ResponseEntity.ok(new JwtResponse<String>(true,null, "Vous n'etes pas autorisé")); 
        	       }
			  
        }
        
        @RequestMapping(value = "/api/getCaisse")
  		public ResponseEntity<?> getCaisse(Authentication auth) {
        	    UserEntity ut = getUser(auth);
        	    Promo_af af = pafDao.getActived();
                List<Caisse> csm= csDao.getCaisseForMaker(ut.getId(), af.getId());
                List<Caisse> csc= csDao.getCaisseForCassier(ut.getId(), af.getId());
                CaisseResp cr = new CaisseResp(csm, csc);
    			return ResponseEntity.ok(new JwtResponse<CaisseResp>(false,cr, "Liste de vos transactions"));
        }
        
        @RequestMapping(value = "/api/getPalmares/{id}")
  		public ResponseEntity<?> getPalmares(Authentication auth, @PathVariable("id") Long id) {
        	    UserEntity ut = getUser(auth);
        	    Palmares pal = new Palmares();
        	    List<Cours> cours = fcDao.getCoursByFrag(id);
        	    List<Etudiant> ets = fpDao.getEtudiants(id);
     
        	   for(int i = 0; i<ets.size(); i++) { 
        	    	List<MResults> results  = new ArrayList<MResults>();
        	    	for(Cours c : cours) {
        	    	   MResults mr = new MResults(c.getId(),c.getName(),0,c.getNote_total());	  
        	    	   results.add(mr);
        	    	}
        	      ets.get(i).setResults(results);
        	      ets.get(i).setMresults(rDao.getResults(ets.get(i).getId(),id));
        	    }
	

        	    pal.setCours(cours);
        	    pal.setEtudiants(ets);
    			return ResponseEntity.ok(new JwtResponse<Palmares>(false,pal, ""));
        }
        
        @RequestMapping(value = "/api/getPalmaresV/{id}")
  		public ResponseEntity<?> getPalmaresv(Authentication auth, @PathVariable("id") Long id) {
        	    UserEntity ut = getUser(auth);
        	    Palmares pal = new Palmares();
        	    List<Cours> cours = fcDao.getCoursByFrag(id);
        	    List<Etudiant> ets = fpDao.getEtudiants(id);
        	   for(int i = 0; i<ets.size(); i++) { 
        	    	List<MResults> results  = new ArrayList<MResults>();
        	    	for(Cours c : cours) {
        	    	   MResults mr = new MResults(c.getId(),c.getName(),0,c.getNote_total());	  
        	    	   results.add(mr);
        	    	}
        	      ets.get(i).setResults(results);
        	    }
	

        	    pal.setCours(cours);
        	    pal.setEtudiants(ets);
    			return ResponseEntity.ok(new JwtResponse<Palmares>(false,pal, ""));
        }
        
        @Modifying
     	@Transactional
        @RequestMapping(value = "/api/syncEtudiant")
  		public ResponseEntity<?> syncEtudiant(Authentication auth) {
        	    List<MParcours> ps = pars.getAllActParcours();
        	    for (MParcours p : ps) {
        	    	 String pm = p.getCprom_name();
        	    	 if(pm==null || pm.equals("")) {
        	    	  user.setPromoName(p.getPromo_name(), p.getId());
        	    	}
        	    }
    			return ResponseEntity.ok(new JwtResponse<List<MParcours>>(false,ps, ""));
        }
        
        @Modifying
     	@Transactional
        @RequestMapping(value = "/api/deleteCoursFromProgramme")
  		public ResponseEntity<?> deleteCoursFromProgramme(Authentication auth) {
        	    List<MParcours> ps = pars.getAllActParcours();
        	    for (MParcours p : ps) {
        	    	 String pm = p.getCprom_name();
        	    	 if(pm==null || pm.equals("")) {
        	    	  user.setPromoName(p.getPromo_name(), p.getId());
        	    	}
        	    }
    			return ResponseEntity.ok(new JwtResponse<List<MParcours>>(false,ps, ""));
        }
        
        @Modifying
     	@Transactional
        @RequestMapping(value = "/api/deleteResult/{c}")
  		public ResponseEntity<?> deleteResult (Authentication auth,  @PathVariable("c") Long c) {
        	    this.rDao.deleteResult(c);
    			return ResponseEntity.ok(new JwtResponse<List<MParcours>>(false,null, "Resultat effacés avec succès"));
        }
        
        
        
}

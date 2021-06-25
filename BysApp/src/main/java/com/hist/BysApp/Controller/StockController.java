/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Controller;
import com.hist.BysApp.Response.JwtResponse;
import com.hist.BysApp.dao.AdmisPaieRepo;
import com.hist.BysApp.dao.CLOPaieDao;
import com.hist.BysApp.dao.CLVDao;
import com.hist.BysApp.dao.COPaieDao;
import com.hist.BysApp.dao.DocRepo;
import com.hist.BysApp.dao.DomRepo;
import com.hist.BysApp.dao.EtabRepo;
import com.hist.BysApp.dao.FCoursDao;
import com.hist.BysApp.dao.FParcoursRepository;
import com.hist.BysApp.dao.FragDoa;
import com.hist.BysApp.dao.LNotRepo;
import com.hist.BysApp.dao.LocationDao;
import com.hist.BysApp.dao.MaterielDao;
import com.hist.BysApp.dao.NiveauRepository;
import com.hist.BysApp.dao.Niveau_relRepository;
import com.hist.BysApp.dao.NotRepo;
import com.hist.BysApp.dao.PFragDao;
import com.hist.BysApp.dao.PRFragDao;
import com.hist.BysApp.dao.PVDao;
import com.hist.BysApp.dao.ParcoursRepository;
import com.hist.BysApp.dao.PayrollDao;
import com.hist.BysApp.dao.Promo_afRepository;
import com.hist.BysApp.dao.Promo_coursRepo;
import com.hist.BysApp.dao.PromotionRepository;
import com.hist.BysApp.dao.ResultsRepository;
import com.hist.BysApp.dao.RoleRepository;
import com.hist.BysApp.dao.UserRepository;
import com.hist.BysApp.dao.VilleRepository;
import com.hist.BysApp.dao.coursDao;
import com.hist.BysApp.entities.config.Etablissement;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.grade.Option;
import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.CycleVersement;
import com.hist.BysApp.entities.paiement.PVersement;
import com.hist.BysApp.entities.paiement.Payment;
import com.hist.BysApp.entities.paiement.classe.ClasseOPaie;
import com.hist.BysApp.entities.paiement.classe.ClasseVersement;
import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.Parcours_frag;
import com.hist.BysApp.entities.promo.PromoFrag;
import com.hist.BysApp.entities.promo.Promotion;
import com.hist.BysApp.factories.Helper;
import com.hist.BysApp.service.JwtUserDetailsService;
import com.hist.BysApp.service.NotService;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class StockController {
	
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
	
	@Autowired
	CLOPaieDao cloDao;

	@Autowired
	CLVDao clvDao;
 
    @Autowired
    private UserRepository userInfoRepository;
    
    @Autowired
    private RoleRepository role;
    
    @Autowired
    private LocationDao locDao;
    
    @Autowired
    private MaterielDao matDao;
    
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
  
  @RequestMapping(value ="/api/matTstCtrl")
  public ResponseEntity<?> getPaiement(Authentication auth) {
       	   UserEntity  utt = getUser(auth);
       	   utt.setParcours(null);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,utt,"succès"));
  }
     
}

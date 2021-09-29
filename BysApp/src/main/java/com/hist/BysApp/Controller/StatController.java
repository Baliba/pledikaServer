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
import com.hist.BysApp.model.MoyDto;
import com.hist.BysApp.model.PromoDto;
import com.hist.BysApp.service.JwtUserDetailsService;
import com.hist.BysApp.service.NotService;

import Palmares.Cours;
import Palmares.StatCours;
import dto.PFrag;
import dto.Reussite;
import lombok.Data;

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
//
public class StatController {
	
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
  
    @RequestMapping(value ="/api/getStatForCours/{id}", method = RequestMethod.GET)
   	public ResponseEntity<?> getStatForCours(@PathVariable("id") Long id ) {
    List<StatCours> sc;
		List<Cours> lfcs = fcDao.getCoursByFrag(id);
		sc = new ArrayList<StatCours>();
		for(Cours c : lfcs) {
		try {		
		System.out.print("########-"+c.getName()+"_"+c.getId()+"-############\n"); 	
		Long te  =       rDao.getTotalStudent(c.getId());
		Long tp  =       rDao.getTotalPassStudentByCoursFrag(c.getId());
		StatCours stc = new StatCours(c.getName(),tp,te);
		stc.setCours(c.getCode());
		sc.add(stc);
		} catch (Exception e) {
			return ResponseEntity.ok(new JwtResponse<String>(true,"",e.getMessage()));
	     }
		}
	  return ResponseEntity.ok(new JwtResponse<List<StatCours>>(true,sc,""));
	
   	}
    
    @RequestMapping(value ="/api/getPromoFragById/{id}", method = RequestMethod.GET)
   	public ResponseEntity<?> getPromoFragById(@PathVariable("id") Long id ) {
    	PFrag pf  = pfDao.getPFragById(id);
    	return ResponseEntity.ok(new JwtResponse<PFrag>(true,pf,"Promo Frag"));
    }
    
    @RequestMapping(value ="/api/getPFragV2/{id}", method = RequestMethod.GET)
   	public ResponseEntity<?> getFByPromo(@PathVariable("id") Long id ) {
    	List<PFrag> pf  = pfDao.getFByPromo(id);
    	return ResponseEntity.ok(new JwtResponse<List<PFrag>>(true,pf,"List Promo Frag"));
    }
    
    @RequestMapping(value ="/api/getStatForCoursByPromo/{id}", method = RequestMethod.GET)
   	public ResponseEntity<?> getStatForCoursByPromo(@PathVariable("id") Long id ) {
    	// get list promo frag  by id promo 
    	PromoDto promo = this.promo.getPromoDto(id);
    	List<PFrag> pfs  = pfDao.getFByPromo(id);
       	List<StatGlobal> lsg = new ArrayList<StatGlobal>();
       	
       	// 
       	for(PFrag pf : pfs ) {
	        List<StatCours> sc = sc = new ArrayList<StatCours>();
			List<Cours> lfcs = fcDao.getCoursByFrag(pf.getId());
			for(Cours c : lfcs) {
			try {		
			// System.out.print("########-"+c.getName()+"_"+c.getId()+"-############\n"); 	
			Long te  =       rDao.getTotalStudent(c.getId());
			Long tp  =       rDao.getTotalPassStudentByCoursFrag(c.getId());
			StatCours stc = new StatCours(c.getName(),tp,te);
			stc.setCours(c.getCode());
			
			sc.add(stc);
			  } catch (Exception e) {
		     }
			}
			StatGlobal sg = new StatGlobal(pf.getCode(), sc);
			sg.setId_frag(pf.getId());
			lsg.add(sg);
       	}
       	FinalStatGlobal<List<StatGlobal>> fsg = new FinalStatGlobal<List<StatGlobal>>();
       	fsg.setLsg(lsg);
       	fsg.setPromo(promo);
       	
	  return ResponseEntity.ok(new JwtResponse<FinalStatGlobal>(true,fsg,""));
   	}
    
    @RequestMapping(value = "/api/reussite/{idp}")
	 public ResponseEntity<?>  reussite(Authentication auth, @PathVariable("idp") Long idp) {
    	 PromoDto promo = this.promo.getPromoDto(idp);
		 List<Reussite> rss = new ArrayList<>();   
		 List<PromoFrag>  pfs = pfDao.getFragByPromo(idp);
		 Long  total_e =  pars.countStudent(idp) ;
		 Long  total_eg = pars.countStudentBySexe(idp);
     	 for(PromoFrag pf : pfs) {
		          Reussite rs = new Reussite();
		          rs.setId(pf.getId());
		          rs.setPeriod(pf.getCode());
		          rs.setTotal_class(total_e);
		          rs.setTotal_g(total_eg);
		          rs.setTotal_f(total_e-total_eg);
//		          //  student IN 
		         Long tic = fpDao.countStudent(pf.getId());
		         rs.setTotal_iclass(tic); 
		         Long tig = fpDao.countStudentBySexe(pf.getId());
		         rs.setTotal_ig(tig);
		         rs.setTotal_if(tic-tig);
		         List<MoyDto>  tp = rDao.getStudentPass(pf.getId());
		         Long ltp = Long.parseLong(tp.size()+"");
		         rs.setTotal_pass(ltp);
		         Long te = tic-ltp;
		         rs.setTotal_echec(te);
		         
		         List<MoyDto>  tps = rDao.getStudentPassBySexe(pf.getId());
		         Long ltps = Long.parseLong(tps.size()+"");
		         rs.setTotal_g_pass(ltps);
		         Long lge = tig-ltps;
		         rs.setTotal_g_echec(lge);
		         Long tfp = ltp-ltps;
		         rs.setTotal_f_pass(tfp);
		         rs.setTotal_f_echec(te-lge);
		         rss.add(rs);
     		 }    
     	 
     	FinalStatGlobal<List<Reussite>> fsg = new FinalStatGlobal<List<Reussite>>();
		fsg.setPromo(promo);  
		fsg.setLsg(rss);
		return ResponseEntity.ok(new JwtResponse<FinalStatGlobal<List<Reussite>>>(false,fsg, "")); 
		 
	 }
    
 
     
}

   @Data
   class FinalStatGlobal<T> {
	   PromoDto promo;
	   T lsg;
	   public FinalStatGlobal() {}
   }

  @Data
  class StatGlobal{
	String control;
	List<StatCours> sc;
	Long id_frag;
	public StatGlobal(String control, List<StatCours> sc) {
		super();
		this.control = control;
		this.sc = sc;
	}
	
 }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.Controller;

import com.hist.BysApp.Helper.RoleName;
import com.hist.BysApp.Request.ParcoursRequest;
import com.hist.BysApp.Request.RegisterRequest;
import com.hist.BysApp.Response.ErrorResponse;
import com.hist.BysApp.Response.JwtResponse;
import com.hist.BysApp.component.StaticData;
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
public class UserInfoController {
	
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
    
       @Transactional
       @RequestMapping(value = "/api/initPaiementByClasse/{code}")
	   public ResponseEntity<?>  initPaiementByClasse(Authentication auth, @PathVariable("code") String code) {
    	   UserEntity  ut = getUser(auth);
		   if(ut.getRole().getName().equals(RoleName.MANAGER) || ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
		       Niveau niv = nivRep.findByCode(code);
		       Option op = niv.getOption();
		       List<ClasseOPaie> clops = new ArrayList<>();
		       for(CycleOPaie c : op.getCopaie()) {
		    	   Collection<CycleVersement> cvs = c.getCversement();
		    	   String cocode = c.getCode()+"_"+niv.getCode();
		    	   ClasseOPaie clop = cloDao.findByCode(cocode);
		    	   if(clop==null) {
			    	   clop = new ClasseOPaie (c,niv.getCode()); 
			    	   clop.setNiveau(niv);
			    	   clop = cloDao.save(clop);
		    	   }
		    	   // 
		    	   Collection<ClasseVersement> clvs = new ArrayList<>();
		    	   for(CycleVersement cv : cvs) {
		    		String clcode =   cv.getCode()+"_"+niv.getCode();
		    		ClasseVersement clv = clvDao.findByCode(clcode); 
		    		if(clv==null) {
		    			clv = new ClasseVersement(cv, niv.getCode());
		    			clv.setCopaie(clop);
		    			clv = clvDao.save(clv);
		    		}
		    		clvs.add(clv);
		    	   }
		    	   clop.setCversement(clvs);
		    	   clop = cloDao.save(clop);
		        }
		        niv.setMontant_admis_classe(op.getMontant_admis());
		        Niveau nv = nivRep.findByCode(code);
			   return ResponseEntity.ok(new JwtResponse<Collection<ClasseOPaie>>(false,nv.getCopaie(),"Type paiement")); 
		   } else {
		       return ResponseEntity.ok(new JwtResponse<UserEntity>(true,null,"Vous n'etes pas autorisé"));
		   }
	   
	}
       

   	@RequestMapping(value = "/api/addStudentToPromoFrag/{id}/{idp}")
   	public ResponseEntity<?> addStudenToPromoFrag(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {
   		List<Parcours> pcs = pars.getParcours(idp);
   		// List<Parcours_frag> fcs = new ArrayList<Parcours_frag>();
   		PromoFrag pfg = pfDao.findById(id).get();
   		if (pcs.size() > 0) {
   			// Parcours_frag pf = fpDao.findById(id).get();
   			for (Parcours pc : pcs) {
   				Parcours_frag fc = new Parcours_frag();
   				String code = pc.getCode() + "-" + id;
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
   					// fcs.add(fc);
   				}
   			}
   		}
   		return ResponseEntity.ok(new JwtResponse<Parcours>(true, null, "parcours par fragment"));
   	}


	@Transactional
	@RequestMapping(value = "/api/addStudentToPromo", method = RequestMethod.POST)
	public ResponseEntity<?> addStudentToPromo(@RequestBody ParcoursRequest u) {
        Etablissement e = etabDao.findAll().get(0);
		// get user
		Optional<UserEntity> s = user.findById(u.getId_student());
		// check if user present
		if (s.isPresent()) {
			UserEntity nu = s.get();
			// check promo
			Optional<Promotion> prom = promo.findById(u.getId_promo());
			// check is present
			if (prom.isPresent()) {
				Promotion np = prom.get();
				if(np.getMax_student()<=np.getParcours().size()) {
					return ResponseEntity.ok(new ErrorResponse("Le nombre maximum d'etudiants pour cette promotion est "+np.getMax_student(), true));
				}
				
				// create parcours
				Parcours p = new Parcours();
				p.setId_promo(np.getId());
				p.setActived(true);
				// add user and promo
				String code = nu.getCode() + "-" + u.getId_promo();
				p.setCode(code);
				p.setId_student(nu.getId());
				p.setSexe(nu.getSexe());
				p.setNom(nu.getLastName());
				p.setPnom(nu.getFirstName());
				p.setGranted(nu.getGranted());
				p.setCode_student(nu.getCode());
				p.setUser(nu);
				p.setPromo_name(np.getCode());
				p.setPromotion(np);
				p.setNiv_code(np.getCode_niveau());
				p.setCycle_code(np.getCode_cycle());
				p.setOption_paiement(u.getId_opaie());
				p.setMode_paiement(e.getMode_paiement());
				// create list versement for parcours
				List<PVersement> pv =  new ArrayList<PVersement>();
				List<PVersement> ppv = new ArrayList<PVersement>();
				Long codep = u.getId_opaie();
				Optional<CycleOPaie> cop = this.cop.findById(codep);
				if(e.getMode_paiement()==1) {
					// in this section we config the payment for cycle 
				 Collection<CycleVersement> cvs = cop.get().getCversement();
				for (CycleVersement cv : cvs) {
					if(cv.type_verse!=3 && cv.isActived() && cv.getMontant()==0) {
						return ResponseEntity.ok(new ErrorResponse("Paiement non configurer pour ce cycle", true));
					}
				 }
				for (CycleVersement cv : cvs) {
					if(cv.type_verse!=3 && cv.isActived() && cv.getMontant()>0) {
						PVersement npv = new PVersement();
						npv.setName(cv.getName() + "-" + u.getId_student() + "-" + u.getId_promo());
						npv.setJour_limit(cv.getJour_limit());
						npv.setMois_limit(cv.getMois_limit());
						npv.setMontant_pay((double) 0);
						npv.setCode(cv.getCode());
						npv.setActived(true);
						npv.setCode_share(cv.getCode());
						npv.setType_verse(cv.getType_verse());
						npv.setPos(cv.getPos());
						if (cv.getMontant() > 0) {
							if (nu.getGranted() == 0) {
								npv.setMontant_to_pay(cv.getMontant());
								npv.setMontant_init((double) 0);
							} else if (nu.getGranted() == 1) {
								npv.setMontant_to_pay((double) 0);
								npv.setMontant_init(cv.getMontant());
							} else {
								npv.setMontant_to_pay(cv.getMontant() / 2);
								npv.setMontant_init(cv.getMontant()/2);
							}
							pv.add(npv);
						} else {
							return ResponseEntity.ok(new ErrorResponse("Paiement non configurer pour ce cycle", true));
						}
					 }
					}
				} else { 
					    // in this section we config pay by grade
					    CycleOPaie ncop = cop.get();
					    String code_niv = prom.get().getNiveau_rel().getNiveau().getCode();
					    ClasseOPaie clop = cloDao.findByCode(ncop.getCode()+"_"+code_niv);
					    Collection<ClasseVersement> cvs = clop.getCversement();
						for (ClasseVersement cv : cvs) {
							if(cv.type_verse!=3 && cv.isActived() && cv.getMontant()==0) {
								return ResponseEntity.ok(new ErrorResponse("Paiement non configurer pour ce cycle", true));
							}
						 }
						for (ClasseVersement cv : cvs) {
							if(cv.type_verse!=3 && cv.isActived() && cv.getMontant()>0) {
								PVersement npv = new PVersement();
								npv.setName(cv.getName() + "-" + u.getId_student() + "-" + u.getId_promo());
								npv.setJour_limit(cv.getJour_limit());
								npv.setMois_limit(cv.getMois_limit());
								npv.setMontant_pay((double) 0);
								npv.setCode(cv.getCode());
								npv.setActived(true);
								npv.setCode_share(cv.getCode());
								npv.setType_verse(cv.getType_verse());
								npv.setPos(cv.getPos());
								if (cv.getMontant() > 0) {
									if (nu.getGranted() == 0) {
										npv.setMontant_to_pay(cv.getMontant());
										npv.setMontant_init((double) 0);
									} else if (nu.getGranted() == 1) {
										npv.setMontant_to_pay((double) 0);
										npv.setMontant_init(cv.getMontant());
									} else {
										npv.setMontant_to_pay(cv.getMontant() / 2);
										npv.setMontant_init(cv.getMontant()/2);
									}
									pv.add(npv);
								} else {
									return ResponseEntity.ok(new ErrorResponse("Paiement non configurer pour ce cycle", true));
								}
							 }
							}	
				}
			
               // there is no payment 
               if(pv.size()<=0) {
        	    return ResponseEntity.ok(new ErrorResponse("Paiement non configurer pour ce cycle",true));
        	   }
			    // *** 
				Parcours tnp = pars.findByCode(code);
				closeOne(p.getId(), p.getCode_student(), false);
				if (tnp == null) {
					p = pars.save(p);
					for (PVersement npv : pv) {
						PVersement sv = pvd.findByName(npv.getName());
						if (sv == null) {
							npv.setParcours(p);
							ppv.add(pvd.save(npv));
						}
					}
					nu.setCurrent_class(prom.get().getNiveau_rel().getNiveau());
					String ncode = prom.get().getNiveau_rel().getNiveau().getNext();
					Niveau niv = nivRep.findByCode(ncode);
					String code2 = prom.get().getNiveau_rel().getNiveau().getPrev();
					Niveau niv2 = nivRep.findByCode(code2);
					nu.setNext_class(niv);
					nu.setPrev_class(niv2);
					nu.setCurrent_promo(np.getCode());
					nu = user.save(nu);
					return ResponseEntity.ok(new JwtResponse<Parcours>(false, p, ""));
				} else {
					return ResponseEntity.ok(new ErrorResponse("Etudiant existe déjà", true));
				}
			}
		}
		return ResponseEntity.ok(new ErrorResponse("", true));
	}
	@Transactional
	public void closeOne(Long id, String code, boolean state) {
		pars.closeOne(id, code, state);
	}
	
	    @RequestMapping(value ="/api/getCurrentUser")
      	public ResponseEntity<?> getPaiement(Authentication auth) {
       	   UserEntity  utt = getUser(auth);
       	   utt.setParcours(null);
		   return ResponseEntity.ok(new JwtResponse<UserEntity>(false,utt,"succès"));
      	}
     
}

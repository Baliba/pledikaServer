/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.component;

import com.hist.BysApp.Helper.RoleName;
import com.hist.BysApp.dao.COPaieDao;
import com.hist.BysApp.dao.CVDao;
import com.hist.BysApp.dao.DocRepo;
import com.hist.BysApp.dao.DomRepo;
import com.hist.BysApp.dao.EtabRepo;
import com.hist.BysApp.dao.EtatRepository;
import com.hist.BysApp.dao.FragDoa;
import com.hist.BysApp.dao.NiveauRepository;
import com.hist.BysApp.dao.OPaieDao;
import com.hist.BysApp.dao.OptionRepository;
import com.hist.BysApp.dao.PRFragDao;
import com.hist.BysApp.dao.PaysRepository;
import com.hist.BysApp.dao.ProgDao;
import com.hist.BysApp.dao.RoleRepository;
import com.hist.BysApp.dao.SalleRepository;
import com.hist.BysApp.dao.UserRepository;
import com.hist.BysApp.dao.VDao;
import com.hist.BysApp.dao.VacationRepository;
import com.hist.BysApp.dao.VilleRepository;
import com.hist.BysApp.entities.Location.Etat;
import com.hist.BysApp.entities.Location.Pays;
import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.config.Etablissement;
import com.hist.BysApp.entities.grade.Document;
import com.hist.BysApp.entities.grade.Domaine;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.grade.Option;
import com.hist.BysApp.entities.grade.Salle;
import com.hist.BysApp.entities.grade.Vacation;
import com.hist.BysApp.entities.member.Role;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.paiement.CycleVersement;
import com.hist.BysApp.entities.paiement.OPaie;
import com.hist.BysApp.entities.paiement.PRFrag;
import com.hist.BysApp.entities.paiement.Versement;
import com.hist.BysApp.entities.promo.Fragment;
import com.hist.BysApp.entities.promo.Programme;
import com.hist.BysApp.factories.Helper;
import com.hist.BysApp.service.JwtUserDetailsService;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
     
    @Autowired
    private FragDoa fDao;
    
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OptionRepository optionDao;
    
    @Autowired
    private VacationRepository vacDao;
    
    @Autowired
    private DomRepo dDao;
    
    @Autowired
    private DocRepo dcDao;
    
    @Autowired
    private NiveauRepository nivDao;
 
    @Autowired
    private EtatRepository etat;
    
    @Autowired
    private VilleRepository ville;
    @Autowired 
    PaysRepository pays;
    
    @Autowired
    private EtabRepo eDao;
    
    
    @Autowired
    private OPaieDao opDao;
    
    @Autowired
    private VDao vDao;
    
    @Autowired
    private PRFragDao prfDao;
    
    @Autowired
    private COPaieDao copDao;
    
    @Autowired
    private CVDao cvDao;
    
    @Autowired
    SalleRepository salle;
    
	HashMap<String,List<Niveau>> niveau;
	
	@Autowired
	ProgDao progDao;
    
    @Transactional 
    public void vacINF() {
       Vacation v = vacDao.findByName("Matin"); 
       if(v==null){
         Vacation mat = new Vacation("Matin","MT");
         this.vacDao.save(mat);
       }
       Vacation v1 = vacDao.findByName("Median"); 
       if(v1==null){
           Vacation mat = new Vacation("Median","MD");
           this.vacDao.save(mat);
       }
       Vacation v2 = vacDao.findByName("Soir"); 
       if(v2==null){
           Vacation mat = new Vacation("Soir","SR");
           this.vacDao.save(mat);
       }
       
    }
    @Transactional 
    public void optionINF() {
    	
    Etablissement etab =  eDao.findAll().get(0);
    	
    	for(DocConfig doc : StaticData.docConfig) {
    		 Document d  = dcDao.findByCode(doc.getDoc_code());
             if (d == null) {
       	         d = new Document(doc.getDoc_code(),doc.getDocument());
                 d = dcDao.save(d);
             }
    	}
    	
    	for(DomConfig dom : StaticData.domConfig) {
    		// select all domain
    	    Domaine d  = dDao.findByName(dom.getDomaine());
            if (d == null) {
      	        d = new Domaine(dom.getDomaine(), dom.getDom_code());
                d = dDao.save(d);
            }
           //  d  = dDao.findByName(dom.getDomaine());
             List<OptionConfig>  ocs = StaticData.setupOption().get(dom.getDom_code());
             
             for(OptionConfig os : ocs) {
           	    Option o  = optionDao.findByCode(os.getOpt_code());
                if  (o == null) {
                     o = new Option (d.getName()+"-"+os.getOption(), os.getOpt_code());
                     o.setDomaine(d);
                     o.setMontant_admis(etab.getPrix_admis());
                     optionDao.save(o);
                 }
             List<Niveau> nivs = StaticData.setNiveau().get(o.getCode()); 
             
             for(Niveau  niv : nivs) {
           	    Niveau nn  = nivDao.findByCode(niv.getCode());
                 if (nn == null) {
                     niv.setOption(o);
                     nivDao.save(niv);
                 }
           	 }
           	}
    	}
       
    }
    
    @Transactional 
    public void setEtab() {
    	Etablissement net = eDao.findByCode(StaticData.code_etab);
    	// SET ETAB
    	if(net==null) {
        Etablissement et = new Etablissement();
        et.setName(StaticData.name_etab);
        et.setAnnee_fond(StaticData.annee_fond);
        et.setCat_etab(StaticData.cat_etab);
        et.setCode(StaticData.code_etab);
        et.setDescription(StaticData.description);
        et.setEmail(StaticData.email_etab);
        et.setPhone_a(StaticData.phone_a);
        et.setAdresse(StaticData.adresse);
        et.setBackground("bg_1.png");
        et.setCurrency("HTG");
        et.setFrag_name("Ctrl");
        eDao.save(et);
    	}
    
    }
 

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
    
        if (alreadySetup)
            return;
        this.setEtab();
        this.vacINF();
        this.optionINF(); 
        // FRAG PAYROLL 
        setPRFrag("Septembre" ,1,"10","09");
    	setPRFrag("Octobre" ,2,"10","10");
    	setPRFrag("Novembre" ,3,"10","11");
    	setPRFrag("Décembre" ,4,"10","12");
    	setPRFrag("Janvier" ,5,"10","01");
    	setPRFrag("Février" ,5,"10","02");
    	setPRFrag("Mars" ,6,"10","03");
    	setPRFrag("Avril" ,7,"10","04");
    	setPRFrag("Mai" ,8,"10","05");
    	setPRFrag("Juin" ,9,"10","06");
    	setPRFrag("Juillet" ,10,"10","07");
    	setPRFrag("Août" ,11,"10","08");
       //-------------------------------
        // create examen fragment 
        createFragIfNotFound("1er Controle");
        createFragIfNotFound("2eme Controle");
        createFragIfNotFound("Bimensuel 1");
        createFragIfNotFound("3eme Controle");
        createFragIfNotFound("Bimensuel 2");
        createFragIfNotFound("4eme Controle");
        createFragIfNotFoundRP("Examen Extraordinaire 1");
        createFragIfNotFoundRP("Examen Extraordinaire 2");
        createFragIfNotFound("Fragment A");
        createFragIfNotFound("Fragment B");
        createFragIfNotFound("Fragment C");
        createFragIfNotFound("Fragment D");
        createFragIfNotFound("Fragment E");
        createFragIfNotFound("Fragment F");
        // create 
        createRoleIfNotFound(RoleName.MASTER);
        createRoleIfNotFound(RoleName.ADMIN);
        createRoleIfNotFound(RoleName.STUDENT);
        createRoleIfNotFound(RoleName.ACCOUNTING);
        createRoleIfNotFound(RoleName.PROF);
        createRoleIfNotFound(RoleName.MANAGER);
        createRoleIfNotFound(RoleName.TEST);
        createRoleIfNotFound(RoleName.MASTER_P);
        createRoleIfNotFound(RoleName.ADMIN_P);
        createRoleIfNotFound(RoleName.MANAGER_P);
        createRoleIfNotFound(RoleName.BIBLIO);
        createRoleIfNotFound(RoleName.PARENT);
        // setUp option de paiement
                
        OPaie op_1 = this.setOPaie("Option 1","O1");
   	 
        OPaie op_2 = this.setOPaie("Option 2","O2");
        // 
        setVersement("V1O1","Versement 1 Option 1","10","12",op_1);
        //
        setVersement("V1O2","Versement 2 Option 1","10","02",op_1);
        // 
        setVersement("V1O3","Versement 3 Option 1","10","04",op_1);
        // 
        setVersement("V1O4","Versement 4 Option 1","10","05",op_1);
        // ------------------------------------------------------------------ \\
        setVersement("V0O0","Versement 0 Option 2","10","10",op_2);
        //
        setVersement("V0O1","Versement 1 Option 2","10","12",op_2);
        //
        setVersement("V0O2","Versement 2 Option 2","10","02",op_2);
        // 
        setVersement("V0O3","Versement 3 Option 2","10","04",op_2);
        // 
        setVersement("V0O4","Versement 4 Option 2","10","05",op_2);
        //
        // SetUp LOCATION*************
        
        
        //*********************
        Long nu = userRepository.count();
        if(nu == 0L){ 	
        setSalle() ;
        setProgramme();
        Role adminRole = roleRepository.findByName(RoleName.MASTER);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setFirstName(StaticData.fname);
        user.setLastName(StaticData.lname);
        String password = StaticData.pass;
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
        user.setUsername(StaticData.email);
        user.setRole(adminRole);
        user.setEnabled(true);
        String code = Helper.generateCode("S","A",0L);
        user.setCode(code);
        user.setAvatar("default.png");
        userRepository.save(user);
        setupLocation();
	    this.setupPaiement();
        }
     
        alreadySetup = true;
    }
     @Transactional
     public void setupLocation() {
    	// SET PAYS 
    	 Pays p = pays.findByCode("HT");
    	 if(p==null) {
              p  = new Pays ();
              p.setName("Haiti");
              p.setCode("HT");
              p = pays.save(p); 
    	 }
              
         this.setDepart(p);
         // --------------------------     
         Etat e = etat.findByCode("OU");
         
         Ville v = ville.findByCode("PV");
         if(v==null) {
        	   v = new Ville();
               v.setName("Pétion Ville");
               v.setCode("PV");
               v.setEtat(e);
               ville.save(v); 
         }
     }
    
    @Transactional
    private void setDepart(Pays p){
    List<String> depart = Arrays.asList("Ouest ","Nord ","Nord-Est ","Nord-Ouest ","Sud ","Sud-Est ","Centre","Artibinites ","Nippes","Grand'Anse");
    List<String> code = Arrays.asList("OU ","ND ","NE ","NO ","SD ","SE ","CT","AT ","NP","GA");
    int i = 0;
    for(String d : depart) { 
     String c = code.get(i);	
     Etat ne = etat.findByCode(c);
     if(ne == null) {
    	 Etat e = new Etat();
         e.setName(d);
         e.setCode(c);
         e.setPays(p);
         e = etat.save(e); 
     }
     i++;
     }
    }
    
    @Transactional
    private void setupPaiement() {
    	
    	
    	
    	List<OPaie>  op =  opDao.findAll();
    	List<Option> ot =  optionDao.findAll();
    	
    	for(Option t : ot) {
    	   for(OPaie o : op) {
    			 String code = o.getCode()+"_"+t.getCode();
    			 CycleOPaie cop = copDao.findByCode(code);
    			 if(cop==null) {
    				 
    				  CycleOPaie nc = new CycleOPaie();
    				     nc.setName(o.getName()+" "+t.getName());
    	    			 nc.setCode(code);
    	    			 nc.setOption(t);
    	    			 nc.setActived(true);
    				     nc = copDao.save(nc);
    				     List<Versement>   lv =  vDao.getVersementById(o.getId());
    				     // System.out.println("#################################"+lv.size());
    			        if(lv.size()>0) {
    			         int pos = 1 ; 	
    				     for(Versement v : lv) {
    				    	  String vcode =  v.getCode()+"_"+t.getCode();
    				    	  CycleVersement  cv = cvDao.findByCode(vcode);
    				    	  if (cv==null) {
    				    		CycleVersement ncv =   new CycleVersement();
    				    		String name = v.getName()+" "+t.getName();
    				    		ncv.setCode(vcode);  
    				    		ncv.setName(name);
    				    		ncv.setJour_limit(v.getJour_limit());
    				    		ncv.setMois_limit(v.getMois_limit());
    				    		ncv.setCopaie(nc);
    				    		ncv.setType_verse(v.getType_verse());
    				    		ncv.setActived(true);
    				    		ncv.setPos(pos);
    				    		pos++;
    				    		cvDao.save(ncv);
    				    	  }
    				     }
    			   }

    			 }
        	}
    	}
        
    }
 
  
    @Transactional
    private Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
    
    @Transactional
    private void setVersement(String code , String name,String j,String m, OPaie p) {
    	Versement vo = vDao.findByCode(code);
        if (vo == null) {
        	 vo = new Versement(code,name,j,m,p,1);
             vDao.save(vo);
        }
    }
    
    @Transactional
    private void setPRFrag(String code ,int pos,String j,String m) {
    	PRFrag vo = prfDao.findByCode(code);
        if  (vo == null) {
        	 vo = new PRFrag(code,pos,m,j);
        	 prfDao.save(vo);
        }
    }
    
    @Transactional
    private OPaie setOPaie(String name,String code) {
        OPaie role = opDao.findByCode(code);
        if (role == null) {
            role = new OPaie(name, code);
            role = opDao.save(role);
        }
        return role;
    }
    
    @Transactional
    private void createFragIfNotFound(String name) {
        Fragment obj = fDao.findByName(name);
        if (obj == null) {
            obj = new Fragment(name);
            fDao.save(obj);
        }
    }
    @Transactional
    private void createFragIfNotFoundRP(String name) {
        Fragment obj = fDao.findByName(name);
        if (obj == null) {
            obj = new Fragment(name,true);
            fDao.save(obj);
        }
    }
    
    @Transactional 
    public void setSalle() {
    	for(int i=1 ; i <= 14; i++) {
    	   Salle s = new Salle ();
    	         s.setCode("S"+i);
    	         s.setName("Classe "+i);
    	         s.setType_salle("Salle");
    	         salle.save(s);
    	}
    }
 
    @Transactional 
    public void setProgramme() {
    	int i = 0;
    	List<Option> os = optionDao.findAll();
    	for (Option op: os) {
    		for (Niveau niv: op.getNiveau()) {
        		Programme p = new Programme();
        		          p.setCode(" P-"+niv.getCode()+"-A");
        		          p.setName(niv.getName()+"-A");
        		          p.setNiveau(niv.getCode());
        		          p.setPos(i);
        		          p.setMax_cours(30);
        		          p.setMax_four(30);
        		          i++;
        		          progDao.save(p);
        	}
    	}
    }
}
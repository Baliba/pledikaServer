package com.hist.BysApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class AppService {
	
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
    
    public Long  countStudByFrag(Long id){
    	return fpDao.countStudent(id);
    }
    
}

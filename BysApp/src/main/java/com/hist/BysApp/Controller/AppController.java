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
import com.hist.BysApp.Request.ParcoursRequest;
import com.hist.BysApp.Request.PayReq;
import com.hist.BysApp.Request.RegisterRequest;
import com.hist.BysApp.Response.AppResponse;
import com.hist.BysApp.Response.ErrorResponse;
import com.hist.BysApp.Response.JwtResponse;
import com.hist.BysApp.Response.RPayment;
import com.hist.BysApp.Response.RStat;
import com.hist.BysApp.Response.ResultRespose;
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
import com.hist.BysApp.entities.promo.Fourniture;
import com.hist.BysApp.entities.promo.Frag_cours;
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
import com.hist.BysApp.model.VilleAndDoc;
import com.hist.BysApp.model.VilleAndNiveau;
import com.hist.BysApp.projection.CourseView;
import com.hist.BysApp.service.FileStorageService;
import com.hist.BysApp.service.JwtUserDetailsService;

import models.MUser;

@Controller
public class AppController {
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
	Promo_afRepository pafDao;

	@Autowired
	DomRepo dRep;

	@Autowired
	UserRepository user;

	@Autowired
	VilleRepository vDoa;

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
	FouDao fDao;
	@Autowired
	PFouDao pfouDao;
	
	public void setImg(Long id, String name) {
		Optional<UserEntity> user = u.findById(id);
		user.get().setAvatar(name);
		UserEntity u = UserDetails.getUserInfo(user.get().getUsername());
		this.u.save(u);
	}
	public void setImgEtab(String code, String name) {
		Etablissement etab = etabDao.findByCode(code);
		etab.setLogo(name);
		etabDao.save(etab);
	}

	@RequestMapping(value = "/app/**", method = RequestMethod.GET)
	public String getAnything() {
		return null;
	}

	@RequestMapping(value = "/api/getOptionPaiement", method = RequestMethod.GET)
	public ResponseEntity<?> getCop(@RequestParam String code) {
		List<CycleOPaie> c = cop.getCops(code);
		return ResponseEntity.ok(new AppResponse<List<CycleOPaie>>(false, "success", c));
	}

	List<String> files = new ArrayList<String>();
	private final Path rootLocation = Paths.get("/assets");

	@RequestMapping(value = "/api/etudiantsQuery", method = RequestMethod.GET)
	public ResponseEntity<Page<UserEntity>> getAllStudentsQuery(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "") String query) {
		    Page<UserEntity> pagedResult = UserDetails.getAllEtudiants(page, size, sort, query);
		return new ResponseEntity<Page<UserEntity>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/profsQuery", method = RequestMethod.GET)
	public ResponseEntity<Page<UserEntity>> getAllProfsQuery(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "") String query) {
		Page<UserEntity> pagedResult = UserDetails.getAllProfs(page, size, sort, query);
		return new ResponseEntity<Page<UserEntity>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}

	public String now() {
		Date date = new Date();
		String strDateFormat = "hh:mm:ss a";
		DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		String formattedDate = dateFormat.format(date);
		return formattedDate.trim();
	}

	@Autowired
	private FileStorageService fileStorageService;

	@RequestMapping(value = "/api/uploadStudentImg", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id)
			throws IOException {

		String fileName = fileStorageService.storeFile(file);
		String message = fileName;
		if (fileName != "") {
			this.setImg(id, fileName);
			return ResponseEntity.ok(new JwtResponse<String>(false, message, message));
		}
		return ResponseEntity.ok(new JwtResponse<String>(true, message, message));
	}
	
	@RequestMapping(value = "/api/uploadEtabLogo", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("code") String id)
			throws IOException {

		String fileName = fileStorageService.storeFile(file);
		String message = fileName;
		if (fileName != "") {
			this.setImgEtab(id, fileName);
			return ResponseEntity.ok(new JwtResponse<String>(false, message, message));
		}
		return ResponseEntity.ok(new JwtResponse<String>(true, message, message));
	}

	private String UPLOADED_FOLDER = "assets/imgs";

	private void saveUploadedFile(MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			String p = UPLOADED_FOLDER + file.getOriginalFilename();
			Path path = Paths.get(p);
			Files.write(path, bytes);
		}
	}

	@RequestMapping(value = "/api/etudiants", method = RequestMethod.GET)
	public ResponseEntity<Page<UserEntity>> getAllStudents(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sort) {
		Page<UserEntity> pagedResult = UserDetails.getAllEtudiants(page, size, sort);
		return new ResponseEntity<Page<UserEntity>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/profs", method = RequestMethod.GET)
	public ResponseEntity<Page<UserEntity>> getAllProfs(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "id") String sort) {
		Page<UserEntity> pagedResult = UserDetails.getAllProfs(page, size, sort);
		return new ResponseEntity<Page<UserEntity>>(pagedResult, new HttpHeaders(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/test", method = RequestMethod.GET)
	public ResponseEntity<?> login() throws Exception {

		return ResponseEntity.ok(user.findAll());
	}

	@RequestMapping(value = "/api/context", method = RequestMethod.GET)
	public ResponseEntity<?> context() throws Exception {
		List<Document> nivs = rDoc.findAll();
		List<Ville> vis = vDoa.findAll();
		return ResponseEntity.ok(new AppResponse<VilleAndDoc>(false, "success", new VilleAndDoc(nivs, vis)));
	}

	@RequestMapping(value = "/api/doms", method = RequestMethod.GET)
	public ResponseEntity<?> dom() throws Exception {
		List<Domaine> d = dRep.findAll();

		return ResponseEntity.ok(new AppResponse<List<Domaine>>(false, "success", d));
	}

	@RequestMapping(value = "/api/getProfForCours", method = RequestMethod.GET)
	public ResponseEntity<?> getProfForCours() {
		List<UserEntity> d = user.getProfForCours();
		return ResponseEntity.ok(new AppResponse<List<UserEntity>>(false, "success", d));
	}

	@RequestMapping(value = "/api/getCoursForPromo", method = RequestMethod.GET)
	public ResponseEntity<?> getCoursForPromo(@RequestParam() Long id) {
		List<Course> c = cDao.getCours(id);
		return ResponseEntity.ok(new AppResponse<List<Course>>(false, "success", c));
	}

	@RequestMapping(value = "/api/delStudentToPromo/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> delStudentToPromo(Authentication auth, @PathVariable("id") Long id) {

		UserEntity utt = getUser(auth);
		if (utt.getRole().getName().equals(RoleName.ADMIN) || utt.getRole().getName().equals(RoleName.MASTER)
				|| utt.getRole().getName().equals(RoleName.MANAGER)) {

			Optional<Parcours> par = pars.findById(id);
			if (par.isPresent()) {
				Parcours np = par.get();
				UserEntity s = np.getUser();
				Promotion pro = np.getPromotion();
				if (s != null) {
					Niveau niv;
					s.setNext_class(pro.getNiveau_rel().getNiveau());
					String ncode = pro.getNiveau_rel().getNiveau().getPrev();
					if (ncode!=null && !ncode.equals(" ")) {
						niv = nivRep.findByCode(ncode);
						if(niv!=null) {
						s.setCurrent_class(niv);
						ncode = niv.getPrev();
						if (ncode!=null && !ncode.equals(" ") ) {
							niv = nivRep.findByCode(ncode);
							s.setPrev_class(niv);
						}
					  }
					}
					s.setCurrent_promo(null);
					user.save(s);
					pars.deleteById(np.getId());
					return ResponseEntity.ok(new JwtResponse<String>(false, null, ""));
				}
			}
		} else {
			return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Vous n'etes pas autorisé"));
		}

		return ResponseEntity.ok(new JwtResponse<String>(true, null, ""));
	}

	@Transactional
	@RequestMapping(value = "/api/addStudentToPromo", method = RequestMethod.POST)
	public ResponseEntity<?> addStudentToPromo(@RequestBody ParcoursRequest u) {

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
				// create list versement for parcours
				List<PVersement> pv =  new ArrayList<PVersement>();
				List<PVersement> ppv = new ArrayList<PVersement>();
				Long codep = u.getId_opaie();
				Optional<CycleOPaie> cop = this.cop.findById(codep);
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

             if(pv.size()<=0) { return ResponseEntity.ok(new ErrorResponse("Paiement non configurer pour ce cycle",true)); }
			
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

	@RequestMapping(value = "/api/getStudentToPromo", method = RequestMethod.GET)
	public ResponseEntity<?> getStudentToPromo(@RequestParam() String code) {
		List<UserEntity> users = user.getStudentForPromo(code);
		return ResponseEntity.ok(new AppResponse<List<UserEntity>>(false, "success", users));
	}

	@RequestMapping(value = "/api/addProf", method = RequestMethod.POST)
	public ResponseEntity<?> addProf(@RequestBody RegisterRequest u, Authentication authentication) throws Exception {
		UserDetails me = (UserDetails) authentication.getPrincipal();

		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());
		if (cu.getRole().getName().equals(RoleName.ADMIN) || cu.getRole().getName().equals(RoleName.MANAGER)
				|| cu.getRole().getName().equals(RoleName.MASTER)) {
			String email = u.getUsername();
			Long nu = user.count();

			if (user.existsByUsername(email)) {
				return ResponseEntity.ok(new ErrorResponse("Email existe déjà ", true));
			}

			String password = "prof@12345";
			String encodedPassword = new BCryptPasswordEncoder().encode(password);
			String fname = u.getFname(); // 1
			String lname = u.getLname(); // 2
			UserEntity ut = new UserEntity(email, encodedPassword, fname, lname);
			ut.setPhone(u.getPhone());

			Role r;
			r = role.findByName(RoleName.PROF);
			ut.setRole(r);
			ut.setCode("P-" + Helper.generateCode(fname, lname, nu));

			ut.setSexe(u.getSexe());
			ut.setDate_de_naiss(u.getDate_de_naiss());
			ut.setCreated_by(cu.getId());
			ut.setAvatar("default.png");
			ut = user.save(ut);
			return ResponseEntity.ok(new JwtResponse<UserEntity>(false, ut, ""));
		} else {
			return ResponseEntity
					.ok(new JwtResponse<UserEntity>(true, null, "vous n'etes pas autorisé a ajouter de enseignants"));
		}
	}

	@RequestMapping(value = "/api/addPersonnel", method = RequestMethod.POST)
	public ResponseEntity<?> addPersonnel(@RequestBody RegisterRequest u, Authentication authentication)
			throws Exception {
		UserDetails me = (UserDetails) authentication.getPrincipal();

		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());

		if (cu.getRole().getName().equals(RoleName.ADMIN) || cu.getRole().getName().equals(RoleName.MANAGER)
				|| cu.getRole().getName().equals(RoleName.MASTER)) {

			String email = u.getUsername();
			Long nu = user.count();

			if (user.existsByUsername(email)) {
				return ResponseEntity.ok(new ErrorResponse("Email existe déjà ", true));
			}

			String password = "admin@12345";
			String encodedPassword = new BCryptPasswordEncoder().encode(password);
			String fname = u.getFname(); // 1
			String lname = u.getLname(); // 2
			UserEntity ut = new UserEntity(email, encodedPassword, fname, lname);
			ut.setPhone(u.getPhone());
			ut.setAdresse(u.getAdresse());
			Role r;
			r = role.findById(u.getRole()).get();
			ut.setRole(r);
			ut.setCode("A-" + Helper.generateCode(fname, lname, nu));
            ut.setSalairy(u.getSalairy());
            ut.setFonction(u.getFonction());
			ut.setSexe(u.getSexe());
			ut.setDate_de_naiss(u.getDate_de_naiss());
			ut.setCreated_by(cu.getId());
			ut.setAvatar("default.png");
			ut.setEnabled(u.isEnabled());
			ut = user.save(ut);
			return ResponseEntity.ok(new JwtResponse<UserEntity>(false, ut, "Personnel ajouté avec succès"));
		} else {

			return ResponseEntity
					.ok(new JwtResponse<UserEntity>(true, null, "Vous n'etes pas autorisé a ajouter un enseignants"));
		}

	}

	@RequestMapping(value = "/api/addStudent", method = RequestMethod.POST)
	public ResponseEntity<?> addStudent(@RequestBody RegisterRequest u, Authentication authentication)
			throws Exception {
		UserDetails me = (UserDetails) authentication.getPrincipal();

		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());

		if (cu.getRole().getName().equals(RoleName.ADMIN) || cu.getRole().getName().equals(RoleName.MANAGER)
				|| cu.getRole().getName().equals(RoleName.MASTER)) {

			String email = u.getUsername();
			Long nu = user.count();

			if (user.existsByUsername(email)) {
				return ResponseEntity.ok(new ErrorResponse("Email existe déjà ", true));
			}

			Ville v = vDoa.getOne(u.getId_ville_ln());
			if (v == null) {
				return ResponseEntity.ok(new ErrorResponse("Lieu de naissance invalide", true));
			}
			Niveau niv = nivRep.findByCode(u.getCode_class());
			if (niv == null) {
				return ResponseEntity.ok(new ErrorResponse("Classe admise invalide ", true));
			}

			String password = "student@12345";
			String encodedPassword = new BCryptPasswordEncoder().encode(password);
			String fname = u.getFname(); // 1
			String lname = u.getLname(); // 2
			UserEntity ut = new UserEntity(email, encodedPassword, fname, lname);
			// ---------------
			ut.setCurrent_class(niv); // 3
			ut.setNext_class(niv);
			// ---------------
			ut.setNom_ass(u.getNom_ass());
			ut.setPhone_ass(u.getPhone_ass());
			ut.setAdresse_ass(u.getAdresse_ass());
			// ------------------
			Role r;
			r = role.findByName(RoleName.STUDENT);
			ut.setRole(r);
			ut.setLieu_de_naiss(v);
			ut.setCode(Helper.generateCode(fname, lname, nu));
			//
			ut.setLast_etab(u.getLast_etab());
			ut.setLast_year(u.getLast_year());
			ut.setLast_moyen(u.getLast_moyen());
			//
			ut.setReference(u.getReference());
			ut.setArefaire(u.isArefaire());
			ut.setSexe(u.getSexe());
			ut.setDate_de_naiss(u.getDate_de_naiss());
			ut.setCreated_by(cu.getId());
			ut.setAvatar("default.png");
			// PAIEMENT
			PaiementAdmission ap = new PaiementAdmission();
			ap.setNiveau(niv);
			ap.setMontant(niv.getOption().getMontant_admis());
			ap.setCode("SD-" + ut.getCode());
			ap.setCreated_by(cu.getId());
			ap.setPay(true);
			Date d = new Date();
			ap.setDate_paiement(d);
			ap.setPay_date(d);
			ap = adp.save(ap);
			ap.setPay_by(cu.getId());
			// END PAIEMENT
			if (ap != null) {
				ut.setPaiement_admission(ap);
				ut = user.save(ut);
				return ResponseEntity.ok(new JwtResponse<UserEntity>(false, ut, "Admission Completé"));
			}
			return ResponseEntity.ok(new ErrorResponse("Erreur du systeme ", true));
		} else {
			return ResponseEntity
					.ok(new JwtResponse<UserEntity>(true, null, "Vous n'etes pas autorisé a ajouter un enseignants"));
		}
	}

	@RequestMapping(value = "/api/admission", method = RequestMethod.POST)
	public ResponseEntity<?> admis(@RequestBody RegisterRequest u, Authentication authentication) throws Exception {
		UserDetails me = (UserDetails) authentication.getPrincipal();

		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());

		String email = u.getUsername();
		Long nu = user.count();

		if (user.existsByUsername(email)) {
			return ResponseEntity.ok(new ErrorResponse("Email existe déjà ", true));
		}

		Ville v = vDoa.getOne(u.getId_ville_ln());
		if (v == null) {
			return ResponseEntity.ok(new ErrorResponse("Lieu de naissance invalide", true));
		}
		Niveau niv = nivRep.findByCode(u.getCode_class());
		if (niv == null) {
			return ResponseEntity.ok(new ErrorResponse("Classe admise invalide ", true));
		}

		String password = "student@12345";
		String encodedPassword = new BCryptPasswordEncoder().encode(password);
		String fname = u.getFname(); // 1
		String lname = u.getLname(); // 2
		UserEntity ut = new UserEntity(email, encodedPassword, fname, lname);
		// ---------------
		ut.setCurrent_class(niv); // 3
		ut.setNext_class(niv);
		// ---------------
		ut.setNom_ass(u.getNom_ass());
		ut.setPhone_ass(u.getPhone_ass());
		ut.setAdresse_ass(u.getAdresse_ass());
		// ------------------
		Role r;
		r = role.findByName(RoleName.STUDENT);
		ut.setRole(r);
		ut.setLieu_de_naiss(v);
		ut.setCode(Helper.generateCode(fname, lname, nu));
		//
		ut.setLast_etab(u.getLast_etab());
		ut.setLast_year(u.getLast_year());
		ut.setLast_moyen(u.getLast_moyen());
		//
		ut.setReference(u.getReference());
		ut.setArefaire(u.isArefaire());
		ut.setSexe(u.getSexe());
		ut.setDate_de_naiss(u.getDate_de_naiss());
		ut.setCreated_by(cu.getId());
		ut.setAvatar("default.png");
		// PAIEMENT
		PaiementAdmission ap = new PaiementAdmission();
		ap.setNiveau(niv);
		ap.setMontant(niv.getOption().getMontant_admis());
		ap.setCode("SD-" + ut.getCode());
		ap.setCreated_by(cu.getId());
		ap = adp.save(ap);
		// END PAIEMENT
		if (ap != null) {
			ut.setPaiement_admission(ap);
			ut = user.save(ut);
			return ResponseEntity.ok(new JwtResponse<UserEntity>(false, ut, "Admission Completé"));
		}

		return ResponseEntity.ok(new ErrorResponse("Erreur du systeme ", true));
	}

	@RequestMapping(value = "/api/getUserById/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(user.findById(id));
	}

	@RequestMapping(value = "/api/getUserByCode/{code}")
	public ResponseEntity<?> getUserById(@PathVariable("code") String code) {
		return ResponseEntity.ok(user.findByCode(code));
	}

	@RequestMapping(value = "/api/payByUserById/{id}")
	public ResponseEntity<?> payByUserId(@PathVariable("id") Long id, Authentication authentication) {
		Optional<UserEntity> ut = user.findById(id);
		UserDetails me = (UserDetails) authentication.getPrincipal();
		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());
		if (cu.getRole().getName().equals(RoleName.ACCOUNTING) || cu.getRole().getName().equals(RoleName.MASTER)) {
			if (ut.isPresent()) {
				ut.get().getPaiement_admission().setPay(true);
				Date d = new Date();
				ut.get().getPaiement_admission().setDate_paiement(d);
				ut.get().getPaiement_admission().setPay_date(d);

				ut.get().getPaiement_admission().setPay_by(cu.getId());
				ut.get().setEnabled(true);
				UserEntity u = user.save(ut.get());
				double h = cu.getHsold();
				double s = cu.getSold();
				double a = ut.get().getPaiement_admission().getMontant();
				cu.setHsold(s);
				s = s + a;
				cu.setSold(s);
				user.save(cu);
				return ResponseEntity.ok(new JwtResponse<UserEntity>(false, u, "Paiement effectué"));
			}

			return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Paiement non effectué"));
		}

		return ResponseEntity
				.ok(new JwtResponse<UserEntity>(true, null, "Vous n'etes pa autorisé" + cu.getRole().getName()));

	}

	@Autowired
	HCoursDao hcDao;

	@RequestMapping(value = "/api/addCoursToPromoFrag/{id}/{idp}")
	public ResponseEntity<?> addCoursToPromoFrag(@PathVariable("id") Long id, @PathVariable("idp") Long idp) {
		List<Promo_cours> pcs = pcDao.getCoursByPromo(idp);
		List<Frag_cours> fcs = new ArrayList<Frag_cours>();
		if (pcs.size() > 0) {
			PromoFrag pf = pfDao.findById(id).get();
			for (Promo_cours pc : pcs) {
				Frag_cours fc = new Frag_cours();
				String code = pc.getCourse().getCode() + "-" + pf.getId();
				Frag_cours fct = fcDao.findByCode(code);
				if (fct == null) {
					fc.setId_promo(idp);
					fc.setId_cours(pc.getCourse().getId());
					fc.setPromofrag(pf);
					fc.setCode(code);
					fc.setCourse(pc.getCourse().getCode());
					fc.setName(pc.getName());
					fc.setCoef(pc.getCoef());
					fc.setNote_total(pc.getNote_total());
					fc.setExamen(true);
					fc.setNote_total(pc.getNote_total());
					fc.setNote_pass(pc.getNote_pass());
					fc.setNote_excel(pc.getNote_excel());
					fc = fcDao.save(fc);
					HCours hc = new HCours();
					hc.setJours(pc.getJours());
					hc.setHeure_cours(pc.getHeure_cours());
					hc.setFrag_cours(fc);
					hc.setCode(pc.getJours() + "-" + pc.getHeure_cours() + "-" + pf.getId() + "-" + fc.getId());
					hc = hcDao.save(hc);
					fc.setAHcours(hc);
					fcs.add(fc);
				}
			}
		}

		return ResponseEntity.ok(new JwtResponse<List<Frag_cours>>(true, fcs, " Taille: " + pcs.size()));

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

	@RequestMapping(value = "/api/getPars_cours/{idf}")
	public ResponseEntity<?> getFS(@PathVariable("idf") Long idf) {
		List<Parcours_frag> ps = fpDao.getRFParcours(idf);
		return ResponseEntity.ok(new JwtResponse<List<Parcours_frag>>(true, ps, "Parcours fragment"));
	}

	@RequestMapping(value = "/api/getPromo/{idp}")
	public ResponseEntity<?> getPromo(@PathVariable("idp") Long idp) {
		Promotion pfs = promo.findById(idp).get();
		return ResponseEntity.ok(new JwtResponse<Promotion>(true, pfs, "Promotion"));
	}

	@RequestMapping(value = "/api/initResult/{idf}/{fc}")
	public ResponseEntity<?> getPromo(@PathVariable("idf") Long id, @PathVariable("fc") Long fci) {
		Frag_cours fc = fcDao.findById(fci).get();

		List<Parcours_frag> ps = fpDao.getRFParcours(id);
		List<Results> rs = new ArrayList<Results>();

		for (Parcours_frag p : ps) {
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
				r.setNote_pass( fc.getNote_pass());
				r.setNote_excel(fc.getNote_excel());
				r.setCode_student(p.getCode_student());
				r.setCoef(fc.getCoef());
				r.setNote(0f);
				rDao.save(r);
				rs.add(r);
			}
		}
		List<Results> rr = rDao.getEtudiants(fci);
		return ResponseEntity.ok(new JwtResponse<List<Results>>(true, rr, "Les resultats"));
	}

	@RequestMapping(value = "/api/getResult/{fc}")
	public ResponseEntity<?> getRes(@PathVariable("fc") Long fci) {
		List<Results> rr = rDao.getEtudiants(fci);
		return ResponseEntity.ok(new JwtResponse<List<Results>>(true, rr, "Les resultats"));
	}

	@RequestMapping(value = "/api/getBulletinFrag/{fc}")
	public ResponseEntity<?> getBulletinFrag(@PathVariable("fc") Long id) {
		List<Results> rr = rDao.getBulletin(id);
		return ResponseEntity.ok(new JwtResponse<List<Results>>(true, rr, "Les resultats"));
	}

	@RequestMapping(value = "/api/getMoyGenFrag/{fc}")
	public ResponseEntity<?> getMoyGen(@PathVariable("fc") Long id) {
		float total = rDao.getNoteTotalGen(id);
		float note = rDao.getNoteGen(id);
		float moy = 1;
		if (total != 0 && note != 0) {
			moy = note / total;
		}
		return ResponseEntity.ok(moy);
	}

	@Autowired
	ProgDao pgDao;

	@RequestMapping(value = "/api/addCoursToProg/{idp}/{idc}")
	public ResponseEntity<?> addCoursToProg(@PathVariable("idp") Long idp, @PathVariable("idc") Long idc) {
		Programme p = pgDao.findById(idp).get();
		Course c = cDao.findById(idc).get();
		c.getProgramme().add(p);
		p.getCourse().add(c);
		pgDao.save(p);
		return ResponseEntity.ok(c);
	}
	

	@RequestMapping(value = "/api/getPCours/{niv}/{idp}")
	public ResponseEntity<?> getCoursProg(@PathVariable("niv") String niv, @PathVariable("idp") Long id) {
		String o = this.nivRep.findById(niv).get().getOption().getCode();
		Programme p = pgDao.findById(id).get();
		java.util.List<Long> ids = new ArrayList<>();
		int s = p.getCourse().size();
		List<Course> c;
		if (s > 0) {
			for (Course cn : p.getCourse()) {
				ids.add(cn.getId());
			}
			c = this.cDao.getPCours(ids, o);
		} else {
			c = this.cDao.getListCours(o);
		}
		return ResponseEntity.ok(new JwtResponse<List<Course>>(true, c, "Les resultats"));
	}

	@RequestMapping(value = "/api/getProgByNiv/{niv}")
	public ResponseEntity<?> getCoursProg(@PathVariable("niv") String niv) {
		List<Programme> p = pgDao.getProg(niv);
		return ResponseEntity.ok(new JwtResponse<List<Programme>>(true, p, "programmes"));
	}

	@RequestMapping(value = "/api/addCoursToPromoByProg/{idp}/{idg}")
	public ResponseEntity<?> getCoursProg(@PathVariable("idp") Long idp, @PathVariable("idg") Long idg) {
		Programme p = pgDao.findById(idg).get();
		List<Promo_cours> pcs = new ArrayList<Promo_cours>();
		if (p.getCourse().size() > 0) {
			Promotion pf = promo.findById(idp).get();
			for (Course pc : p.getCourse()) {
				Promo_cours fc = new Promo_cours();
				String code = pc.getCode() + "-" + pf.getId();
				Promo_cours fct = pcDao.findByCode(code);
				if (fct == null) {
					fc.setPromotion(pf);
					fc.setCode(code);
					fc.setCourse(pc);
					fc.setName(pc.getName());
					fc.setCoef(pc.getCoef());
					fc.setNote_total(pc.getNote_total());
					fc.setNote_pass(pc.getNote_pass());
					fc.setNote_excel(pc.getNote_excel());
					fc.setNote_rep(pc.getNote_rep());
					fc = pcDao.save(fc);
					pcs.add(fc);
					
				}
			}
		}
		return ResponseEntity.ok(new JwtResponse<List<Promo_cours>>(true, pcs, "promo_cours"));
	}

	@RequestMapping(value = "/api/getParcours/{id}")
	public ResponseEntity<?> getPc(@PathVariable("id") Long id) {
		List<Parcours> p = pDao.getParcours(id);
		return ResponseEntity.ok(new JwtResponse<List<Parcours>>(true, p, "parcours"));
	}
	
	

	@RequestMapping(value = "/api/getStudentForPromoV2/{id}")
	public ResponseEntity<?> getStudentForPromoV2(@PathVariable("id") Long id) {
		List<Parcours> p = pDao.getParcours(id);
		java.util.List<String> ids = new ArrayList<>();
		for (Parcours cn : p) {
			ids.add(cn.getCode_student());
		}
		List<UserEntity> u;
		if (ids.size() > 0) {
			u = user.getStudentForPromoV2(ids);
		} else {
			u = user.getStudentForPromoV2();
		}
		return ResponseEntity.ok(new JwtResponse<List<UserEntity>>(true, u, "Student"));
	}
	
	@RequestMapping(value = "/api/getStudentForPromoV3/{id}")
	public ResponseEntity<?> getStudentForPromoV3(@PathVariable("id") Long id) {
		Promotion prom = promo.findById(id).get();
		List<Parcours> p = pDao.getParcours(id);
		java.util.List<String> ids = new ArrayList<>();
		for (Parcours cn : p) {
			ids.add(cn.getCode_student());
		}
		List<UserEntity> u;
		if (ids.size() > 0) {
			u = user.getStudentForPromoV3(ids, prom.getCode_niveau());
		} else {
			u = user.getStudentForPromoV3(prom.getCode_niveau());
		}
		return ResponseEntity.ok(new JwtResponse<List<UserEntity>>(true, u, "Student"));
	}
	
	@RequestMapping(value = "/api/getStudentForPromoV3")
	public ResponseEntity<?> getStudentForPromoV() {
		List<UserEntity> u = user.getStudentForPromoV2();
		return ResponseEntity.ok(new JwtResponse<List<UserEntity>>(true, u, "Student"));
	}

	@RequestMapping(value = "/api/getPayment")
	public ResponseEntity<?> getPayment(Authentication authentication) {
		UserDetails me = (UserDetails) authentication.getPrincipal();
		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());
		Date d = new Date();
		List<PaiementAdmission> p = adp.getMyPayment(cu.getId(), d);
		return ResponseEntity.ok(new JwtResponse<List<PaiementAdmission>>(true, p, "PAYMENT"));
	}

	@Transactional
	@RequestMapping(value = "/api/closeParcours/{id}/{state}", method = RequestMethod.GET)
	public ResponseEntity<?> close(@PathVariable("id") Long id, @PathVariable("state") boolean state) {
		pars.closeByPromo(id, state);
		return ResponseEntity.ok(new JwtResponse<Boolean>(true, true, "PAYMENT"));
	}

	@RequestMapping(value = "/api/getAllParcours/{state}/{code}")
	public ResponseEntity<?> getAllPc(@PathVariable("code") String code, @PathVariable("state") boolean state) {
		List<Parcours> p = pDao.getAllParcours(state, code);
		return ResponseEntity.ok(new JwtResponse<List<Parcours>>(true, p, "parcours"));
	}

	@RequestMapping(value = "/api/getOneParcours/{state}")
	public ResponseEntity<?> getAllPc(@PathVariable("state") Long state) {
		Parcours p = pDao.getOneParcours(state);
		return ResponseEntity.ok(new JwtResponse<Parcours>(true, p, "parcours"));
	}

	@Transactional
	public void closeOne(Long id, String code, boolean state) {
		pars.closeOne(id, code, state);
	}

	@Autowired
	PayDao payD;

	@RequestMapping(value = "/api/payment/{id}/{sold}/{idv}")
	public ResponseEntity<?> payment(@PathVariable("idv") Long idv, @PathVariable("id") Long id,
		@PathVariable("sold") int montant, Authentication authentication) {
		UserDetails me = (UserDetails) authentication.getPrincipal();
		UserEntity cu = this.UserDetails.getUserInfo(me.getUsername());
		double verse = 0;
		double sold = montant;
		Parcours pars = pDao.getOneParcours(id);
		// System.out.print("##############="+pars.getCode_student());
		if (cu.getRole().getName().equals(RoleName.ACCOUNTING) || cu.getRole().getName().equals(RoleName.MASTER)) {
			if (pars != null) {
				if (idv > 0 && idv!=null) {
					// choose the specific payment
					PVersement p = pvd.getVerse(idv);
					if(p.isActived()) {
					if (p.getMontant_pay() == null) {
						p.setMontant_pay((double) 0);
					} if (p.getMontant_to_pay() > p.getMontant_pay()) {
						if ((p.getMontant_to_pay() - p.getMontant_pay()) > sold) {
							p.setMontant_pay(p.getMontant_pay() + sold);
							verse += sold;
							sold = 0;
						} else if ((p.getMontant_to_pay() - p.getMontant_pay()) < sold) {
							double rest = p.getMontant_to_pay() - p.getMontant_pay();
							p.setMontant_pay(p.getMontant_to_pay());
							verse += rest;
							sold -= rest;
						} else if ((p.getMontant_to_pay() - p.getMontant_pay()) == sold) {
							p.setMontant_pay(p.getMontant_to_pay());
							verse += sold;
							sold = 0;
						}
						pvd.save(p);
					} else {
						return ResponseEntity.ok(new JwtResponse<RPayment>(true, null, " Versement non effectué"));
					}
				} else {
					return ResponseEntity.ok(new JwtResponse<RPayment>(true, null, " Versement deactivé"));
				}
				} else {

					// if idv == 0 payment automatic
					List<PVersement> pvs = pvd.getVerseForParcours(id);
				
					if (pvs.size() > 0) {
						for (PVersement p : pvs) {
							if(p.getType_verse()!=1 || !p.isActived() ) {
								continue;
							}
							if (sold > 0) {
								if (p.getMontant_pay() == null) {
									p.setMontant_pay((double) 0);
								}
								if (p.getMontant_to_pay() > p.getMontant_pay()) {
									if ((p.getMontant_to_pay() - p.getMontant_pay()) > sold) {
										p.setMontant_pay(p.getMontant_pay() + sold);
										verse += sold;
										sold = 0;
									} else if ((p.getMontant_to_pay() - p.getMontant_pay()) < sold) {
										double rest = p.getMontant_to_pay() - p.getMontant_pay();
										p.setMontant_pay(p.getMontant_to_pay());
										verse += rest;
										sold -= rest;
									} else if ((p.getMontant_to_pay() - p.getMontant_pay()) == sold) {
										p.setMontant_pay(p.getMontant_to_pay());
										verse += sold;
										sold = 0;
									}
									pvd.save(p);
								} else {
									continue;
								}
							} else {
								break;
							}
						}
					} else {
						return ResponseEntity.ok(new JwtResponse<RPayment>(true, null, "Aucun versement disponible"));
					}
				}

				Payment pay = new Payment();
				pay.setPay_money((double) montant);
				pay.setCode_etudiant(pars.getCode_student());
				pay.setPromotion(pars.getPromo_name());
				pay.setMontant(verse);
				pay.setRemain(sold);
				pay.setPay_by(cu.getId());
				pay.setId_parcours(id);
				pay = payD.save(pay);
				// -----------------------
				RPayment rp = new RPayment();
				rp.setId_payment(pay.getId());
				rp.setRemain(sold);
				rp.setPv(pvd.getVerseForParcours(id));

				double h = cu.getHsold();
				double s = cu.getSold();
				double a = verse;
				cu.setHsold(s);
				s = s + a;
				cu.setSold(s);
				user.save(cu);
				return ResponseEntity.ok(new JwtResponse<RPayment>(false, rp, "succès"));
			}

			return ResponseEntity.ok(new JwtResponse<RPayment>(true, null, "Etudiant non trouvé"));
		}
		return ResponseEntity
				.ok(new JwtResponse<RPayment>(true, null, "Vous n'avez pas le doit de faire des paiments"));
	}

	@RequestMapping(value = "/api/logout")
	public ResponseEntity<?> getAllPc(Authentication auth) {

		return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Deconnection"));
	}

	@RequestMapping(value = "/api/getPersonnel")
	public ResponseEntity<?> getPersonnel() {
		List<UserEntity> users = user.getUsers();
		return ResponseEntity.ok(new JwtResponse<List<UserEntity>>(true, users, "Liste des personnels"));
	}
	
	@RequestMapping(value = "/api/getUserV2/{role}")
	public ResponseEntity<?> getUserV2(@PathVariable("role") String role) {
		List<MUser>  users = user.getPeopleV2(role);
		return ResponseEntity.ok(new JwtResponse<List<MUser>>(true, users, "Liste des "+role+"s"));
	}
	
	@RequestMapping(value = "/api/getUserV3/{role}/{code}")
	public ResponseEntity<?> getUserV2(@PathVariable("role") String role,@PathVariable("code") String code) {
		List<MUser> users = user.getPeopleV3(role,code);
		return ResponseEntity.ok(new JwtResponse<List<MUser>>(true, users, "Liste des "+role+"s"));
	}
	
	@RequestMapping(value = "/api/getUserV4/{role}/{code}")
	public ResponseEntity<?> getUserV4(@PathVariable("role") String role,@PathVariable("code") String code) {
		List<MUser> users = user.getPeopleV4(role, code);
		return ResponseEntity.ok(new JwtResponse<List<MUser>>(true, users, "Liste des "+role+"s"));
	}

	@RequestMapping(value = "/api/getPayment/{state}")
	public ResponseEntity<?> getPaiement(@PathVariable("state") Long state) {
		Payment p = payD.getPayment(state);
		return ResponseEntity.ok(new JwtResponse<Payment>(true, p, "payment " + state));
	}

	@RequestMapping(value = "/api/getParcoursByCodeStudent/{code}")
	public ResponseEntity<?> getAllPc(@PathVariable("code") String code) {
		List<Parcours> p = pDao.getParcoursByCodeStudent(code);
		return ResponseEntity.ok(new JwtResponse<List<Parcours>>(true, p, "parcours"));
	}

	@RequestMapping(value = "/api/getPaymentForUser/{page}")
	public ResponseEntity<?> getPayForUser(@PathVariable("page") int pageNo, Authentication authentication) {
		UserDetails me = (UserDetails) authentication.getPrincipal();
		UserEntity ut = this.UserDetails.getUserInfo(me.getUsername());
		if (ut.getRole().getName().equals(RoleName.ACCOUNTING) || ut.getRole().getName().equals(RoleName.MASTER)) {
			Pageable paging = PageRequest.of(pageNo, 100, Sort.by("id"));
			Page<Payment> pays = payD.getPaymentForUser(paging, ut.getId());
			return ResponseEntity.ok(new JwtResponse<Page<Payment>>(false, pays, "Les paiements effectués"));
		}
		return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Vous n'etes pa autorisé"));

	}

	@RequestMapping(value = "/api/getCoursForProf/{id}")
	public ResponseEntity<?> getCoursFormProf(@PathVariable("id") Long id, Authentication auth) {
		UserEntity ut = getUser(auth);
		if (ut.getRole().getName().equals(RoleName.PROF) || ut.getRole().getName().equals(RoleName.MASTER)) {
			List<Frag_cours> c = fcDao.getCoursByProf(id);
			return ResponseEntity.ok(new JwtResponse<List<Frag_cours>>(false, c, ""));
		}
		return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, ""));

	}

	public UserEntity getUser(Authentication authentication) {
		UserDetails me = (UserDetails) authentication.getPrincipal();
		return this.UserDetails.getUserInfo(me.getUsername());
	}

	@RequestMapping(value = "/api/getPromoForProf")
	public ResponseEntity<?> getPromoFormProf(Authentication auth) {
		UserEntity ut = getUser(auth);
		if (ut.getRole().getName().equals(RoleName.PROF) || ut.getRole().getName().equals(RoleName.MASTER)) {
			List<Promotion> c = promo.getPromoForProf(ut.getId());
			return ResponseEntity.ok(new JwtResponse<List<Promotion>>(false, c, ""));
		}
		return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, ""));

	}

	@RequestMapping(value = "/api/getProfByRole")
	public ResponseEntity<?> getRole(Authentication auth) {
		UserEntity ut = getUser(auth);
		if (ut.getRole().getName().equals(RoleName.PROF) || ut.getRole().getName().equals(RoleName.MASTER)) {
			Role c = role.getProfRole(RoleName.PROF);
			return ResponseEntity.ok(new JwtResponse<Role>(false, c, ""));
		}
		return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, ""));

	}

	@Transactional
	@RequestMapping(value = "/api/changePass", method = RequestMethod.POST)
	public ResponseEntity<?> changePass(@RequestBody CPassRequest user, Authentication auth) {
		UserEntity ut = getUser(auth);
		String password = user.getPass_0();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (encoder.matches(password, ut.getPassword())) {
			password = user.getPass_1();
			String nPass = new BCryptPasswordEncoder().encode(password);
			ut.setPassword(nPass);
			ut = u.save(ut);
			return ResponseEntity.ok(new JwtResponse<UserEntity>(false, ut, "Mot de passe modifié"));
		} else {
			http: // localhost:4200/app/profil
			return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Ancien mot de passe incorrect"));
		}
	}

	@Transactional
	@RequestMapping(value = "/api/changePass/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> changePassUser(@RequestBody CPassRequest u, @PathVariable("id") Long id,
			Authentication auth) {
		UserEntity ut = getUser(auth);
		if (ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
			UserEntity mu = user.findById(id).get();
			String password = u.getPass_1();
			String nPass = new BCryptPasswordEncoder().encode(password);
			mu.setPassword(nPass);
			user.save(mu);
			return ResponseEntity.ok(new JwtResponse<UserEntity>(false, null, "Mot de passe modifié"));
		} else {
			return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Mot de passe non  modifié"));
		}

	}

	@Transactional
	@RequestMapping(value = "/api/openAC/{id}")
	public ResponseEntity<?> open(@PathVariable("id") Long id, Authentication auth) {
		UserEntity ut = getUser(auth);
		if (ut.getRole().getName().equals(RoleName.ADMIN) || ut.getRole().getName().equals(RoleName.MASTER)) {
			pafDao.close();
			pafDao.open(id);
			return ResponseEntity.ok(new JwtResponse<UserEntity>(false, null, "Activé"));
		} else {
			return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Non activé"));
		}

	}

	@Transactional
	@RequestMapping(value = "/api/stat")
	public ResponseEntity<?> statistique() {
		RStat stat = new RStat();
		Promo_af af = pafDao.getActived();
		stat.setAf(af);
		stat.setTotal_student(user.getNbreStudent());
		stat.setTotal_prof(user.getNbreProf());
		stat.setTotal_garcon(user.getNbreGarcon());
		stat.setTotal_fille(user.getNbreFille());
		stat.setTotal_pers(user.getNbrePers());
		stat.setTotal_cours(cDao.count());
		return ResponseEntity.ok(new JwtResponse<RStat>(false, stat, "Statistiques"));
	}

	@RequestMapping(value = "/api/getUserForPayroll")
	public ResponseEntity<?> payroll() {
		List<Role> users = role.getUsers();
		return ResponseEntity.ok(new JwtResponse<List<Role>>(false, users, "Payroll"));
	}

	public double setSalaryNet(double s) {
		Promo_af etab = pafDao.getActived();
		s = s * (1 - (etab.getOna() / 100));
		s = s * (1 - (getIRI(etab, s) / 100));
		s = s * (1 - (etab.getFdu() / 100));
		s = s * (1 - (etab.getCas() / 100));
		return s;
	}

	public double getIRI(Promo_af etab, double s) {
		s *= 12;
		double iri = 0;
		if (s >= 1 && s <= 60000) {
			iri = etab.getIri();
		} else if (s >= 60001 && s <= 240000) {
			iri = etab.getIri_1();
		} else if (s >= 240001 && s <= 480000) {
			iri = etab.getIri_2();
		} else if (s >= 480001 && s < 1000000) {
			iri = etab.getIri_3();
		} else if (s >= 100000) {
			iri = etab.getIri_4();
		}
		return iri;
	}

	@Autowired
	PayrollDao payDao;

	@Autowired
	EtabRepo etabDao;

	@Autowired
	PRFragDao prfDao;

	@RequestMapping(value = "/api/payroll")
	public ResponseEntity<?> makePayroll(@RequestBody PayReq pay, Authentication auth) {
		UserEntity ut = getUser(auth);
		UserEntity pu = user.findById(pay.user).get();
		Promo_af year = pafDao.getActived();
		if (ut.getRole().getName().equals(RoleName.ACCOUNTING) || ut.getRole().getName().equals(RoleName.ADMIN)
				|| ut.getRole().getName().equals(RoleName.MASTER)) {
			String code = year.getId() + "-" + pay.getUser() + "-" + pay.getCode();
			Payroll o = payDao.findByCode(code);
			if (o == null) {
				Payroll p = new Payroll();
				p.setCode(code);
				p.setNom(pu.getLastName());
				p.setPnom(pu.getFirstName());
				p.setCode_user(pu.getCode());
				p.setRole(pu.getRole().getName());
				p.setSalaire_brut(pu.getSalairy());
				p.setSalaire_net(this.setSalaryNet(pu.getSalairy()));
				p.setId_acad(year.getId());
				p.setType_payroll(pay.getTpay());
				p.setAdd_by(ut.getId());
				p.setId_pers(pu.getId());
				p.setId_mois(pay.getId_mois());
				p.setMois(pay.getCode());
				p = payDao.save(p);
				return ResponseEntity.ok(new JwtResponse<Payroll>(false, null, "Payroll pour (" + pu.getCode() + ") "
						+ pu.getLastName() + " " + pu.getFirstName() + " validé"));
			} else {
				return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Payroll pour (" + pu.getCode() + ") "
						+ pu.getLastName() + " " + pu.getFirstName() + "  déjà validé"));
			}
		} else {
			return ResponseEntity.ok(new JwtResponse<UserEntity>(true, null, "Vous n'etes pas autorisé"));
		}
	}

}

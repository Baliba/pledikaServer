package com.hist.BysApp.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.hist.BysApp.Request.NotRequest;
import com.hist.BysApp.dao.NotRepo;
import com.hist.BysApp.dao.ParcoursRepository;
import com.hist.BysApp.dao.Promo_coursRepo;
import com.hist.BysApp.dao.UserRepository;
import com.hist.BysApp.entities.config.LNot;
import com.hist.BysApp.entities.config.Notification;
import com.hist.BysApp.entities.enums.TYPE_NOT;
import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.Promo_cours;



@Service
public class NotService {
	@Autowired 
	NotRepo notDao;
	

	@Autowired 
	ParcoursRepository pars;
	
	
	@Autowired
	UserRepository user;
	
	@Autowired
	Promo_coursRepo pcDao;

	public boolean  sendNotTo(NotRequest not, UserEntity utt, LNot ln) {
	List<UserEntity> users = Arrays.asList() ;
	 TYPE_NOT tp = TYPE_NOT.BROADCAST_TO_All;
	  switch(not.getCible()) {
	  case 1 : {
		  users = user.findAllUser();
		  tp =  TYPE_NOT.BROADCAST_TO_All;
		  break;
	  }
      case 2 : {
    	 users = user.findAllTeacher();
    	  tp =  TYPE_NOT.BROADCAST_TO_PROF;
		  break;
	  }
      case 3 : {
    	 users = user.findAllStudent();
    	  tp =  TYPE_NOT.BROADCAST_TO_STUDENT;
		  break;
	  }
     case 4 : {
    	 users = user.findAllWorker();
    	  tp =  TYPE_NOT.BROADCAST_TO_PERS;
		  break;
	  }
	  }
	  
	  for(UserEntity u: users) {
		  if(notDao.findByCodeAndUserId(u.getId(),ln.getId())==null) {
		  Notification n = new Notification();
		  n.setId_sender(utt.getId());
		  n.setId_receiver(u.getId());
		  n.setMsg(not.getMessage());
		  n.setTitre(not.getTitre());
		  n.setType_not(tp);
		  n.setCode_not(ln.getId());
		  n.setDate_rec(ln.getCreated_at());
		  notDao.save(n);
		
		   if(not.getCible()==3 || not.getCible()==1) {
			  if(u.getPere_id()!=null && u.getPere_id()>0  ) {
				 n.setMsg(not.getMessage()+"\n ["+u.getLastName()+" "+u.getFirstName()+"]");
			     n.setId_receiver(u.getPere_id());
			  }
			  if(u.getMere_id()!=null && u.getMere_id()>0) {
			     n.setId_receiver(u.getMere_id());
			     n.setMsg(not.getMessage()+"\n ["+u.getLastName()+" "+u.getFirstName()+"]");
			  }
			  notDao.save(n);
		   }
		  }
	  }
	  return true;
	}
	
	public boolean  sendNotToPromo(NotRequest not, UserEntity utt, LNot ln,Long idp) {
		List<Promo_cours> users = Arrays.asList() ;
		List<Parcours> parcours = Arrays.asList() ;
		 TYPE_NOT tp = TYPE_NOT.BROADCAST_TO_All;
		  switch(not.getCible()) {
		  case 1 : {
			  users = pcDao.getCoursByPromo(idp);
			  parcours = pars.getActParcours(idp);
			  tp =  TYPE_NOT.BROADCAST_TO_All;
			  for(Promo_cours cp: users) {
				  UserEntity u = cp.getCourse().getProf();
				  if(notDao.findByCodeAndUserId(u.getId(),ln.getId())==null) {
				  Notification n = new Notification();
				  n.setId_sender(utt.getId());
				  n.setId_receiver(u.getId());
				  n.setMsg(not.getMessage());
				  n.setTitre(not.getTitre());
				  n.setType_not(tp);
				  n.setCode_not(ln.getId());
				  n.setDate_rec(ln.getCreated_at());
				  notDao.save(n);
				  }
			  }
			  for(Parcours cp: parcours) {
				  UserEntity u = cp.getUser();
				  if(notDao.findByCodeAndUserId(u.getId(),ln.getId())==null) {
				  Notification n = new Notification();
				  n.setId_sender(utt.getId());
				  n.setId_receiver(u.getId());
				  n.setMsg(not.getMessage());
				  n.setTitre(not.getTitre());
				  n.setType_not(tp);
				  n.setCode_not(ln.getId());
				  n.setDate_rec(ln.getCreated_at());
				  notDao.save(n);
		
				  if(u.getPere_id()!=null && u.getPere_id()>0) {
				   n.setId_receiver( u.getPere_id());
				   n.setMsg(not.getMessage()+"\n ["+u.getLastName()+" "+u.getFirstName()+"]");
				  }
				  if(u.getMere_id()!=null && u.getMere_id()>0) {
				   n.setId_receiver(u.getMere_id());
				   n.setMsg(not.getMessage()+"\n ["+u.getLastName()+" "+u.getFirstName()+"]");
				  }
				  }
			  }
			  break;
		  }
	      case 2 : {
	    	  users = pcDao.getCoursByPromo(idp);
	    	  tp =  TYPE_NOT.BROADCAST_TO_PROF;
	    	  for(Promo_cours cp: users) {
				  UserEntity u = cp.getCourse().getProf();
				  if(notDao.findByCodeAndUserId(u.getId(),ln.getId())==null) {
				  Notification n = new Notification();
				  n.setId_sender(utt.getId());
				  n.setId_receiver(u.getId());
				  n.setMsg(not.getMessage());
				  n.setTitre(not.getTitre());
				  n.setType_not(tp);
				  n.setCode_not(ln.getId());
				  n.setDate_rec(ln.getCreated_at());
				  notDao.save(n);
				  }
			  }
			  break;
		  }
	      case 3 : {
	    	  parcours = pars.getActParcours(idp);
	    	  tp =  TYPE_NOT.BROADCAST_TO_STUDENT;
	    	  for(Parcours cp: parcours) {
				  UserEntity u = cp.getUser();
				  if(notDao.findByCodeAndUserId(u.getId(),ln.getId())==null) {
				  Notification n = new Notification();
				  n.setId_sender(utt.getId());
				  n.setId_receiver(u.getId());
				  n.setMsg(not.getMessage());
				  n.setTitre(not.getTitre());
				  n.setType_not(tp);
				  n.setCode_not(ln.getId());
				  n.setDate_rec(ln.getCreated_at());
				  notDao.save(n);
				  if(u.getPere_id()!=null && u.getPere_id()>0) {
				   n.setId_receiver(u.getPere_id());
				   n.setMsg(not.getMessage()+"\n ["+u.getLastName()+" "+u.getFirstName()+"]");
				  }
				  if(u.getMere_id()!=null && u.getMere_id()>0) {
				   n.setId_receiver(u.getMere_id());
				   n.setMsg(not.getMessage()+"\n ["+u.getLastName()+" "+u.getFirstName()+"]");
				  }
				 }
			  }
			  break;
		  }
		  }
		  
		 
		  return true;
		}

	public boolean sendNotToOneUser(UserEntity user, String msg,String titre,String btn) {
		  Notification n = new Notification();
		  n.setId_sender(user.getId());
		  n.setId_receiver(0L);
		  n.setMsg(msg);
		  n.setTitre(titre);
		  n.setType_not(TYPE_NOT.FORGET_PASS);
		  n.setCode_not(0L);
		  n.setDate_rec(new Date());
		  n.setBtn(btn);
		  notDao.save(n);
		  
		 if(user.getPere_id()!=null && user.getPere_id()>0) {
			   n.setId_receiver(user.getPere_id());
			   n.setMsg(msg+"\n ["+user.getLastName()+" "+user.getFirstName()+"]");
			  }
		 if(user.getMere_id()!=null && user.getMere_id()>0) {
			   n.setId_receiver(user.getMere_id());
			   n.setMsg(msg+"\n ["+user.getLastName()+" "+user.getFirstName()+"]");
		  }
		  notDao.save(n);
		  
		  return true;
	}
}

package com.hist.BysApp.projection;

import org.springframework.data.rest.core.config.Projection;

import com.hist.BysApp.entities.member.UserEntity;
import com.hist.BysApp.entities.promo.Course;

@Projection(
		  name = "CourseView", 
		  types = { Course.class }) 
public interface CourseView {
   public Long  getId();
   String getName();
   UserEntity getProf();
   String getCode();
}

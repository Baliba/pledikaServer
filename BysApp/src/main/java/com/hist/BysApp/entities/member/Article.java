package com.hist.BysApp.entities.member;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hist.BysApp.entities.cObj;

import lombok.Data;
@Entity
@Data
public class Article extends cObj implements Serializable  {
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy =  GenerationType.IDENTITY)
		private Long id;
	    private String terme;
	    private String  description;
}

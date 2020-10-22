package com.hist.BysApp.entities.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Data
@Entity
public class FileDB extends cObj implements Serializable {
	
	
	
    public FileDB() {
		super();
	}

	public FileDB(String fileName, String ct, byte[] bytes, String id, int te) {
       name = fileName;
       type_img= ct;
       data = bytes;
       id_entity = id;
       type_entity = te;
	}

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		public Long id;
	   
	    @Lob
	    private byte[] data;
	    
	    private String name;
	    
	    private String type_img;
	    
	    @Column(nullable = true, unique = true, updatable=false)
	    private String id_entity;
	    
	    private int  type_entity;

}

package com.hist.BysApp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hist.BysApp.Helper.FileStorageProperties;
import com.hist.BysApp.dao.UserRepository;

import Exceptions.FileStorageException;

@Service
public class FileStorageService {
	    private final Path fileStorageLocation;
	    
	    @Autowired
	    private UserRepository userInfoRepository;
	    
	    
	
	    
	    @Autowired
	    public FileStorageService(FileStorageProperties fileStorageProperties) {
	        String url = getClass().getClassLoader().getResource("static/assets/imgs").getPath();
	    	java.io.File file = new java.io.File(url);
	        this.fileStorageLocation   =  Paths.get(file.getAbsolutePath());
	        try {
	            Files.createDirectories(this.fileStorageLocation);
	        } catch (Exception ex) {
	       
	            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
	        }
	    }
	    public  String  now() {
	        Date date = new Date();
	        String strDateFormat = "hh:mm:ss a";
	        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	        String formattedDate = dateFormat.format(date);
	        return formattedDate.trim().replace(" ", "_").replace(":","_");
	    }
	    
	    public String storeFile(MultipartFile file) {
	    	
	    	//System.out.println("===================================");
	        // Normalize file name
	        String fileName = StringUtils.cleanPath("img_"+this.now()+"_"+file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	            	fileName = "";
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence ");
	            }

	            // Copy file to the target location (Replacing existing file with the same name)
	            Path targetLocation = this.fileStorageLocation.resolve(fileName);
	            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	          
	            return fileName;
	        } catch (IOException ex) {
	        	// ex.printStackTrace(System.out);
	        	fileName = "";
	            throw new FileStorageException("Could not store file .Please try again!", ex);
	        }
	    }

}

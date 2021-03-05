package com.hist.BysApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hist.BysApp.Helper.FileStorageProperties;

  @SpringBootApplication
  @ComponentScan
  @EnableConfigurationProperties({ FileStorageProperties.class })
  public class BysAppApplication {
	   
  // public class BysAppApplication  extends SpringBootServletInitializer {

   public static void main(final String[] args) {
		SpringApplication.run(BysAppApplication.class, args);
   }
	
//
//   @Override
//   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//      return application.sources(BysAppApplication.class);
//   }
}

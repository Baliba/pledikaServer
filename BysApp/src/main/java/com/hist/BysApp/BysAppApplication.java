package com.hist.BysApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hist.BysApp.Helper.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
public class BysAppApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BysAppApplication.class, args);
	}
	
}

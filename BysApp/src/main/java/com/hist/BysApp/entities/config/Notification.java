
package com.hist.BysApp.entities.config;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.enums.TYPE_NOT;

import lombok.Data;
@Data
@Entity
public class Notification extends cObj implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    private Long id_sender;
    private Long id_receiver;
    private String titre;
    private String msg;
    private Date date_rec;
    private TYPE_NOT type_not;
    private Long code_not;
    private String btn;    
    private boolean statut;
    
}

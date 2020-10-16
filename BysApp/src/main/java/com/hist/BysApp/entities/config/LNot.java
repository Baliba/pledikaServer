
package com.hist.BysApp.entities.config;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.hist.BysApp.entities.cObj;
import lombok.Data;
@Data
@Entity
public class LNot extends cObj implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long   id;
    private Long   id_sender;
    private String msg,titre;
    private Date   date_rec;
    private int to_who;
}

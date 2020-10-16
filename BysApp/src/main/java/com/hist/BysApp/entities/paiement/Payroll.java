package com.hist.BysApp.entities.paiement;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hist.BysApp.component.StaticData;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.grade.Niveau;

import lombok.Data;


@Entity
@Data
public class Payroll extends cObj  implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	@Column(unique=true)
	// over
    private String code;
	private Long   id_pers;
	private String nom;
	private String pnom;
	private String code_user;
	private Long   id_acad;
	private Long   id_mois;
	private String mois;
	private double salaire_brut;
	private double salaire_net;
	private Long   add_by;
    private String role,fonction, no_ref;
    private boolean receive = false;
    private Long rec_by;
    private Date date_rec;
    @Column(columnDefinition=" varchar (100) default 'CASH' ")
    private String  type_payroll;
}

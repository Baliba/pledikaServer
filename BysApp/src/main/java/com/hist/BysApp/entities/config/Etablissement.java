package com.hist.BysApp.entities.config;

import com.hist.BysApp.entities.cObj;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Etablissement extends cObj  implements Serializable {
	// new change 2
    private static final long serialVersionUID = 1L;
    @Id
    private String code; 
    private String logo;
    @Column(unique=true)
    private String background;
    private String name;
    private String cat_etab;
    private String annee_fond;
    private String email;
    private String phone_a;
    private String phone_b;
    private String phone_c;
    private String phone_d;
    private String description;
    private String website;
    private String depart;
    private String commune;
    private String adresse;
    private String token;
    private String currency;
    private boolean teacher,student;
    
    private double prix_admis;
    // moy 
    private int    moy_total;
    private double moy_accept;
    private double moy_reprise;
    private double moy_exc;
    // info respon
    private String nom_resp;
    private String email_resp;
    private String desc_resp;
    private String phone_e;
    private String phone_f;
    private String phone_g;
    private String phone_h;
    private String adresse_resp;
    // info sur taxe 
    @Column(columnDefinition=" float default 6.0 ")
    private float ona;
    
    @Column(columnDefinition=" float default 0 ")
    private float iri;
    
    @Column(columnDefinition=" float default 10.0 ")
    private float iri_1;
    
    @Column(columnDefinition=" float default 15.0 ")
    private float iri_2;
    
    @Column(columnDefinition=" float default 10.0 ")
    private float iri_3;
    
    @Column(columnDefinition=" float default 30 ")
    private float iri_4;
    
    @Column(columnDefinition=" float default 1 ")
    private float cfgdct;
    
    @Column(columnDefinition=" float default 1 ")
    private float fdu;
    @Column(columnDefinition=" float default 1 ")
    private float cas;
    @Column(columnDefinition=" float default 3 ")
    private float assure_mal;
    // salaire
    private double salairy_min;
    private double salairy_max;
    //------------------------
    private boolean block;
    private String  msg_block;
    private Date    last_pay;
    // package
   
    // admis  -----
    private boolean admis_is_open;
    private Date date_admis_debut;
    private Date date_admis_fin;
    // NEW DATA
    String fb, insta, twitter, youtube, linkedin, whatsapp;
    String mission, vision, valeur;
    private int nprog, ncours, nmat, ncmat;
    
    @Column(nullable = true, columnDefinition=" integer default 4 ")
    private int nbre_ctrl;
    
    @Column(nullable = true, columnDefinition=" varchar default 'Ctrl' ")
    private String frag_name;
    
    @Column(columnDefinition=" integer default 1 ")
    private int type_reprise;
    
    @Column(nullable = true, columnDefinition=" integer default 1 " )
    private int reprise;
    private String pfail_msg;
    private String ppass_msg;
    private String fail_msg;
    private String pass_msg;
    private String fail_msg_rep;
    private String pass_msg_rep;
    
  
    
    @Column(nullable = false, columnDefinition=" integer default 1 ")
    private int year_part;
    
    @Column(nullable = false, columnDefinition=" boolean default false ")
    private boolean over_config;
    
    // 1 par versement
    // 2 par mois
    @Column(nullable = false, columnDefinition=" integer default 1  ")
    private int type_paiement;
    
    @Column(nullable = true)
    private Long current_year;
    
    @Column(nullable = false, columnDefinition=" boolean default true")
    private boolean actived;
    
    @Column(nullable = false, columnDefinition=" boolean default true")
    private boolean payroll= true;
    
    @Column(nullable = true, updatable=true)
    Long id_img;
    
    @Column(nullable = false, columnDefinition=" boolean default false ")
    private boolean tuts;
    
    @Column(nullable = false, columnDefinition=" boolean default false ")
    private boolean app;
    
    @Column(nullable = true, updatable=true)
    private String start_time;
    
    @Column(nullable = true, updatable=true)
    private String end_time;
    
    @Column(nullable = false, columnDefinition=" boolean default true")
    private boolean bulletin;
    
    private String code_six, code_neuv, code_rheto, code_philo;
    
    @Column(nullable = true, columnDefinition=" varchar default 'https://pledika.com/' ")
    private String backend;
    
    
    @Column(nullable = true, columnDefinition=" varchar default 'http://192.168.0.1:8090/' ")
    private String smsGateway;
    
    @Column(nullable = true, columnDefinition=" varchar default 'admin' ")
    private String usernameGateway;
    
    @Column(nullable = true, columnDefinition=" varchar default '12345' ")
    private String passGateway;
    
    @Column(nullable = true)
    private String tokenGateway;
    
    @Column(nullable = false, columnDefinition=" integer default 1 ")
    private int mode_paiement;
    
    
}

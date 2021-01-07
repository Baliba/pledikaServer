/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hist.BysApp.entities.member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hist.BysApp.entities.cObj;
import com.hist.BysApp.entities.Location.Adresse;
import com.hist.BysApp.entities.Location.Phone;
import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.grade.Niveau;
import com.hist.BysApp.entities.paiement.PaiementAdmission;
import com.hist.BysApp.entities.promo.Course;
import com.hist.BysApp.entities.promo.Parcours;
import com.hist.BysApp.entities.promo.PromoFrag;
import com.hist.BysApp.entities.promo.Promotion;

import lombok.Data;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public @Data class UserEntity extends cObj implements Serializable, UserDetails{
	//  INSERT INTO user_entity(id, code) VALUES (nextval('user_entity_id_seq'), 'user_entity_seq');
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = true, unique = true, updatable=false)
    private String code;
    @Column(nullable = true, unique = true, updatable=true)
    private String nif,cin;
    
    @Column(nullable = false, length = 50)
    private String firstName;
    
    @Column(nullable = false, length = 50)
    private String lastName;
    
    @Column(nullable = false, length = 120, unique = true)
    private String username;
   
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String  avatar;
    @Column(nullable = true)
    private String etat_civil, sexe, statut,reference;
    private String nom_conjoint, phone, hphone, adresse;
    private boolean arefaire;
    private double  sold;
    
    private double  hsold;
    
    @ManyToOne
    @JsonIgnoreProperties("niveau_rel")
    private Niveau prev_class;
    
    @ManyToOne
    @JsonIgnoreProperties("niveau_rel")
    private Niveau next_class;
    
    @ManyToOne
    @JsonIgnoreProperties("niveau_rel")
    private Niveau current_class;
    
    
    @Column(nullable = true)
    private String nationalite = "Haitienne", autre_nom, token;
    
    @Column(nullable = true)
    private Date   date_de_naiss;
    
    @Column(nullable = true)
    private Long   pere_id, mere_id;
    
    
    private String last_year, last_moyen, last_etab;
    
    private String nom_ass, phone_ass, adresse_ass,email_ass; 
    
    private boolean valide_doc;
    
    @ManyToOne
    @JsonIgnoreProperties("users")
    public Role role;
    
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user") 
    private Collection <Parent> parent;
    
    @OneToMany(mappedBy = "prof")
    @JsonIgnoreProperties({"prof","promo_cours","programme","matiere"}) 
    private Collection <Course> courses;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user") 
    private Collection <Adresse> adresses;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user") 
    private Collection<Phone> phones;
    
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user") 
    private Collection<Maladie> maladies;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Ville lieu_de_naiss;
    
    
    private String   pob;
    
    @OneToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private  PaiementAdmission paiement_admission;
    
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value={"user","promotion","parcours_frag"}) 
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Parcours> parcours ; 
    
    @OneToMany(mappedBy = "titulaire")
    @JsonIgnoreProperties(value = {"titulaire","niveau_rel","promo_af","parcours","promofrag","promo_cours"})
    @NotFound(action = NotFoundAction.IGNORE)
    private Collection<Promotion> promotion;
    
    
    private String bank_account_HTG;
    private String bank_account_USD;
    private double salairy;
    private String religion;
    private String orphelin;
    private String fonction, nom_pere,nom_mere; 
    @Column(columnDefinition=" boolean default true ")
    private boolean enabled;
    
    @Column(columnDefinition=" boolean default false ")
    private boolean lover;
    
    @Column(columnDefinition=" Integer default 1234 ")
    private int  pin;
   
    
    @Column(nullable = true, unique = true, updatable=true, columnDefinition=" varchar default '' ")
    private String  identifiant;
    
    @Column(nullable = true, unique = true, updatable=true, columnDefinition=" varchar default '' ")
    private String  matricule;
    
    @Column(nullable = true, updatable=true)
    private String year_over;
    
    @Column(nullable = true, updatable=true)
    private String current_promo;
    
    @Column(nullable = true, updatable=true)
    Long id_img;
    
    private int granted = 0;
    
    private String annee_six, annee_neuv, annee_rheto, annee_philo;
    
    @Column(nullable = false, columnDefinition=" boolean default false ")
    private boolean exclude;
    
    public UserEntity(){}
    public UserEntity(String username, String password, String fname, String lname ) {
        this.username  = username;
        this.password  = password;
        this.firstName = fname;
        this.lastName  = lname;    
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> auth =  new ArrayList<>();
            String name = role.getName();
            auth.add(new SimpleGrantedAuthority(name));
            return auth; 
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
     }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
	@Override
	public String getPassword() {
	  return this.password ;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}

  
}

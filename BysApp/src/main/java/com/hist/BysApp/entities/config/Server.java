package com.hist.BysApp.entities.config;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.hist.BysApp.entities.cObj;

import lombok.Data;
@Data
@Entity
public class Server extends cObj  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String code;
    
    private String name;
    
    @Column(unique=true)
    private String server_name;


}

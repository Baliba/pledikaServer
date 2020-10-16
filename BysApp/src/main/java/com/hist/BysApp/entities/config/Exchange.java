
package com.hist.BysApp.entities.config;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.hist.BysApp.entities.cObj;

import lombok.Data;

@Entity
@Data
public class Exchange extends cObj implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    
    @ManyToOne
    public  Currency from;

    @ManyToOne
    public  Currency to;
    
    private double taux;
}

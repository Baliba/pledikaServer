
package com.hist.BysApp.entities.config;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.hist.BysApp.entities.cObj;
import lombok.Data;
@Entity
public class Currency extends cObj implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String code; 
    
    @Column(unique = true)
    private String name;
    
    @OneToMany(mappedBy ="to")
    Collection<Exchange> tos;
    
    @OneToMany(mappedBy = "from")
    private Collection<Exchange> froms;
}

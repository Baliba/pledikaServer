package com.hist.BysApp.model;

import java.util.Collection;
import java.util.List;

import com.hist.BysApp.entities.grade.Option;
import com.hist.BysApp.entities.paiement.CycleOPaie;
import com.hist.BysApp.entities.promo.ProgFourniture;
import com.hist.BysApp.entities.promo.Programme;

import lombok.Data;
@Data
public class FicheFourniture {
 public String name ;	
 private Collection<CycleOPaie> copaie;
 public List<List<ProgFourniture>>lpf; 
 public List<Programme>  programme;
}

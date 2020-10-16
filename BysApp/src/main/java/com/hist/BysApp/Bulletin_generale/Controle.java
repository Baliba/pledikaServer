package com.hist.BysApp.Bulletin_generale;
import java.util.Collection;

import com.hist.BysApp.entities.promo.Course;
import com.hist.BysApp.entities.promo.Results;

import lombok.Data;
@Data
public class Controle {
  private String name ;
  double  note_total;
  double  moy_class;
  double  moy_etudiant;
  int     absence, application, demerite;
  private Collection<Results> results;
 
}

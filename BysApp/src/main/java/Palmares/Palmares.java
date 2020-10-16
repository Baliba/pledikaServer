package Palmares;

import java.util.List;

import com.hist.BysApp.entities.promo.Frag_cours;

import lombok.Data;

@Data
public class Palmares {
   String promo_name;	
   List<Cours>  cours;
   List<Etudiant>    etudiants;
}

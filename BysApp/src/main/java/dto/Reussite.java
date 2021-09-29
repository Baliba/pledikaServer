package dto;

import java.util.List;

import lombok.Data;

@Data
public class Reussite {
  Long id;	
  String period; 
  Long total_class, total_f, total_g;
  Long total_iclass, total_if, total_ig;	
  Long total_echec , total_pass; 
  Long total_g_echec , total_g_pass; 
  Long total_f_echec , total_f_pass;
}

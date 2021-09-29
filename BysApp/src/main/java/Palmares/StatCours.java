package Palmares;

import java.util.List;

import lombok.Data;

@Data
public class StatCours {
	
	String cours;
	Long reussit;
	Long echec;
	public StatCours(String cours, Long reussit, Long total) {
		super();
		cours = cours;
		this.reussit = reussit;
		this.total = total;
		this.echec = total-reussit;
	}
	Long total;
	
	

}


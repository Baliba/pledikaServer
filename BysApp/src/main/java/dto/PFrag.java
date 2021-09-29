package dto;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;
@Data
public class PFrag {
	
	
	    private Long id;
	    private String code ;
	    Long id_promo;
		public PFrag(Long id, String code) {
			super();
			this.id = id;
			this.code = code;
		}
		
		public PFrag() {
			super();
		}

		public PFrag(Long id, String code, Long id_promo) {
			super();
			this.id = id;
			this.code = code;
			this.id_promo = id_promo;
		}
	    
	    


}

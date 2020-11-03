package dto;

import lombok.Data;

@Data
public class DCours {
    Long id ;
    String code;
	public DCours(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
    
}

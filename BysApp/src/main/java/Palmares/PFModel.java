package Palmares;

import lombok.Data;

@Data
public class PFModel {
   Long id;
   String share_code;
public PFModel(Long id, String share_code) {
	super();
	this.id = id;
	this.share_code = share_code;
}
}

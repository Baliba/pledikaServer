package com.hist.BysApp.model;

import java.io.Serializable;
import java.util.List;

import com.hist.BysApp.entities.Location.Ville;
import com.hist.BysApp.entities.grade.Document;
import com.hist.BysApp.entities.grade.Niveau;

import lombok.Data;

@Data
public class VilleAndDoc implements Serializable{
	private static final long   serialVersionUID = 1L;
	private final  List<Document> nivs;
    private final  List<Ville>  vis;
	public VilleAndDoc(List<Document> nivs, List<Ville> vis) {
		this.nivs = nivs;
		this.vis = vis;
	}
    
}

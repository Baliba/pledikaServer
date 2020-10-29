package com.hist.BysApp.Response;

import java.util.List;

import com.hist.BysApp.entities.enums.CEvent;
import com.hist.BysApp.entities.paiement.PVersement;

import lombok.Data;

@Data
public class REvent {
	private CEvent categorie;
}

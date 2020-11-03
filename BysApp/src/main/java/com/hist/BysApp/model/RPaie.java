package com.hist.BysApp.model;

import java.util.List;

import com.hist.BysApp.entities.promo.Parcours;

import lombok.Data;

@Data
public class RPaie {
   float max_gain, gain, to_gain, bgain;
   List<Parcours> p;

public RPaie(float max_gain, float gain, float to_gain ) {
	super();
	this.max_gain = max_gain;
	this.gain = gain;
	this.to_gain = to_gain;
}

public RPaie(float max_gain, float gain, float to_gain, float b ) {
	super();
	this.max_gain = max_gain;
	this.gain = gain;
	this.to_gain = to_gain;
	bgain = b;
}
   
}

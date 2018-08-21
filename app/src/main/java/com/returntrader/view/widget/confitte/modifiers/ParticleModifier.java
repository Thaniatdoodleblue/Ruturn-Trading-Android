package com.returntrader.view.widget.confitte.modifiers;


import com.returntrader.view.widget.confitte.Particle;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}

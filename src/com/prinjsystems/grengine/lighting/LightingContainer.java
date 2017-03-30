package com.prinjsystems.grengine.lighting;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.TransformGroup;

/**
 * This class only wrap all LightComponent things in Universe.
 */
public final class LightingContainer {
	
	private List<LightComponent> lights;
	
	public LightingContainer() {
		lights = new ArrayList<>();
	}
	
	public void addLight(LightComponent l) {
		lights.add(l);
	}
	
	public LightComponent getLight(int id) {
		return lights.get(id);
	}
	
	public TransformGroup getLightSpecific(int id) {
		return lights.get(id).getTransformGroup();
	}
	
	public int listSize() {
		return lights.size();
	}
}

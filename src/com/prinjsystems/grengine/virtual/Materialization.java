package com.prinjsystems.grengine.virtual;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import com.prinjsystems.grengine.utils.FloatArray3;
import com.prinjsystems.grengine.utils.FloatArray4;

/**
 * This class contain an ident of an materialization code, and can compile and apply this ident.
 */
@SuppressWarnings("unused")
public class Materialization {
	
	private String code;
	
	private List<Material> materials;
	
	public Materialization(String code) {
		this.code = code;
		materials = new ArrayList<>();
	}
	
	public String getCode() {
		return code;
	}
	
	/**
	 * Not finished!!!!!!!!!!!!!
	 */
	public void compile() {
		List<Color3f> ambients  = new ArrayList<>();
		List<Color3f> emissives = new ArrayList<>();
		List<Color3f> diffuses  = new ArrayList<>();
		List<Color3f> speculars = new ArrayList<>();
		
		List<FloatArray3> arrays_3 = new ArrayList<>();
		List<FloatArray4> arrays_4 = new ArrayList<>();
		
		String[] cmd = code.split(" ");
		
		for(int i = 0; i < cmd.length; i++) {
			String com = cmd[i];
			
			if(com.equals("mat = (.*\\..*, .*\\..*, .*\\..*, .*\\..*)")) {
				
			}
		}
	}
}

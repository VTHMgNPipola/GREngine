package com.prinjsystems.grengine.virtual;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.TexCoord2f;

import com.prinjsystems.grengine.Universe;
import com.prinjsystems.grengine.utils.GrengineException;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.utils.geometry.Sphere;

public class Thing {
	
	private BranchGroup bg;
	
	/**
	 * The new value of rotation or translation will be added to the current value.
	 */
	public static final int TRANSFORM_ROTATE_ADDICTIVE = 1;
	/**
	 * The new value of rotation or translation will take place of the current value.
	 */
	public static final int TRANSFORM_ROTATE_DEFINITIVE = 2;
	/**
	 * The new value of rotation or translation will be subtracted from the current value.
	 */
	public static final int TRANSFORM_ROTATE_SUBTRACTIVE = 3;
	
	private int trMode;
	
	private double tX, tY, tZ;
	private double rX, rY, rZ;
	
	private SimpleModel model;
	
	private Universe u;
	
	/**
	 * Simple constructor
	 */
	public Thing(Universe u) {
		trMode = TRANSFORM_ROTATE_DEFINITIVE;
		
		bg = new BranchGroup();
		
		this.u = u;
	}
	
	/**
	 * Complete constructor
	 */
	public Thing(String modelLocation, String[] names, Universe u) {
		trMode = TRANSFORM_ROTATE_DEFINITIVE;
		
		bg = new BranchGroup();
		
		try {
			Color ambientColour = new Color(1.0f, 1.0f, 1.0f);
		    Color diffuseColour = new Color(1.0f, 1.0f, 1.0f);
		    Color specularColour = new Color(1.0f, 1.0f, 1.0f);
		    Color emissiveColour = new Color(0.0f, 0.0f, 0.0f);
		    
		    float sh = 100.0f;
			
			model = new SimpleModel(modelLocation, names, ambientColour, emissiveColour, diffuseColour, 
					specularColour, sh);
			
			bg.addChild(model.getTransformGroup());
		} catch (IncorrectFormatException | ParsingErrorException | FileNotFoundException 
				| MalformedURLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
		
		this.u = u;
	}
	
	/**
	 * Creates a standard 50% size ColorCube.
	 */
	public void createTestSphere() {
		Appearance app = new Appearance();
		
		Color3f ambientColour = new Color3f(1.0f, 0.0f, 0.0f);
	    Color3f diffuseColour = new Color3f(1.0f, 0.0f, 0.0f);
	    Color3f specularColour = new Color3f(1.0f, 1.0f, 1.0f);
	    Color3f emissiveColour = new Color3f(0.0f, 0.0f, 0.0f);
	    
	    float s = 20.0f;
		
		app.setMaterial(new Material(ambientColour, emissiveColour, diffuseColour, specularColour, s));
		
		Sphere sphere = new Sphere(0.3f, Sphere.GENERATE_NORMALS, 120, app);
		bg.addChild(sphere);
	}
	
	/**
	 * Changes rotation of the model contained here.
	 * @param x new X axis rotation.
	 * @param y new Y axis rotation.
	 * @param z new Z axis rotation.
	 */
	public void rotate(double x, double y, double z) {
		if(trMode == TRANSFORM_ROTATE_DEFINITIVE) {
			model.rotate(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
		} else if(trMode == TRANSFORM_ROTATE_ADDICTIVE) {
			rX += x;
			rY += y;
			rZ += z;
			
			model.rotate(Math.toRadians(rX), Math.toRadians(rY), Math.toRadians(rZ));
		} else if(trMode == TRANSFORM_ROTATE_SUBTRACTIVE) {
			rX -= x;
			rY -= y;
			rZ -= z;
			
			model.rotate(Math.toRadians(rX), Math.toRadians(rY), Math.toRadians(rZ));
		}
	}
	
	/**
	 * Changes position of the model contained here.
	 * @param x new X axis position.
	 * @param y new Y axis position.
	 * @param z new Z axis position.
	 */
	public void translate(double x, double y, double z) {
		if(trMode == TRANSFORM_ROTATE_DEFINITIVE) {
			model.translate(x * u.getDeltaTime(), y * u.getDeltaTime(), z * u.getDeltaTime());
		} else if(trMode == TRANSFORM_ROTATE_ADDICTIVE) {
			tX += x;
			tY += y;
			tZ += z;
			
			model.translate(tX * u.getDeltaTime(), tY * u.getDeltaTime(), tZ * u.getDeltaTime());
		} else if(trMode == TRANSFORM_ROTATE_SUBTRACTIVE) {
			tX -= x;
			tY -= y;
			tZ -= z;
			
			model.translate(tX * u.getDeltaTime(), tY * u.getDeltaTime(), tZ * u.getDeltaTime());
		}
	}
	
	/**
	 * Changes the natural size of the model.
	 * @param factor Change the size of the model. Lower than zero and the model will reduce, greater than 
	 * zero and it will enlarge.
	 */
	public void scale(double factor) {
		model.scale(factor);
	}
	
	/**
	 * Changes the TransferRotation Mode, which defines if the new value of translation/rotation will 
	 * replace the last, or will be added to then.
	 * @param mode Is the mode to take place of the last. Can be {@link TRANSFORM_ROTATE_ADDICTIVE}, 
	 * {@link TRANSFORM_ROTATE_DEFINITIVE} or {@link TRANSFORM_ROTATE_SUBTRACTIVE}.
	 */
	public void setTransformRotateMode(int mode) {
		trMode = mode;
		System.out.println("Changed TransformRotate Mode to " + mode);
	}
	
	public void setTexture(Universe u, String path, int id) {
		model.setTexture(u, path, id);
	}
	
	public void setTextures(Universe u, String[] paths) throws GrengineException {
		model.setTextures(u, paths);
	}
	
	public void setAllTextures(Universe u, String path) {
		model.setAllTextures(u, path);
	}
	
	public void setTextureCoordinates(List<TexCoord2f> coordinates) {
		model.setTextureCoordinates(coordinates);
	}
	
	public void setReflectionMaterial(int index, Color ambientColor, Color emissiveColor, 
			Color diffuseColor, Color specularColor, float shiness) {
		model.setReflectionMaterial(index, ambientColor, emissiveColor, diffuseColor, specularColor, shiness);
	}
	
	public void setAllReflectionMaterials(Color ambientColor, Color emissiveColor, 
			Color diffuseColor, Color specularColor, float shiness) {
		model.setAllReflectionMaterials(ambientColor, emissiveColor, diffuseColor, specularColor, shiness);
	}
	
	public Color getReflectionAmbientColor(int index) {
		return model.getReflectionAmbientColor(index);
	}
	
	public Color getReflectionEmissiveColor(int index) {
		return model.getReflectionEmissiveColor(index);
	}
	
	public Color getReflectionDiffuseColor(int index) {
		return model.getReflectionDiffuseColor(index);
	}
	
	public Color getReflectionSpecularColor(int index) {
		return model.getReflectionSpecularColor(index);
	}
	
	public void addFormMaterial(Color ambientColor, Color emissiveColor, Color diffuseColor, 
			Color specularColor, float shiness) {
		model.addFormMaterial(ambientColor, emissiveColor, diffuseColor, specularColor, shiness);
	}
	
	public void mixMaterials(int materialIndex, int formMaterialIndex) {
		model.mixMaterials(materialIndex, formMaterialIndex);
	}
	
	/**
	 * Mix all current reflection materials with the specified form material.
	 * @param formMaterialIndex Index of the form material to be integrated to all reflection materials.
	 */
	public void mixAllMaterials(int formMaterialIndex) {
		model.mixAllMaterials(formMaterialIndex);
	}
	
	/**
	 * Compile the model in the {@link BranchGroup} and return it.
	 * @return The compiled {@link BranchGroup}.
	 */
	public BranchGroup getModel() {
//		if(model != null) {
//			tg.addChild(model);
//			bg.addChild(tg);
//		}
		
		bg.compile();
		return bg;
	}
	
	public SimpleModel getPrimitiveModel() {
		return model;
	}
}

package com.prinjsystems.grengine.virtual;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.Vector3d;

import com.prinjsystems.grengine.Universe;
import com.prinjsystems.grengine.utils.GrengineException;
import com.prinjsystems.grengine.utils.ModelLoader;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

public class SimpleModel {
	
	private TransformGroup group;
	private Transform3D transform;
	
	private List<Shape3D> shapes;
	
	private List<Appearance> apps;
	private List<Texture> textures;
	private List<GeometryArray> geometries;
	private List<Material> materials;
	
	private double scaleFactor;
	private Vector3d transformationVector;
	
	public SimpleModel() {
		group = new TransformGroup();
		transform = new Transform3D();
		
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		shapes = new ArrayList<>();
		
		apps = new ArrayList<>();
		textures = new ArrayList<>();
		
		transformationVector = new Vector3d();
		geometries = new ArrayList<>();
		materials = new ArrayList<>();
	}
	
	public SimpleModel(String modelLocation, String[] names) throws FileNotFoundException, 
	IncorrectFormatException, ParsingErrorException, MalformedURLException {
		Scene sc = ModelLoader.loadOBJModel(modelLocation);
		@SuppressWarnings("unchecked")
		Map<String, Shape3D> shape = sc.getNamedObjects();
		sc.getSceneGroup().removeAllChildren();
		
		group = new TransformGroup();
		transform = new Transform3D();
		
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		shapes = new ArrayList<>();
		apps = new ArrayList<>();
		textures = new ArrayList<>();
		
		for(int i = 0; i < names.length; i++) {
			String s = names[i];
			
			Shape3D sha = shape.get(s);
			sc.getNamedObjects().remove(sha);
			
			shapes.add(sha);
			shapes.get(i).setAppearance(apps.get(i));
			group.addChild(shapes.get(i));
		}
		
		transformationVector = new Vector3d();
		geometries = new ArrayList<>();
		materials = new ArrayList<>();
	}
	
	public SimpleModel(String modelLocation, String[] names, Color ambientColor, Color emissiveColor, 
			Color diffuseColor, Color specularColor, float shine) throws FileNotFoundException, 
	IncorrectFormatException, ParsingErrorException, MalformedURLException {
		Scene sc = ModelLoader.loadOBJModel(modelLocation);
		@SuppressWarnings("unchecked")
		Map<String, Shape3D> shape = sc.getNamedObjects();
		sc.getSceneGroup().removeAllChildren();
		
		group = new TransformGroup();
		transform = new Transform3D();
		
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		group.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		shapes = new ArrayList<>();
		
		Color3f ambient  = new Color3f(ambientColor);
		Color3f emissive = new Color3f(emissiveColor);
		Color3f diffuse  = new Color3f(diffuseColor);
		Color3f specular = new Color3f(specularColor);
		
		apps = new ArrayList<>();
		textures = new ArrayList<>();
		materials = new ArrayList<>();
		
		PointAttributes pointAttribs = new PointAttributes();
		pointAttribs.setPointAntialiasingEnable(true);
		LineAttributes lineAttribs = new LineAttributes();
		lineAttribs.setLineAntialiasingEnable(true);
		
		for(int i = 0; i < names.length; i++) {
			apps.add(new Appearance());
			Material material = new Material(ambient, emissive, diffuse, specular, shine);
			apps.get(i).setMaterial(material);
			apps.get(i).setPointAttributes(pointAttribs);
			apps.get(i).setLineAttributes(lineAttribs);
			materials.add(material);
			String s = names[i];
			
			Shape3D sha = shape.get(s);
			sc.getNamedObjects().remove(sha);
			
			shapes.add(sha);
			shapes.get(i).setAppearance(apps.get(i));
			group.addChild(shapes.get(i));
		}
		
		transformationVector = new Vector3d();
		geometries = new ArrayList<>();
	}
	
	public void rotate(double x, double y, double z) {
		Transform3D ry = new Transform3D();
		Transform3D rz = new Transform3D();
		
		transform.rotX(x);
		ry.rotY(y);
		rz.rotZ(z);
		
		transform.mul(ry);
		transform.mul(rz);
		
		transform.setScale(scaleFactor);
		transform.setTranslation(transformationVector);
		
		group.setTransform(transform);
	}
	
	public void translate(double x, double y, double z) {
		transformationVector.setX(x);
		transformationVector.setY(y);
		transformationVector.setZ(z);
		transform.setTranslation(new Vector3d(x, y, z));
		group.setTransform(transform);
	}
	
	public void scale(double factor) {
		scaleFactor = factor;
		transform.setScale(factor);
		group.setTransform(transform);
	}
	
	public TransformGroup getTransformGroup() {
		return group;
	}
	
	/**
	 * 
	 * @param path
	 * @param resolution Must be a power of 2.
	 */
	public void setTexture(Universe u, String path, int id) {
		textures.add(id, u.loadTexture(path));
		TextureAttributes attribs = new TextureAttributes();
		attribs.setTextureMode(TextureAttributes.MODULATE);
		apps.get(id).setTexture(textures.get(id));
		apps.get(id).setTextureAttributes(attribs);
	}
	
	public void setTextures(Universe u, String[] paths) throws GrengineException {
		if(paths.length > group.numChildren()) {
			throw new GrengineException("Too many textures!");
		} else if(paths.length < group.numChildren()) {
			throw new GrengineException("Need more textures!");
		}
		
		TextureAttributes attribs = new TextureAttributes();
		attribs.setTextureMode(TextureAttributes.MODULATE);
		
		for(int i = 0; i < paths.length; i++) {
			textures.add(u.loadTexture(paths[i]));
			apps.get(i).setTexture(textures.get(i));
			apps.get(i).setTextureAttributes(attribs);
		}
	}
	
	public void setAllTextures(Universe u, String path) {
		textures.clear();
		
		List<Material> materials = new ArrayList<>();
		
		for(int i = 0; i < apps.size(); i++) {
			materials.add(apps.get(i).getMaterial());
		}
		
		apps.clear();
		
		TextureAttributes attribs = new TextureAttributes();
		attribs.setTextureMode(TextureAttributes.MODULATE);
		
		for(int i = 0; i < group.numChildren(); i++) {
			textures.add(u.loadTexture(path));
			apps.add(new Appearance());
			apps.get(i).setMaterial(materials.get(i));
			apps.get(i).setTexture(textures.get(i));
			apps.get(i).setTextureAttributes(attribs);
			((Shape3D) group.getChild(i)).setAppearance(apps.get(i));
		}
	}
	
	public void setTextureCoordinates(List<TexCoord2f> textureCoords) {
		geometries.clear();
		
		for(int i = 0; i < textures.size(); i++) {
			((Shape3D) group.getChild(i)).removeAllGeometries();
			QuadArray array = new QuadArray(4, GeometryArray.COORDINATES 
					| GeometryArray.TEXTURE_COORDINATE_2);
			for(int id = 0; i < 4; i++) {
				array.setTextureCoordinate(0, i + id, textureCoords.get(i + id));
			}
			
			geometries.add(array);
			((Shape3D) group.getChild(i)).addGeometry(geometries.get(i));
		}
	}
	
	public void setReflectionMaterial(int index, Color ambientColor, Color emissiveColor, 
			Color diffuseColor, Color specularColor, float shiness) {
		Color3f ambient  = new Color3f(ambientColor);
		Color3f emissive = new Color3f(emissiveColor);
		Color3f diffuse  = new Color3f(diffuseColor);
		Color3f specular = new Color3f(specularColor);
		
		if(index < materials.size()) {
			materials.add(index, new Material(ambient, emissive, diffuse, specular, shiness));
			apps.get(index).setMaterial(materials.get(index));
		}
	}
	
	public void setAllReflectionMaterials(Color ambientColor, Color emissiveColor, Color diffuseColor, 
			Color specularColor, float shiness) {
		Color3f ambient  = new Color3f(ambientColor);
		Color3f emissive = new Color3f(emissiveColor);
		Color3f diffuse  = new Color3f(diffuseColor);
		Color3f specular = new Color3f(specularColor);
		
		materials.clear();
		
		for(int i = 0; i < apps.size(); i++) {
			materials.add(new Material(ambient, emissive, diffuse, specular, shiness));
			apps.get(i).setMaterial(materials.get(i));
		}
	}
}

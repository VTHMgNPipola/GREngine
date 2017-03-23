package com.prinjsystems.grengine.lighting;

import java.awt.Color;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class LightComponent {

	private Light light;
	private TransformGroup group;
	private Transform3D transform;
	private BoundingSphere lightArea;
	
	private Color3f color;
	private Vector3f direction;
	
	public static final int DIRECTIONAL_LIGHT = 0;
	
	public LightComponent(int type, Color color, Vector3f direction, BoundingSphere lightArea) {
		transform = new Transform3D();
		group = new TransformGroup(transform);
		
		this.color = new Color3f(color);
		this.direction = direction;
		
		if(type == DIRECTIONAL_LIGHT) {
			light = new DirectionalLight(this.color, this.direction);
		}
		
		this.light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
		this.light.setInfluencingBounds(lightArea);
		this.lightArea = lightArea;
		
		group.addChild(light);
	}
	
	public void changeBoundingSphere(BoundingSphere area) {
		lightArea = area;
		light.setInfluencingBounds(lightArea);
	}
	
	public BoundingSphere getLightArea() {
		return lightArea;
	}
	
	public TransformGroup getTransformGroup() {
		return group;
	}
	
//	public void setTranslation(double x, double y, double z) {
//		transform.setTranslation(new Vector3d(x, y, z));
//		group.setTransform(transform);
//	}
//	
//	public void setRotation(double x, double y, double z) {
//		Transform3D ry = new Transform3D();
//		Transform3D rz = new Transform3D();
//		
//		transform.rotX(x);
//		ry.rotY(y);
//		rz.rotZ(z);
//		
//		transform.mul(ry);
//		transform.mul(rz);
//		
//		group.setTransform(transform);
//	}
}

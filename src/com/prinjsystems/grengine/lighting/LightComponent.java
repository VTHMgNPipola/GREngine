package com.prinjsystems.grengine.lighting;

import java.awt.Color;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Light;
import javax.media.j3d.PointLight;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.prinjsystems.grengine.utils.DoubleArray3;
import com.prinjsystems.grengine.utils.FloatArray3;

/**
 * This class is a box for all dependencies of an light, that can be Point, Spot or Directional (Ambient can 
 * not because Universe contains a very large so).
 * Each constructor has it's own dependencies, like direction, color, focus factor and spread angle.
 */
public class LightComponent {

	protected Light light;
	protected TransformGroup group;
	protected Transform3D transform;
	private BoundingSphere lightArea;
	private Point3f position;
	
	protected Color3f color;
	
	private int type;
	
	private boolean isSpotOrDirectional;
	private Vector3f direction;
	private boolean isSpot;
	private float angle;
	private float focus;
	
	/**
	 * This constructor instantiates a light of the type Point.
	 */
	public LightComponent(Color color, BoundingSphere lightArea) {
		transform = new Transform3D();
		group = new TransformGroup(transform);
		
		this.color = new Color3f(color);
		
		Point3d p = new Point3d();
		lightArea.getCenter(p);
		this.position = new Point3f((float) p.x, (float) p.x, (float) p.x);
		
		this.type = -1;
		isSpotOrDirectional = false;
		isSpot = false;
		
		light = new PointLight(new Color3f(color), position, new Point3f(1.0f, 0.0f, 0.0f));
		
		this.light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
		this.light.setInfluencingBounds(lightArea);
		this.lightArea = lightArea;
		
		group.addChild(light);
	}
	
	/**
	 * This constructor instantiates a light of the type Spot.
	 */
	public LightComponent(int type, Color color, BoundingSphere lightArea, FloatArray3 direction, 
			float angle, float focus) {
		transform = new Transform3D();
		group = new TransformGroup(transform);
		
		this.color = new Color3f(color);
		
		Point3d p = new Point3d();
		lightArea.getCenter(p);
		this.position = new Point3f((float) p.x, (float) p.x, (float) p.x);
		
		this.type = type;
		isSpotOrDirectional = true;
		isSpot = true;
		
		this.direction = new Vector3f(direction.getA(), direction.getB(), direction.getC());
		this.focus = focus;
		this.angle = angle;
		
		light = new SpotLight(new Color3f(color), position, new Point3f(1.0f, 0.0f, 0.0f), this.direction, 
				angle, focus);
		
		this.light.setCapability(DirectionalLight.ALLOW_DIRECTION_WRITE);
		this.light.setInfluencingBounds(lightArea);
		this.lightArea = lightArea;
		
		group.addChild(light);
	}
	
	/**
	 * This constructor instantiates a light of the type Directional.
	 */
	public LightComponent(int type, Color color, BoundingSphere lightArea, FloatArray3 direction) {
		transform = new Transform3D();
		group = new TransformGroup(transform);
		
		this.color = new Color3f(color);
		
		Point3d p = new Point3d();
		lightArea.getCenter(p);
		this.position = new Point3f((float) p.x, (float) p.x, (float) p.x);
		
		this.type = type;
		isSpotOrDirectional = true;
		isSpot = true;
		
		this.direction = new Vector3f(direction.getA(), direction.getB(), direction.getC());
		
		light = new DirectionalLight(new Color3f(color), this.direction);
		
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
	
	public int getType() {
		return type;
	}
	
	public boolean isSpotOrDirectional() {
		return isSpotOrDirectional;
	}
	
	public boolean isSpot() {
		return isSpot;
	}
	
	public float getSpreadAngle() {
		return angle;
	}
	
	public float getFocusFactor() {
		return focus;
	}
	
	public FloatArray3 getDirection() {
		return new FloatArray3(direction.x, direction.y, direction.z);
	}
	
	public void setTranslation(DoubleArray3 translation) {
		transform.setTranslation(new Vector3d(translation.getA(), translation.getB(), translation.getC()));
		group.setTransform(transform);
	}
	
	public void setRotation(DoubleArray3 translation) {
		Transform3D ry = new Transform3D();
		Transform3D rz = new Transform3D();
		
		transform.rotX(translation.getA());
		ry.rotY(translation.getB());
		rz.rotZ(translation.getC());
		
		transform.mul(ry);
		transform.mul(rz);
		
		group.setTransform(transform);
	}
}

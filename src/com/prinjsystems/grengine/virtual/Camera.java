package com.prinjsystems.grengine.virtual;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.universe.ViewingPlatform;

public class Camera {
	
	private ViewingPlatform viewPltf;
	
	private TransformGroup group;
	private Transform3D transform;
	
	private KeyHandler handler;
	
	private double x, y, z, r;
	
	public Camera() {
		viewPltf = new ViewingPlatform();
		viewPltf.getViewPlatform().setActivationRadius(300f);
		//u.getCompletelyAbstractDigitalSimpleUniverse().getViewingPlatform().removeAllChildren();
		group = viewPltf.getViewPlatformTransform();
		transform = new Transform3D();
		transform.invert();
		transform.lookAt(new Point3d(0f, 0f, 0f), new Point3d(-1f, -1f, -1f), new Vector3d(-1f, -1f, -1f));
		handler = new KeyHandler();
	}
	
	@SuppressWarnings("static-access")
	public void move() {
		
		// Condition processing
		if(handler.keys[KeyEvent.VK_W]) {
			x += 0.1;
		} if(handler.keys[KeyEvent.VK_S]) {
			x -= 0.1;
		} if(handler.keys[KeyEvent.VK_A]) {
			z += 0.1;
		} if(handler.keys[KeyEvent.VK_D]) {
			z -= 0.1;
		}
		
		if(handler.keys[KeyEvent.VK_Q]) {
			r += 0.05;
		} if(handler.keys[KeyEvent.VK_E]) {
			r -= 0.05;
		}
		
		// At final
		transform.setTranslation(new Vector3d(x, y, z));
		transform.rotY(r);
		group.setTransform(transform);
	}
	
	public KeyListener getCameraComponent() {
		return handler;
	}
	
	public ViewingPlatform getCamera() {
		return viewPltf;
	}
}

class KeyHandler implements KeyListener {
	
	public static boolean[] keys = new boolean[68836];
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode > 0 && keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode > 0 && keyCode < keys.length) {
			keys[keyCode] = true;
		}
	}
	
}

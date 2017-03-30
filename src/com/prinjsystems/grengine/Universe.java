package com.prinjsystems.grengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Node;
import javax.media.j3d.Texture;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import com.prinjsystems.grengine.lighting.LightComponent;
import com.prinjsystems.grengine.lighting.LightingContainer;
import com.prinjsystems.grengine.virtual.Camera;
import com.prinjsystems.grengine.virtual.Thing;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.Viewer;

public final class Universe implements Runnable {
	
	private SimpleUniverse universe;
	
	private GraphicsConfiguration gConfs;
	private Canvas3D canvas;
	
	private JFrame frame;
	
	private Dimension dimension;
	
	private List<Thing> things;
	private List<Node> alternativeNodes;
	
	private BranchGroup main;
	
	private boolean vSyncEnable;
	private int fpsCap;
	
	private AmbientLight ambientLight;
	
	private LightingContainer container;
	
	private Background back;
	
	private Camera camera;
	
	private long lastTime;
	private int deltaTime;
	
	/*
	 * Version! \/\/\/\/\/\/\/
	 */
	/**
	 * This is the version of GREngine!
	 */
	public static final String grengineVersion = "dev0.9.9.3 update 4";
	/*
	 * Version! /\/\/\/\/\/\/\
	 */
	
	/**
	 * Secondary constructor of Universe.
	 * @param w Width of the frame.
	 * @param h Height of the frame.
	 * @param camera Camera component to attach.
	 */
	public Universe(int w, int h, Camera camera) {
		System.err.println("---->");
		System.err.println("--->");
		System.err.println("--> GREngine " + grengineVersion + " start! <--");
		System.err.println("--->");
		System.err.println("---->");
		
		vSyncEnable = false;
		fpsCap = 60;
		
		container = new LightingContainer();
		
		dimension = new Dimension(w, h);
		frame = new JFrame();
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				destroy();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.setTitle("Game");
		
		GraphicsConfigTemplate3D gct3D= new GraphicsConfigTemplate3D();
		gct3D.setSceneAntialiasing(GraphicsConfigTemplate3D.REQUIRED);
		gConfs = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
				getBestConfiguration(gct3D);
		canvas = new Canvas3D(gConfs);
		
		Viewer v = new Viewer(canvas);
		View view = v.getView();
		view.setBackClipDistance(300);
		
		this.camera = camera;
		universe = new SimpleUniverse(this.camera.getCamera(), v);
		
		frame.add(canvas);
		frame.addKeyListener(this.camera.getCameraComponent());
		
		things = new ArrayList<>();
		alternativeNodes = new ArrayList<>();
		
		main = new BranchGroup();
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
		
		ambientLight = new AmbientLight(new Color3f(Color.white));
		ambientLight.setInfluencingBounds(bounds);
		
		back = new Background(new Color3f(0, 191, 255));
		back.setApplicationBounds(bounds);
		
		lastTime = 0;
	}
	
	/**
	 * Default constructor of Universe.
	 * @param w Width of the frame.
	 * @param h Height of the frame.
	 */
	public Universe(int w, int h) {
		System.err.println("---->");
		System.err.println("--->");
		System.err.println("--> GREngine " + grengineVersion + " start! <--");
		System.err.println("--->");
		System.err.println("---->");
		
		vSyncEnable = false;
		fpsCap = 60;
		
		container = new LightingContainer();
		
		dimension = new Dimension(w, h);
		frame = new JFrame();
		frame.setSize(dimension);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				destroy();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.setTitle("Game");
		
		GraphicsConfigTemplate3D gct3D= new GraphicsConfigTemplate3D();
		gct3D.setSceneAntialiasing(GraphicsConfigTemplate3D.REQUIRED);
		gConfs = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
				getBestConfiguration(gct3D);
		canvas = new Canvas3D(gConfs);
		
		universe = new SimpleUniverse(canvas);
		
		frame.add(canvas);
		
		things = new ArrayList<>();
		alternativeNodes = new ArrayList<>();
		
		main = new BranchGroup();
		
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
		
		ambientLight = new AmbientLight(new Color3f(Color.white));
		ambientLight.setInfluencingBounds(bounds);
		
		back = new Background(new Color3f(0, 191, 255));
		back.setApplicationBounds(bounds);
		
		lastTime = 0;
	}
	
	/**
	 * Change the title of the game screen.
	 * @param title New title
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	/**
	 * Adds a Thing instance to the rendering list.
	 * @param thing Thing to be added.
	 */
	public void addThing(Thing thing) {
		things.add(thing);
	}
	
	/**
	 * Adds a group of Things to the rendering list.
	 * @param things A Collection of Things to be added.
	 */
	public void addAllThings(Collection<Thing> things) {
		this.things.addAll(things);
	}
	
	/**
	 * Adds an primitive Node or Leaf to the secondary rendering list. This method is so primitive, now use 
	 * addLightComponent to add a light thing.
	 * @param node Node or Leaf to be added.
	 */
	@Deprecated
	public void addAlternativeNode(Node node) {
		alternativeNodes.add(node);
	}
	
	/**
	 * Adds a group of Nodes or Leafs to the secondary rendering list. This method is so primitive, now use 
	 * addAllLightComponent to add a group of light things.
	 * @param nodes A Collection of Nodes or Leafs to be added.
	 */
	@Deprecated
	public void addAllAlternativeNodes(Collection<Node> nodes) {
		alternativeNodes.addAll(nodes);
	}
	
	/**
	 * Redefines the condition of the resize event, if false, the window can'not resize, but if true, the 
	 * window can be resized freely.
	 * @param mode The new value for ResizeMode.
	 */
	public void setWindowResizeMode(boolean mode) {
		frame.setResizable(mode);
	}
	
	/**
	 * Change the area of effect of the default AmbientLight.
	 * @param bounds The new bounds of AmbientLight.
	 */
	public void setAmbientLightBounds(Bounds bounds) {
		ambientLight.setInfluencingBounds(bounds);
	}
	
	/**
	 * Change the color of the default AmbientLight.
	 * @param color The new color for AmbientLight.
	 */
	public void setAmbientLightColor(Color3f color) {
		ambientLight.setColor(color);
	}
	
	/**
	 * Load a texture. Used only by internal methods of Thing.
	 */
	public Texture loadTexture(String path) {
		TextureLoader loader = new TextureLoader(path, canvas);
//		Texture2D texture = new Texture2D(Texture.BASE_LEVEL_POINT, Texture.RGBA, dimension, dimension);
//		texture.setImage(0, loader.getImage());
		Texture texture = loader.getTexture();
		
		return texture;
	}
	
	/**
	 * Adds a light component to light primary rendering list.
	 * @param light LightComponent to be added.
	 */
	public void addLightComponent(LightComponent light) {
		container.addLight(light);
	}
	
	/**
	 * Adds a group of light component to light primary rendering list.
	 * @param light LightComponent to be added.
	 */
	public void addAllLightComponent(Collection<LightComponent> light) {
		for(LightComponent l : light) {
			container.addLight(l);
		}
	}
	
	public synchronized SimpleUniverse getCompletelyAbstractDigitalSimpleUniverse() {
		return universe;
	}
	
	public int getDeltaTime() {
		return deltaTime;
	}
	
	public synchronized void resetUniverseWithoutCamera() {
		universe.cleanup();
		universe = new SimpleUniverse(canvas);
		camera = null;
	}
	
	/**
	 * Destroy the application window and finish execution with code 0.
	 */
	public void destroy() {
		System.out.println("Destroying window and resources...");
		universe.cleanup();
		System.exit(0);
	}
	
	/**
	 * Changes state of vSync.
	 * @param vsync The new value for vSync mode.
	 */
	public void vSyncMode(boolean vsync) {
		vSyncEnable = vsync;
	}
	
	/**
	 * Changes the FPS cap of vSync.
	 * @param fpsCap Max FPS of the application.
	 */
	public void vSyncFPS(int fpsCap) {
		this.fpsCap = fpsCap;
	}
	
	/**
	 * Start the rendering process. This is the most important method of renderization sequence, and must 
	 * be run before all other methods.
	 */
	public synchronized void startup() {
		System.out.println("Starting up rendering process...");
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Finish the render of each frame, and apply vSync. This method not are obligatory, but if used, must 
	 * stay at the last in Processing Loop.
	 */
	public synchronized void finishRender() {
		try {
			if(vSyncEnable && fpsCap != 0) {
				Thread.sleep(1000/fpsCap);
			}
			if(camera != null) {
				camera.move();
			}
			
			long time = System.nanoTime();
			deltaTime = (int) ((time - lastTime) / 1000000);
			lastTime = time;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error finishing rendering!\n" + e.getMessage(), "!Error!",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Don't use this method! This method is purely dependent of other Universe states, and not work properly
	 * without these!
	 */
	@Override
	public void run() {
		frame.setVisible(true);
		
		universe.getViewingPlatform().setNominalViewingTransform();
		
		for(Thing t : things) {
			universe.addBranchGraph(t.getModel());
		}
		
		if(alternativeNodes.size() != 0) {
			for(Node n : alternativeNodes) {
				main.addChild(n);
			}
		}
		
		if(container.listSize() != 0) {
			for(int i = 0; i < container.listSize(); i++) {
				main.addChild(container.getLightSpecific(i));
			}
		}
		
		if(alternativeNodes.size() == 0 && container.listSize() == 0) {
			main.addChild(ambientLight);
		}
		
		main.addChild(back);
		
		universe.addBranchGraph(main);
		
		lastTime = System.nanoTime();
		deltaTime = 1;
	}
}

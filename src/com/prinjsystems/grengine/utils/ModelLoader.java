package com.prinjsystems.grengine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.Map;

import javax.media.j3d.Shape3D;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import com.sun.j3d.loaders.objectfile.ObjectFile;

public class ModelLoader {
	
//	private static Map<String, Scene> loadedModels;
//	
//	static {
//		loadedModels = new HashMap<>();
//	}
	
	@SuppressWarnings("unchecked")
	public static Scene loadOBJModel(String path) throws FileNotFoundException,
	IncorrectFormatException, ParsingErrorException, MalformedURLException {
		System.out.println("Loading " + path + "...");
		Scene scene;
		
//		if(getModel(path)) {
			ObjectFile loader = new ObjectFile();
			scene = loader.load(new File(path).toURI().toURL());
			Map<String, Shape3D> shapes = scene.getNamedObjects();
			
			for(String s : shapes.keySet()) {
				System.out.println("Name: " + s);
			}
			
//			loadedModels.put(path, scene);
//		} else {
//			System.out.println("This model alleardy loaded!");
//			scene = loadedModels.get(path);
//		}
		
		return scene;
		//return loader.load(new FileReader(path));
	}
	
	public static Scene loadLWOModel(String path) throws FileNotFoundException, IncorrectFormatException,
	ParsingErrorException {
		System.out.println("Loading " + path + "...");
		Lw3dLoader loader = new Lw3dLoader();
		return loader.load(new FileReader(path));
	}
	
//	private static boolean getModel(String path) {
//		boolean bol = false;
//		
//		try {
//			if(loadedModels.get(path) == null) {
//				bol = false;
//			} else if(loadedModels.get(path) != null && loadedModels.size() > 0) {
//				bol = true;
//			}
//		} catch(Exception e) {
//			bol = false;
//		}
//		return bol;
//	}
}

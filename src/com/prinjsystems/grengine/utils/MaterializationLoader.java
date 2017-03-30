package com.prinjsystems.grengine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.prinjsystems.grengine.virtual.Materialization;

public final class MaterializationLoader {
	
	public Materialization loadMaterialization(String path, String identifier) {
		File file = new File(path);
		
		String code = "";
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = "";
			
			Map<String, String> codes = new HashMap<>();
			String currentCode = "";
			
			while((line = br.readLine()) != null) {
				if(!line.equals("ident .*")) {
					currentCode += line + "\n";
				} else {
					codes.put(line.split(" ")[1], currentCode);
					currentCode = "";
				}
			}
			
			code = codes.get(identifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Materialization(code);
	}
}

package com.synectiks.asset.controller.testservicedata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ServiceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String associatedOU;
	public String associatedDept;
	public String associatedProduct;
	public String associatedEnv;
    private String serviceType;
	private String serviceHostingType;
	private String serviceNature;
	private String associatedCommonService;
	private String associatedBusinessService;
	private String serviceName;
	private String description;
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

		public String getAssociatedBusinessService() {
		return associatedBusinessService;
	}

	public void setAssociatedBusinessService(String associatedBusinessService) {
		this.associatedBusinessService = associatedBusinessService;
	}

		public String getAssociatedCommonService() {
		return associatedCommonService;
	}

	public void setAssociatedCommonService(String associatedCommonService) {
		this.associatedCommonService = associatedCommonService;
	}

	public String getServiceNature() {
		return serviceNature;
	}

	public void setServiceNature(String serviceNature) {
		this.serviceNature = serviceNature;
	}
		public String getServiceHostingType() {
		return serviceHostingType;
	}

	public void setServiceHostingType(String serviceHostingType) {
		this.serviceHostingType = serviceHostingType;
	}

    public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	

	public String getAssociatedOU() {
		return associatedOU;
	}

	public void setAssociatedOU(String associatedOU) {
		this.associatedOU = associatedOU;
	}

	public String getAssociatedDept() {
		return associatedDept;
	}

	public void setAssociatedDept(String associatedDept) {
		this.associatedDept = associatedDept;
	}

	public String getAssociatedProduct() {
		return associatedProduct;
	}

	public void setAssociatedProduct(String associatedProduct) {
		this.associatedProduct = associatedProduct;
	}

	public String getAssociatedEnv() {
		return associatedEnv;
	}

	public void setAssociatedEnv(String associatedEnv) {
		this.associatedEnv = associatedEnv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getHostingType(String jsonFile) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			Object obj = parser.parse(new FileReader(jsonFile));
            jsonObject =  (JSONObject) obj;
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			e.printStackTrace();
		}
		
		return (String)jsonObject.get("serviceHostingType");
	}

	public static String getEnv(String jsonFile) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			Object obj = parser.parse(new FileReader(jsonFile));
            jsonObject =  (JSONObject) obj;
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
			e.printStackTrace();
		}
		return (String)jsonObject.get("associatedEnv");
	}

	// public static String getHostingTypes(File jsonFile) {
	// 	JSONParser parser = new JSONParser();
	// 	JSONObject jsonObject = null;
	// 	try {
	// 		Object obj = parser.parse(jsonFile.toString());
    //         jsonObject =  (JSONObject) obj;
	// 	}catch (ParseException e) {
	// 		e.printStackTrace();
	// 	}
		
	// 	return (String)jsonObject.get("serviceHostingType");
	// }
	
	protected ServiceDto readJson(String json) {	
		return null;
	}
	
	protected void save() throws JsonParseException, JsonMappingException, IOException {	
		
	}
	public static List<File> listFilesUsingFileWalkAndVisitor(String dir) throws IOException {
		Set<String> fileList = new HashSet<>();
		List<File> fl = new ArrayList<>();
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				if (!Files.isDirectory(file)) {
					fileList.add(file.getFileName().toString());
					fl.add(file.toFile());
				}
				return FileVisitResult.CONTINUE;
			}
		});
		
		return fl;
	}
	
	public static ServiceDto instantiate(String hostingType) {
		switch (hostingType) {
	        case "ClusterManaged":
	            return new ClusterServiceDto();
	        case "CloudManaged":
	            return new CloudServiceDto();
	        case "GlobalManaged":
	            return new GlobalServiceDto();
	        default:
	            throw new IllegalArgumentException("Unknown hosting type: "+hostingType);
        }
	}
}

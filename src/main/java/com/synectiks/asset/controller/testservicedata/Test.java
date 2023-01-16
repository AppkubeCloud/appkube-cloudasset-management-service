package com.synectiks.asset.controller.testservicedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

public class Test{


	public static String IP = "localhost";
	public static String PORT = "5057";
	public static String BASE_URL = "http://"+IP+":"+PORT+"/api";
  
	public static String jsonfile;
	//calling api 
	public static void testGetApi(File jsonPath) {


			// // String jsonFilePath = obj.get("jsonFilePath");
			// ServiceDto dto = ServiceDto.instantiate(ServiceDto.getHostingType(jsonPath.getAbsolutePath()));
			// dto.readJson(jsonFilePath);
			// dto.toString();
			// //dto.save();
	

		String API_END_POINT = BASE_URL + "/create-test-sd-data?jsonFilePath="+jsonPath;
		HttpURLConnection conn = null;
	  	try {
			  	URL url = new URL(API_END_POINT);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
					
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
		  }catch (MalformedURLException e) {
			  e.printStackTrace();
		  }catch (IOException e) {
			  e.printStackTrace();
		  }finally {
			 if(conn != null) {
				conn.disconnect();
			 }
		  }
	
		}
		//getting files from Dir
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


	public static String loadFile(final String pathResource) {
		try (final InputStream is = new ClassPathResource(pathResource).getInputStream()) {
		 // To be customized
		 return IOUtils.toString(is, "UTF-8");
		} catch (final Exception e) {
		 final String errorMessage = "Error loading file ";
		 // Could be more specific
		 throw new RuntimeException(errorMessage + e);
		}
	   }
	
	   public static String readResource(final String pathResource) {
		try (final InputStream is = new ClassPathResource(pathResource).getInputStream()) {
		 return IOUtils.toString(is, "UTF-8");
		} catch (final IOException e) {
		 throw new UncheckedIOException(e);
		}
	   }
	
	   public String readResourceParallel(final String pathResource) {
		try (final InputStream is = new ClassPathResource(pathResource).getInputStream()) {
		 return new BufferedReader(new InputStreamReader(is)).lines()
		  .parallel().collect(Collectors.joining("\n"));
		} catch (final IOException e) {
		 throw new UncheckedIOException(e);
		}
	   }
	public static void main(String[] args) throws IOException {

		// URL url=new URL("https://github.com/AppkubeCloud/json-data/tree/main/apkube-data"); 
		String string =  readResource("https://github.com/AppkubeCloud/json-data/tree/main/apkube-data");
		// List<File> fil =listFilesUsingFileWalkAndVisitor(url.getFile());
		// for(File file : fil){

		// 	System.out.println(file);
		// 	testGetApi(file);
		// }
		System.out.println(string);

	}

	}

	// public static void main(String[] args){    
	// 	try{    
	// 	URL url=new URL("https://github.com/AppkubeCloud/json-data/tree/main/apkube-data");    
	// 	System.out.println("Protocol: "+url.getProtocol());// Using getProtocol() method of the URL class  
	// 	System.out.println("Host Name: "+url.getHost()); // Using getHost() method   
	// 	System.out.println("Port Number: "+url.getPort());  // Using getPort() method  
	// 	System.out.println("File Name: "+url.getFile());    //Using getFile() method  
	// 	}  
	// 	catch(Exception e)  
	// 	{  
	// 		System.out.println(e);}    
	// 	}    
	// 	}  
	

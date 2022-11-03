package com.synectiks.asset.controller.testservicedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	public static void main(String[] args) throws IOException {

		List<File> fil =listFilesUsingFileWalkAndVisitor("/opt/mycode/umran/mycodes/apkube-data");
		for(File file : fil){
			testGetApi(file);
		}
	}
	
}

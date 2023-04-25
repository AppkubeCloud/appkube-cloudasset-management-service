package com.synectiks.asset.controller.testservicedata;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class cmdb {


    private static final Logger logger = LoggerFactory.getLogger(cmdb.class);

    


    public static List<String> getLastCommitFiles(String url) {
        // String url = String.format("https://api.github.com/repos/%s/%s/commits?access_token=%s", owner, repo, accessToken);
        String commitSha = null;
        try {
            // Send a GET request to the GitHub API to retrieve the most recent commit
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: " + responseCode);
                return null;
            }
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();
            JSONArray commits = new JSONArray(response);
            if (commits.length() == 0) {
                System.out.println("No commits found.");
                return null;
            }
            // Get the SHA of the most recent commit
            JSONObject commit = commits.getJSONObject(0);
            commitSha = commit.getString("sha");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // Use the commit SHA to retrieve the files in the commit
        List<String> filesNames = new ArrayList<>();
    

       String surl = String.format(url+"/"+commitSha);
        for(int i = 1; i < 10;i++){
            List<String> matchingfiles =getFilesNames(surl+"?page="+i);

            for(String ss:matchingfiles){
                filesNames.add(ss);
            }
        }
        System.out.println("total files:"+filesNames.size());

        
        return filesNames;

    }

    public static List<String> getFilesNames(String url){

        List<String> files = new ArrayList<>();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Accept","application/vnd.github.VERSION.raw");
            connection.addRequestProperty("Accept", "application/vnd.github.raw");
            connection.addRequestProperty("Accept", "application/vnd.github.object");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: " + responseCode);
                return null;
            }
            String response = "";
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
                // System.out.println(response);
            }
            scanner.close();
            JSONObject commit = new JSONObject(response);
            JSONArray filesArray = commit.getJSONArray("files");
            // System.out.println("total files:"+filesArray.length());
            for (int i = 0; i < filesArray.length(); i++) {
                JSONObject file = filesArray.getJSONObject(i);
                files.add(file.getString("filename"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return files;

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


    public static List<File> cloneAndDeleteRepository(String cloneUrl) {

        List<File> l1 = new ArrayList<>();
        List<File> matchingFiles = new ArrayList<>();


        try {
            // Create a File object for the clone directory
            String clonePath = "/home/umran/apkube-data-dd";
            File cloneDirectory = new File(clonePath);
            l1 = listFilesUsingFileWalkAndVisitor(clonePath);
            System.out.println(l1.size());
            // matchingFiles = findMatchingFiles(clonePath,matchingp);
            deleteDirectory(cloneDirectory);
            
            
            // Run the git clone command
            
            Process process = Runtime.getRuntime().exec("git clone " + cloneUrl + " " + clonePath);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.");
                
                
                // l1 = listFilesUsingFileWalkAndVisitor(clonePath+"/apkube-data");
                
                // Recursively delete the clone directory and all its contents
      
               

            } else {
                System.out.println("Error cloning repository.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return l1;

    }

    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            // Delete all the files in the directory
            for (File file : directory.listFiles()) {
                deleteDirectory(file);
                file.delete();
            }
        }
        // Delete the directory itself
        // directory.delete();
    }



	public static void gitToCmdb( Map<String, String> obj) throws JsonParseException, JsonMappingException, IOException{
		logger.info("Request to create service detail test data");
		String jsonFilePath = obj.get("jsonFilePath");
		List<String> filess = cmdb.getLastCommitFiles("https://api.github.com/repos/umranBatuwah/Demo_Repository/commits");
		List<File> l1 = new ArrayList<>();

		try {
            // Create a File object for the clone directory
            String clonePath = "/home/umran/apkube-data-dd/";
            File cloneDirectory = new File(clonePath);
            l1 = cmdb.listFilesUsingFileWalkAndVisitor(clonePath);
            // matchingFiles = findMatchingFiles(clonePath,matchingp);
            
            
            deleteDirectory(cloneDirectory);
            // Run the git clone command
            
            Process process = Runtime.getRuntime().exec("git clone " + jsonFilePath + " " + clonePath);
            int exitCode = process.waitFor();
            for(String file : filess){
                ServiceDto dto = ServiceDto.instantiate(ServiceDto.getHostingType(clonePath+"/"+file.toString()));
                dto.readJson(clonePath+"/"+file.toString());
                dto.toString();
                dto.save();
            }
            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.");
                
                
                
            } else {
                System.out.println("Error cloning repository.");
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }

		
	
	}
	


    

}

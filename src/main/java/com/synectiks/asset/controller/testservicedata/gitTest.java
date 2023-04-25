package com.synectiks.asset.controller.testservicedata;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;

import com.amazonaws.services.budgets.model.TimeUnit;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import liquibase.pro.packaged.fl;
import liquibase.pro.packaged.ls;
import liquibase.pro.packaged.s;


public class gitTest {

        
     
        public static String gitRequest(String Url) throws IOException, org.json.simple.parser.ParseException {
            URL url = new URL(Url);
            List<String> lines = Lists.newArrayList();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Accept", "application/vnd.github.VERSION.raw");
            conn.addRequestProperty("Accept", "application/vnd.github.raw");
            conn.addRequestProperty("Accept", "application/vnd.github.object");
            conn.addRequestProperty("Authorization", "Bearer " + "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");

    
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            sc.close();

            conn.disconnect();

            return sb.toString();
        }
        


                        public static List<String> getFilesInDirectory(String owner,String repo,String path) throws IOException, JSONException {
                            List<String> filePaths = new ArrayList<String>();
                             String apiUrl = String.format("https://api.github.com/repos/%s/%s/contents/%s", owner, repo, path);
                            URL url = new URL(apiUrl);
                            List lss = new ArrayList<>();
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.addRequestProperty("Accept", "application/vnd.github.VERSION.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.object");
                        
                            if (conn.getResponseCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                            }
                        
                            Scanner sc = new Scanner(new InputStreamReader((conn.getInputStream())));
                            StringBuilder response = new StringBuilder();
                            while (sc.hasNextLine()) {
                                response.append(sc.nextLine());
                                lss.add(response.toString());
                            }
                            sc.close();
                            // JSONObject js = new JSONObject(response.toString());
                            for (int i = 0; i < lss.size(); i++) {
                                Object jsonObject = lss.get(i);

                                JSONObject jsn = new JSONObject();
                                jsn.put("data",jsonObject);
                                JSONObject jss = new JSONObject(jsn.getString("data"));
                                System.out.println(jss);
                                

                                if (jss.getString("type").equals("file")) {
                                    filePaths.add(jss.getString("path"));
                                
                                } else if (jss.getString("type").equals("dir")) {
                                    List<String> subDirectoryFiles = getFilesInDirectory(owner,repo,jss.getString("path"));
                                    System.out.println(subDirectoryFiles);
                                    filePaths.addAll(subDirectoryFiles);
                                }
                            
                            }
                            System.out.println(filePaths);
                        
                            conn.disconnect();
                            return filePaths;

                        }

                        public static String gitRequestIntree(String Url) throws IOException, org.json.simple.parser.ParseException {
                            URL url = new URL(Url);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.addRequestProperty("Accept","application/vnd.github.VERSION.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.object");
                            conn.addRequestProperty("Authorization", "Bearer " + "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");
                         
                            if (conn.getResponseCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                            }
                        
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String output;
                            JSONObject directoryJson = null;
                        
                            while ((output = br.readLine()) != null) {
                                directoryJson = new JSONObject(output);
                            }
                            JSONArray trees = directoryJson.getJSONArray("tree");
                            // System.out.println(trees);
                            List<String> fileUrls = new ArrayList<>();
                            List<String> dirUrls = new ArrayList<>();
                            for (int i = 0; i < trees.length(); i++) {
                                JSONObject entry = trees.getJSONObject(i);
                                if(entry.get("type").equals("tree")){
                                    dirUrls.add(entry.getString("url"));
                                }else if(entry.get("type").equals("blob")){
                                fileUrls.add(entry.getString("url"));   
                                }
                             }
                        
                            JSONArray data = new JSONArray();
                            for (String fileUrl : dirUrls) {
                                String fileContent = gitRequestIntree(fileUrl);
                                
                            }


                                // JSONArray data = new JSONArray();
                                for (String fileUrl : fileUrls) {
                                    // System.out.println(fileUrl);
                                    String fileContent = gitRequest(fileUrl);
                                    JSONObject fileJson = new JSONObject(fileContent);
                                   System.out.println(fileJson);
                                    data.put(fileJson);
                                }
                        
                            return ".";
                        }


                        public static JSONArray gitRequestIntrees(String Url) throws IOException, org.json.simple.parser.ParseException {
                            URL url = new URL(Url);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.addRequestProperty("Accept","application/vnd.github.VERSION.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.object");
                            conn.addRequestProperty("Authorization", "Bearer " + "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");
                        
                            if (conn.getResponseCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                            }
                        
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String output;
                            JSONObject directoryJson = null;
                        
                            while ((output = br.readLine()) != null) {
                                directoryJson = new JSONObject(output);
                            }
                            JSONArray trees = directoryJson.getJSONArray("tree");
                            List<String> fileUrls = new ArrayList<>();
                            List<String> dirUrls = new ArrayList<>();
                            for (int i = 0; i < trees.length(); i++) {
                                JSONObject entry = trees.getJSONObject(i);
                                if(entry.get("type").equals("tree")){
                                    dirUrls.add(entry.getString("url"));
                                }else if(entry.get("type").equals("blob")){
                                    fileUrls.add(entry.getString("url"));   
                                }
                            }
                        
                            JSONArray files = new JSONArray();
                            for (String fileUrl : fileUrls) {
                                String fileContent = gitRequest(fileUrl);
                                JSONObject fileJson = new JSONObject(fileContent);
                                files.put(fileJson);
                                System.out.println(files);
                            }

                            
                        
                            for (String dirUrl : dirUrls) {
                                JSONArray subFiles = gitRequestIntrees(dirUrl);
                                for (int i = 0; i < subFiles.length(); i++) {
                                    JSONObject subFile = subFiles.getJSONObject(i);
                                    files.put(subFile);
                                }
                            }
                        
                            return files;
                        }

                        public static JSONArray gitRequestIntreesm(String url) throws IOException, org.json.simple.parser.ParseException, ExecutionException, InterruptedException {
                            URL requestUrl = new URL(url);
                            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
                            conn.setRequestMethod("GET");
                            conn.addRequestProperty("Accept","application/vnd.github.VERSION.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.raw");
                            conn.addRequestProperty("Accept", "application/vnd.github.object");
                            // conn.addRequestProperty("Authorization", "Bearer" + "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");
                        
                            if (conn.getResponseCode() != 200) {
                                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                            }
                        
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String output;
                            JSONObject directoryJson = null;
                        
                            while ((output = br.readLine()) != null) {
                                directoryJson = new JSONObject(output);
                            }
                        
                            JSONArray trees = directoryJson.getJSONArray("tree");
                            List<String> fileUrls = new ArrayList<>();
                            List<String> dirUrls = new ArrayList<>();
                            for (int i = 0; i < trees.length(); i++) {
                                JSONObject entry = trees.getJSONObject(i);
                                if(entry.get("type").equals("tree")){
                                    dirUrls.add(entry.getString("url"));
                                }else if(entry.get("type").equals("blob")){
                                    fileUrls.add(entry.getString("url"));
                                }
                            }
                        
                            JSONArray files = new JSONArray();
                            for (String fileUrl : fileUrls) {
                                String fileContent = gitRequest(fileUrl);
                                System.out.println(fileContent);
                                JSONObject fileJson = new JSONObject(fileContent);
                                files.put(fileJson);
                            }
                        
                            // Parallelize subdirectory processing using ExecutorService
                            ExecutorService executorService = Executors.newCachedThreadPool();
                            List<Future<JSONArray>> subDirectories = new ArrayList<>();
                            for (String dirUrl : dirUrls) {
                                Callable<JSONArray> task = () -> gitRequestIntreesm(dirUrl);
                                Future<JSONArray> subDirResult = executorService.submit(task);
                                subDirectories.add(subDirResult);
                            }
                        
                            for (Future<JSONArray> subDirResult : subDirectories) {
                                JSONArray subFiles = subDirResult.get();
                                for (int i = 0; i < subFiles.length(); i++) {
                                    JSONObject subFile = subFiles.getJSONObject(i);
                                    files.put(subFile);
                                }
                            }
                        
                            executorService.shutdown();
                            return files;
                        }
                        
                        
        
    
                     
    
        public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException, InterruptedException, ExecutionException {
            JSONArray s1 = gitRequestIntreesm("https://api.github.com/repos/AppkubeCloud/json-data/git/trees/8172deb010460820e83c7320b94e83299f772bba");
            System.out.println(s1.length());
            
    
            // List<String> filePaths = getFilesInDirectory("AppkubeCloud","json-data","apkube-data");
    
        //  JSONObject s1 = getPath("https://api.github.com/repos/AppkubeCloud/json-data/contents/apkube-data");
        // System.out.println(s1);
    
        
             //   List<String> s1 =  downloadFilesFromGitRepo("https://api.github.com/repos/AppkubeCloud/json-data/contents/apkube-data/LOGISTICS/DIGITAL-AUCTION/DEV/COMMON/SitesCentral/GL/DATA");
        //     System.out.println(s1);
    
        // readFileFromGithub("https://github.com/AppkubeCloud/json-data/blob/main/apkube-data/LOGISTICS/DIGITAL-AUCTION/DEV/COMMON/SitesCentral/GL/DATA/LOGISTICS-DIGITAL-AUCTION-DEV-COMMON-SitesCentral-GL-DATA-SitesCentralData2.json");
    
            // try {
            //     List<String> files = getFilesInDirectory("https://api.github.com/repos/AppkubeCloud/json-data/contents/apkube-data/LOGISTICS/DIGITAL-AUCTION/DEV/COMMON/SitesCentral/GL/DATA");
            //     System.out.println(files);
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
        
         
    }
    }
   


    


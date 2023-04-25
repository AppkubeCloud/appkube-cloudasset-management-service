package com.synectiks.asset.controller.testservicedata;

import java.io.IOException;
import java.util.List;


import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class GitData {



    public static List<File> cloneAndDeleteRepository(String cloneUrl) {

        List<File> l1 = new ArrayList<>();

        try {
            // Create a File object for the clone directory
            String clonePath = "/home/umran/apkube-data-path";
            File cloneDirectory = new File(clonePath);
            deleteDirectory(cloneDirectory);
        

            // Run the git clone command
            
            Process process = Runtime.getRuntime().exec("git clone " + cloneUrl + " " + clonePath);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Repository cloned successfully.");

                
                l1 = listFilesUsingFileWalkAndVisitor(clonePath+"/apkube-data");

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
            }
        }
        // Delete the directory itself
        directory.delete();
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

            public static void main(String[] args) throws IOException {

                String cloneUrl = "https://github.com/AppkubeCloud/json-data.git";
                String clonePath = "/home/umran/apkube-data-path";
                

                

                // Process process = Runtime.getRuntime().exec("git clone " + cloneUrl + " " + clonePath);

              List<File> f1 =  cloneAndDeleteRepository(cloneUrl);
              System.out.println(f1.size());
            }


}

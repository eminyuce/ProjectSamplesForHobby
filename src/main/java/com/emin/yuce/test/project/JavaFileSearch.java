package com.emin.yuce.test.project;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class JavaFileSearch {

    public  void test() {
        String directoryPath = "C:\\Users\\eminy\\IdeaProjects\\my-java-development"; // Replace this with your directory path
        String searchString = "calculateApparentPower"; // The string you want to search for
        searchString(directoryPath, searchString);
        System.out.println("Search is DONE");
        System.exit(1);
    }


    public void searchString(String directoryPath, String searchString) {
        List<File> javaFiles = findJavaFiles(new File(directoryPath));

        for (File file : javaFiles) {
            try {
                List<Integer> lineNumbers = containsStringIndexLine(file.toPath(), searchString);
                if (CollectionUtils.isNotEmpty(lineNumbers)) {
                    System.out.println("Found '" + searchString + "' in file: " + file.getAbsolutePath() + " LineNumbers:" + lineNumbers);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<File> findJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    javaFiles.addAll(findJavaFiles_v2(file));
                } else if (file.isFile() && file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
        return javaFiles;
    }

    private List<File> findJavaFiles_v2(File directory) {
        List<File> javaFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    javaFiles.addAll(findJavaFiles(file));
                } else if (file.isFile() && file.getName().endsWith(".java")) {
                    javaFiles.add(file);
                }
            }
        }
        return javaFiles;
    }

    private List<Integer> containsStringIndexLine(Path file, String searchString) throws IOException {
        List<Integer> resultLineNumber = new ArrayList<Integer>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toFile()))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.contains(searchString)) {
                    resultLineNumber.add(lineNumber);
                }
            }
        }
        return resultLineNumber;
    }
}

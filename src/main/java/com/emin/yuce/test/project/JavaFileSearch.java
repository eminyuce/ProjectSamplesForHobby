package com.emin.yuce.test.project;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JavaFileSearch {

    public void search(String directoryPath, String searchString) {
        List<Path> javaFiles = findJavaFiles(Paths.get(directoryPath));
        searchFiles(javaFiles, searchString);
    }

    private List<Path> findJavaFiles(Path directory) {
        try {
            return Files.walk(directory)
                  .filter(path -> path.toString().endsWith(".java"))
                  .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error finding Java files", e);
        }
    }

    private void searchFiles(List<Path> files, String searchString) {
        files.forEach(file -> {
            try {
                List<Integer> lineNumbers = findStringInFile(file, searchString);
                if (CollectionUtils.isNotEmpty(lineNumbers)) {
                    System.out.println("Found '" + searchString + "' in file: " + file + " LineNumbers: " + lineNumbers);
                }
            } catch (IOException e) {
                System.err.println("Error searching file: " + file + " - " + e.getMessage());
            }
        });
    }

    private List<Integer> findStringInFile(Path file, String searchString) throws IOException {
        List<Integer> result = new ArrayList<>();
        try (LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(file))) {
            String line;
            while ((line = reader.readLine())!= null) {
                if (line.contains(searchString)) {
                    result.add(reader.getLineNumber());
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        JavaFileSearch search = new JavaFileSearch();
        search.search("C:\\Users\\eminy\\IdeaProjects\\my-java-development", "calculateApparentPower");
    }
}

package com.prakarshgit.commands;

import picocli.CommandLine.Command;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

@Command(name = "diff", description = "Show differences between working directory and last commit")
public class DiffCommand implements Runnable {

    @Override
    public void run() {
        try {
            File headFile = new File(".vit/HEAD");

            if (!headFile.exists()) {
                System.out.println("No commits yet.");
                return;
            }

            String headHash = Files.readString(headFile.toPath()).trim();

            File commitFile = new File(".vit/objects/" + headHash);
            String content = Files.readString(commitFile.toPath());

            Map<String, String> fileMap = parseCommit(content);

            for (Map.Entry<String, String> entry : fileMap.entrySet()) {
                String filePath = entry.getKey();
                String blobHash = entry.getValue();

                File workingFile = new File(filePath);

                if (!workingFile.exists()) {
                    System.out.println("Deleted: " + filePath);
                    continue;
                }

                byte[] workingBytes = Files.readAllBytes(workingFile.toPath());
                byte[] committedBytes = Files.readAllBytes(
                        new File(".vit/objects/" + blobHash).toPath()
                );

                if (!Arrays.equals(workingBytes, committedBytes)) {
                    System.out.println("diff -- " + filePath);
                    printDiff(
                            new String(committedBytes),
                            new String(workingBytes)
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseCommit(String content) {
        Map<String, String> map = new HashMap<>();

        String[] lines = content.split("\n");

        for (String line : lines) {
            if (line.startsWith("message:") ||
                    line.startsWith("parent:") ||
                    line.startsWith("timestamp:")) {
                continue;
            }

            if (line.contains(":")) {
                String[] parts = line.split(":", 2);
                map.put(parts[0], parts[1]);
            }
        }

        return map;
    }

    private void printDiff(String oldText, String newText) {
        List<String> oldLines = Arrays.asList(oldText.split("\n"));
        List<String> newLines = Arrays.asList(newText.split("\n"));

        int max = Math.max(oldLines.size(), newLines.size());

        for (int i = 0; i < max; i++) {
            String oldLine = i < oldLines.size() ? oldLines.get(i) : "";
            String newLine = i < newLines.size() ? newLines.get(i) : "";

            if (!oldLine.equals(newLine)) {
                if (!oldLine.isEmpty()) {
                    System.out.println("- " + oldLine);
                }
                if (!newLine.isEmpty()) {
                    System.out.println("+ " + newLine);
                }
            }
        }
    }
}
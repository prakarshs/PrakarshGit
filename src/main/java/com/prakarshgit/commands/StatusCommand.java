package com.prakarshgit.commands;

import com.prakarshgit.core.HashUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

@Command(name = "status", description = "Show repository status")
public class StatusCommand implements Runnable {

    @Override
    public void run() {
        try {
            Map<String, String> indexMap = readIndex();
            Map<String, String> headMap = readHeadCommit();

            Set<String> workingFiles = listWorkingFiles();

            System.out.println("Changes to be committed:");
            for (String file : indexMap.keySet()) {
                String indexHash = indexMap.get(file);
                String headHash = headMap.get(file);

                if (headHash == null || !indexHash.equals(headHash)) {
                    System.out.println("    modified: " + file);
                }
            }

            System.out.println("\nChanges not staged for commit:");
            for (String file : indexMap.keySet()) {
                File f = new File(file);
                if (!f.exists()) continue;

                byte[] content = Files.readAllBytes(f.toPath());
                String workingHash = HashUtil.sha1(content);

                if (!workingHash.equals(indexMap.get(file))) {
                    System.out.println("    modified: " + file);
                }
            }

            System.out.println("\nUntracked files:");
            for (String file : workingFiles) {
                if (!indexMap.containsKey(file)) {
                    System.out.println("    " + file);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> readIndex() throws Exception {
        Map<String, String> map = new HashMap<>();

        File indexFile = new File(".vit/index");
        if (!indexFile.exists()) return map;

        List<String> lines = Files.readAllLines(indexFile.toPath());

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(":", 2);
            map.put(parts[0], parts[1]);
        }

        return map;
    }

    private Map<String, String> readHeadCommit() throws Exception {
        Map<String, String> map = new HashMap<>();

        File headFile = new File(".vit/HEAD");
        if (!headFile.exists()) return map;

        String hash = Files.readString(headFile.toPath()).trim();
        if (hash.isEmpty()) return map;

        File commitFile = new File(".vit/objects/" + hash);
        String content = Files.readString(commitFile.toPath());

        String[] lines = content.split("\n");

        for (String line : lines) {
            if (line.startsWith("message:") ||
                    line.startsWith("parent:") ||
                    line.startsWith("timestamp:")) continue;

            if (line.contains(":")) {
                String[] parts = line.split(":", 2);
                map.put(parts[0], parts[1]);
            }
        }

        return map;
    }

    private Set<String> listWorkingFiles() {
        Set<String> files = new HashSet<>();

        File currentDir = new File(".");

        for (File file : currentDir.listFiles()) {
            if (file.isFile()) {
                String path = file.getPath().replace("\\", "/");

                if (path.startsWith("./")) {
                    path = path.substring(2);
                }

                if (!path.startsWith(".vit")) {
                    files.add(path);
                }
            }
        }

        return files;
    }
}
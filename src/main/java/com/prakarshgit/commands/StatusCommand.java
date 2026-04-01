package com.prakarshgit.commands;

import com.prakarshgit.core.HashUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

@Command(name = "status", description = "Show status")
public class StatusCommand implements Runnable {

    @Override
    public void run() {
        try {
            Map<String, String> indexMap = readIndex();

            List<String> workingFiles = new ArrayList<>();
            scanFiles(new File("."), workingFiles);

            System.out.println("=== Vit Status ===");

            List<String> untracked = new ArrayList<>();
            List<String> modified = new ArrayList<>();
            List<String> staged = new ArrayList<>();

            for (String filePath : workingFiles) {

                File file = new File(filePath);
                byte[] content = Files.readAllBytes(file.toPath());
                String currentHash = HashUtil.sha1(content);

                if (!indexMap.containsKey(filePath)) {
                    untracked.add(filePath);
                } else if (!indexMap.get(filePath).equals(currentHash)) {
                    modified.add(filePath);
                } else {
                    staged.add(filePath);
                }
            }

            printSection("Untracked Files", untracked);
            printSection("Modified Files", modified);
            printSection("Staged Files", staged);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // Helpers
    // =========================

    private Map<String, String> readIndex() throws Exception {
        Map<String, String> map = new HashMap<>();

        File indexFile = new File(".vit/index");
        if (!indexFile.exists()) return map;

        List<String> lines = Files.readAllLines(indexFile.toPath());

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                map.put(parts[0], parts[1]);
            }
        }

        return map;
    }

    private static final Set<String> IGNORE_DIRS = Set.of(
            ".vit",
            ".git",
            ".idea",
            "target",
            "node_modules"
    );

    private void scanFiles(File dir, List<String> files) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {

            if (IGNORE_DIRS.contains(file.getName())) continue;

            if (file.isDirectory()) {
                scanFiles(file, files);
            } else {
                String path = file.getPath().replace("\\", "/");

                if (path.startsWith("./")) {
                    path = path.substring(2);
                }

                files.add(path);
            }
        }
    }

    private void printSection(String title, List<String> files) {
        if (files.isEmpty()) return;

        System.out.println("\n" + title + ":");
        for (String f : files) {
            System.out.println("  " + f);
        }
    }
}
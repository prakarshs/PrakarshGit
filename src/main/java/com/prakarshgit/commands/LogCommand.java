package com.prakarshgit.commands;

import picocli.CommandLine.Command;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Command(name = "log", description = "Show commit history")
public class LogCommand implements Runnable {

    @Override
    public void run() {
        try {
            File headFile = new File(".vit/HEAD");

            if (!headFile.exists()) {
                System.out.println("No commits yet.");
                return;
            }

            String currentHash = Files.readString(headFile.toPath()).trim();

            while (currentHash != null && !currentHash.isEmpty()) {

                File commitFile = new File(".vit/objects/" + currentHash);

                if (!commitFile.exists()) {
                    System.out.println("Corrupted commit: " + currentHash);
                    return;
                }

                String content = Files.readString(commitFile.toPath());

                Map<String, String> parsed = parseCommit(content);

                System.out.println("commit " + currentHash);
                System.out.println("Date: " + parsed.get("timestamp"));
                System.out.println();
                System.out.println("    " + parsed.get("message"));
                System.out.println();

                currentHash = parsed.get("parent");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseCommit(String content) {
        Map<String, String> map = new HashMap<>();

        String[] lines = content.split("\n");

        for (String line : lines) {
            if (line.startsWith("message:")) {
                map.put("message", line.substring(8));
            } else if (line.startsWith("parent:")) {
                map.put("parent", line.substring(7));
            } else if (line.startsWith("timestamp:")) {
                map.put("timestamp", line.substring(10));
            }
        }

        return map;
    }
}
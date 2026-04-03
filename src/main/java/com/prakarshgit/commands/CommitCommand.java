package com.prakarshgit.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.nio.file.Files;

@Command(name = "commit", description = "Commit changes")
public class CommitCommand implements Runnable {

    @Option(names = "-m", required = true)
    private String message;

    @Override
    public void run() {
        try {
            File indexFile = new File(".vit/index");

            if (!indexFile.exists() || Files.readString(indexFile.toPath()).trim().isEmpty()) {
                System.out.println("Nothing to commit.");
                return;
            }

            String indexContent = Files.readString(indexFile.toPath());

            String parent = "";
            File headFile = new File(".vit/HEAD");
            if (headFile.exists()) {
                parent = Files.readString(headFile.toPath()).trim();
            }

            long timestamp = System.currentTimeMillis();

            String commitData =
                    "message:" + message + "\n" +
                            "parent:" + parent + "\n" +
                            "timestamp:" + timestamp + "\n" +
                            indexContent;

            String hash = Integer.toHexString(commitData.hashCode());

            File commitFile = new File(".vit/objects/" + hash);
            Files.writeString(commitFile.toPath(), commitData);

            // update HEAD
            Files.writeString(headFile.toPath(), hash);

            System.out.println("Committed: " + hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
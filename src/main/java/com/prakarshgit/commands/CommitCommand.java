package com.prakarshgit.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;
        import java.nio.file.Files;

@Command(name = "commit", description = "Commit changes")
public class CommitCommand implements Runnable {

    @Option(names = "-m", required = true)
    private String message;

    @Override
    public void run() {
        try {
            String indexContent = Files.readString(new File(".vit/index").toPath());

            String commitData = "message:" + message + "\n" + indexContent;

            String hash = Integer.toHexString(commitData.hashCode());

            File commitFile = new File(".vit/objects/" + hash);
            Files.writeString(commitFile.toPath(), commitData);

            // update HEAD
            Files.writeString(new File(".vit/HEAD").toPath(), hash);

            System.out.println("Committed: " + hash);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
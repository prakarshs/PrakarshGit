package com.prakarshgit.commands;

import com.prakarshgit.core.HashUtil;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.*;
        import java.nio.file.Files;

@Command(name = "add", description = "Add file to staging")
public class AddCommand implements Runnable {

    @Parameters
    private String filePath;

    @Override
    public void run() {
        try {
            File file = new File(filePath);
            String normalizedPath = file.getPath().replace("\\", "/");

            if (normalizedPath.startsWith("./")) {
                normalizedPath = normalizedPath.substring(2);
            }
            byte[] content = Files.readAllBytes(file.toPath());

            String hash = HashUtil.sha1(content);

            // store object
            File objectFile = new File(".vit/objects/" + hash);
            Files.write(objectFile.toPath(), content);

            // update index
            FileWriter fw = new FileWriter(".vit/index", true);
            fw.write(normalizedPath + ":" + hash + "\n");
            fw.close();

            System.out.println("Added " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
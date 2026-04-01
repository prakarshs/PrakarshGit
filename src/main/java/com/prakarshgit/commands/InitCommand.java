package com.prakarshgit.commands;

import picocli.CommandLine.Command;

import java.io.File;


@Command(name = "init", description = "Initialize repository")
public class InitCommand implements Runnable {

    @Override
    public void run() {
        File vitDir = new File(".vit");

        if (vitDir.exists()) {
            System.out.println("Repository already exists");
            return;
        }

        new File(".vit/objects").mkdirs();
        new File(".vit/refs/heads").mkdirs();

        try {
            new File(".vit/HEAD").createNewFile();
            new File(".vit/index").createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Initialized empty Vit repository");
    }
}
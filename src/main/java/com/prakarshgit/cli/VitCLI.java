package com.prakarshgit.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "vit",
        mixinStandardHelpOptions = true,
        subcommands = {
                com.prakarshgit.commands.InitCommand.class,
                com.prakarshgit.commands.AddCommand.class,
                com.prakarshgit.commands.CommitCommand.class
        }
)
public class VitCLI implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new VitCLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Use a subcommand");
    }
}
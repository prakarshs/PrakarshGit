package com.prakarshgit.cli;

import com.prakarshgit.commands.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "vit",
        mixinStandardHelpOptions = true,
        subcommands = {
                InitCommand.class,
                AddCommand.class,
                CommitCommand.class,
                StatusCommand.class
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
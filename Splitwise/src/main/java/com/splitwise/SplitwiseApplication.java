package com.splitwise;

import com.splitwise.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class SplitwiseApplication {
    private static CommandExecutor commandExecutor;
    private static SettleUpCommand settleUpCommand;
    private static RegisterUserCommand registerUserCommand;

    public SplitwiseApplication(
            CommandExecutor commandExecutor,
            SettleUpCommand settleUpCommand
    ) {
        this.commandExecutor = commandExecutor;
        this.settleUpCommand = settleUpCommand;
        this.registerUserCommand = registerUserCommand;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SplitwiseApplication.class, args);

        Scanner scanner = new Scanner(System.in);
        commandExecutor.addCommand(settleUpCommand);
        commandExecutor.addCommand(registerUserCommand);

        while (true) {
            String input = scanner.nextLine();
            commandExecutor.execute(input);
        }
    }

}

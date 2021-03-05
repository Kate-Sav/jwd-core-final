package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.command.Command;
import com.epam.jwd.core_final.command.CommandName;
import com.epam.jwd.core_final.context.impl.CommandProvider;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.UnknownEntityException;

import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();
    NassaContext nassaContext = new NassaContext();

    default void printAvailableOptions() {
        nassaContext.start();
            String userRequest;
            Scanner scanner = new Scanner(System.in);
            String work = "c";
            while (work.equals("c")) {
                try {
                    System.out.println(handleUserInput(CommandName.START.toString()));
                    userRequest = scanner.nextLine();
                    System.out.println(handleUserInput(userRequest));
                    userRequest = userRequest + "_" + scanner.nextLine();
                    System.out.println(handleUserInput(userRequest));
                    System.out.println(handleUserInput(CommandName.CLOSE.toString()));
                    userRequest = scanner.nextLine();
                    work = handleUserInput(CommandName.MENUWORK + ";" + userRequest);
                } catch (UnknownEntityException e) {
                    System.out.println(e.getMessage());
                    System.out.println(handleUserInput(CommandName.CLOSE.toString()));
                    userRequest = scanner.nextLine();
                    work = userRequest;
                }

            }
            nassaContext.interrupt();
    }


    default String handleUserInput(String userRequest) {
        CommandProvider commandProvider = new CommandProvider();
        Command command = commandProvider.getCommand(userRequest);
        return command.execute(userRequest, nassaContext);
    }
}

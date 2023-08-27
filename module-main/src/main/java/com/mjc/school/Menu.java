package com.mjc.school;

import com.mjc.school.controller.Operations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Menu {
    private final OperationHandler operationHandler;

    @Autowired
    public Menu(OperationHandler operationHandler) {
        this.operationHandler = operationHandler;
    }

    public void printMenu(){
        while (true){
            try {
                for (Operations operation: Operations.values()){
                    System.out.println(operation.getOperationWithNumber());
                }
                System.out.println("Enter the number of operation:");
                Scanner scanner = new Scanner(System.in);
                try {
                    int command = scanner.nextInt();
                    if (command == 0) System.exit(0);
                    if (command > 0 && command <= 18){
                        operationHandler.executeOperation(command);
                    }
                } catch (InputMismatchException e){
                    System.out.println("Command not found.");
                }
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class ChatApp {
    public static void run(Scanner scanner) {

        System.out.println();
        System.out.println("Welcome to ChatApp.");

        int maxMessages = readInt(scanner, "How many messages do you want to enter? ");
        boolean messagesEntered = false;
        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");

            int option = readInt(scanner, "Choose an option: ");

            switch (option) {
                case 1:
                    if (messagesEntered) {
                        System.out.println("You have already entered your " + maxMessages + " messages.");
                    } else {
                        sendMessages(scanner, maxMessages);
                        messagesEntered = true;
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Please choose 1, 2 or 3.");
            }
        }
    }
    private static void sendMessages(Scanner scanner, int maxMessages) {

        for (int i = 0; i < maxMessages; i++) {

            System.out.println();
            System.out.println("--- Message " + (i + 1) + " of " + maxMessages + " ---");

            // Recipient
            String recipient;
            while (true) {
                System.out.print("Recipient cell number: ");
                recipient = scanner.nextLine();
                if (LoginDetails.isValidCellNumber(recipient)) {
                    System.out.println("Cell phone number successfully captured.");
                    break;
                }
                System.out.println("Cell phone number is incorrectly formatted or does not "
                        + "contain an international code. Please correct the number and try again.");
                
            } String text;while (true) {
                System.out.print("Message: ");
                text = scanner.nextLine();
                if (text.length() <= 250) {break;
                }
                System.out.println("Please enter a message of less than 250 characters.");
            }

            Message message = new Message(i, recipient, text);

            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            int choice = readInt(scanner, "Choose (1, 2 or 3): ");

            System.out.println(message.SentMessage(choice));

            if (choice == 2) {int answer = readInt(scanner, "Enter 0 to delete the message: ");
               if (answer == 0) {message.deleteDisregarded();
                    System.out.println("Message deleted.");
                }
            } else if (choice == 1) {
                System.out.println(message.details());
            }
        }

        System.out.println();
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
    
}

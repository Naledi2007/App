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
    public static void run(Scanner scanner, String senderName) {
        MessageManager.sender = senderName;
        MessageManager.loadStoredFromJson();

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
            System.out.println("4) Stored Messages");

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
                case 4:
                    storedMessagesMenu(scanner);
                    break;
                default:
                    System.out.println("Please choose 1, 2, 3 or 4.");
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
            
            /** Part 3: sub-menu for the stored messages features. */
    private static void storedMessagesMenu(Scanner scanner) {
        
        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("--- Stored Messages ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display report");
            System.out.println("g) Back to main menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println(MessageManager.displaySenderAndRecipient());
                    break;
                case "b":
                    System.out.println(MessageManager.longestStoredMessage());
                    break;
                case "c": {
                    System.out.print("Enter the message ID: ");
                    Message found = MessageManager.searchByMessageID(scanner.nextLine().trim());
                    if (found == null) {
                        System.out.println("No message found with that ID.");
                    } else {
                        System.out.println("Recipient: " + found.getRecipient());
                        System.out.println("Message: " + found.getMessage());
                    }
                    break;
                }
                case "d": {
                    System.out.print("Enter the recipient number: ");
                    java.util.List<String> results =
                            MessageManager.searchByRecipient(scanner.nextLine().trim());
                    if (results.isEmpty()) {
                        System.out.println("No messages found for that recipient.");
                    } else {
                        for (String text : results) {
                            System.out.println("- " + text);
                        }
                    }
                    break;
                }
                case "e":
                    System.out.print("Enter the message hash: ");
                    System.out.println(MessageManager.deleteByHash(scanner.nextLine().trim()));
                    break;
                case "f":
                    System.out.println(MessageManager.displayReport());
                    break;
                case "g":
                    back = true;
                    break;
                default:
                    System.out.println("Please choose a letter from a to g.");
            }
        }
    }
}

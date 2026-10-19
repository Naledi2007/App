/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Student
 */

//Unit tests based on:(Emeris School of Computer Science, 2025b; JUnit Team, n.d.).
public class Message {
    public static String storeFile = "messages.json";

private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static int totalSent = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String message;
    private final String messageHash;

    
    public Message(int messageNumber, String recipient, String message) {
        this(generateMessageID(), messageNumber, recipient, message);
    }

 
    public Message(String messageID, int messageNumber, String recipient, String message) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

   
    public static String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

   
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    
    public String checkRecipientCell() {
        if (LoginDetails.isValidCellNumber(recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "+ "international code. Please correct the number and try again.";
    }


    public static String checkMessageLength(String text) {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
    }

   
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last)
                .toUpperCase();
    }

    
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(this);
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                storedMessages.add(this);
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
 
    
    public void deleteDisregarded() {
        disregardedMessages.remove(this);
        
    }
    
    //Reference:(Google, n.d.) Gson User Guide.
    //Available at: https://google.github.io/gson/UserGuide.html (Accessed: 19 October 2026).
    public void storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Message> all = loadStoredMessages();
        all.add(this);
        try (FileWriter writer = new FileWriter(storeFile)) {
            gson.toJson(all, writer);
        } catch (IOException e) {
            System.out.println("Could not save message: " + e.getMessage());
        }
    }

   
    public static List<Message> loadStoredMessages() {
        Type listType = new TypeToken<ArrayList<Message>>() { }.getType();
        try (FileReader reader = new FileReader(storeFile)) {
            List<Message> loaded = new Gson().fromJson(reader, listType);
            return loaded == null ? new ArrayList<>() : loaded;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

   
    public String details() {
        return "Message ID: " + messageID+ "\nMessage Hash: " + messageHash + "\nRecipient: " + recipient+ "\nMessage: " + message;
    }

    
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.details()).append("\n\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalSent;
    }

    
    public static void resetAll() {
        sentMessages.clear();
        storedMessages.clear();
        disregardedMessages.clear();
        totalSent = 0;
    }

    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
}
    


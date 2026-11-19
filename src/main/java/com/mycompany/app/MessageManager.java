/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Student
 */
public class MessageManager {
    
     private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();

    public static String sender = "You";


    public static void addSent(Message m) {
        sentMessages.add(m);
        register(m);
    }

    public static void addDisregarded(Message m) {
        disregardedMessages.add(m);
        register(m);
    }

    public static void addStored(Message m) {
        storedMessages.add(m);
        register(m);
    }

    public static void removeDisregarded(Message m) {
        disregardedMessages.remove(m);
    }

    public static void addByFlag(Message m, String flag) {
        switch (flag.toLowerCase()) {
            case "sent":
                addSent(m);
                break;
            case "stored":
                addStored(m);
                break;
            case "disregard":
            case "disregarded":
                addDisregarded(m);
                break;
            default:
                break;
        }
    }

    private static void register(Message m) {
        if (!messageIDs.contains(m.getMessageID())) {
            messageIDs.add(m.getMessageID());
            messageHashes.add(m.getMessageHash());
        }
    }


    public static List<String> getSentMessageTexts() {
        return texts(sentMessages);
    }

    public static List<String> getDisregardedMessageTexts() {
        return texts(disregardedMessages);
    }

    public static List<String> getStoredMessageTexts() {
        return texts(storedMessages);
    }

    public static List<String> getMessageHashes() {
        return new ArrayList<>(messageHashes);
    }

    public static List<String> getMessageIDs() {
        return new ArrayList<>(messageIDs);
    }

    private static List<String> texts(List<Message> list) {
        List<String> result = new ArrayList<>();
        for (Message m : list) {
            result.add(m.getMessage());
        }
        return result;
    }

    public static String displaySenderAndRecipient() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(sender)
              .append(" | Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    public static String longestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        String longest = storedMessages.get(0).getMessage();
        for (Message m : storedMessages) {
            if (m.getMessage().length() > longest.length()) {
                longest = m.getMessage();
            }
        }
        return longest;
    }

    public static Message searchByMessageID(String id) {
        List<List<Message>> allLists = List.of(sentMessages, storedMessages, disregardedMessages);
        for (List<Message> list : allLists) {
            for (Message m : list) {
                if (m.getMessageID().equals(id)) {
                    return m;
                }
            }
        }
        return null;
    }

    public static List<String> searchByRecipient(String recipient) {
        List<String> found = new ArrayList<>();
        for (Message m : sentMessages) {
            if (m.getRecipient().equals(recipient)) {
                found.add(m.getMessage());
            }
        }
        for (Message m : storedMessages) {
            if (m.getRecipient().equals(recipient)) {
                found.add(m.getMessage());
            }
        }
        return found;
    }
    
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.getMessageHash().equals(hash)) {
                storedMessages.remove(i);
                messageHashes.remove(m.getMessageHash());
                messageIDs.remove(m.getMessageID());
                saveStoredToJson();
                return "Message: \"" + m.getMessage() + "\" successfully deleted.";
            }
        }
        return "No stored message found with that hash.";
    }

    public static String displayReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== SENT MESSAGES =====\n");
        for (Message m : sentMessages) {
            sb.append(m.details()).append("\n\n");
        }
        sb.append("===== STORED MESSAGES =====\n");
        for (Message m : storedMessages) {
            sb.append(m.details()).append("\n\n");
        }
        return sb.toString();
    }

    // ---------- JSON ----------

    /*
     * Reads the stored messages from the JSON file into the stored list.
     * Reference: (Google, no date) Gson User Guide.
     * Available at: https://google.github.io/gson/UserGuide.html (Accessed: 19 October 2026).
     */
    public static void loadStoredFromJson() {
        storedMessages.clear();
        for (Message m : Message.loadStoredMessages()) {
            storedMessages.add(m);
            register(m);
        }
    }

    /*
     * Writes the stored list back to the JSON file (used after a delete).
     * Reference: (Google, no date) Gson User Guide.
     * Available at: https://google.github.io/gson/UserGuide.html (Accessed: 19 October 2026).
     */
    public static void saveStoredToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(Message.storeFile)) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            System.out.println("Could not update the JSON file: " + e.getMessage());
        }
    }

    public static void reset() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
    }
}


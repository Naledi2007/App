/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

/**
 *
 * @author Student
 */
public class MessageManagerTest {
    
    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;

    @BeforeEach
    public void setUp() {
        Message.resetAll();
        Message.storeFile = "test-messages.json";
        new File(Message.storeFile).delete();

        message1 = new Message("1111111111", 0, "+27834557896", "Did you get the cake?");
        message2 = new Message("2222222222", 1, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        message3 = new Message("3333333333", 2, "+27834484567", "Yohoooo, I am at your gate.");
        message4 = new Message("0838884567", 3, "0838884567", "It is dinner time!");
        message5 = new Message("5555555555", 4, "+27838884567", "Ok, I am leaving without you.");

        MessageManager.addByFlag(message1, "Sent");
        MessageManager.addByFlag(message2, "Stored");
        MessageManager.addByFlag(message3, "Disregard");
        MessageManager.addByFlag(message4, "Sent");
        MessageManager.addByFlag(message5, "Stored");
    }

    @Test
    public void testSentMessagesArray() {
        assertEquals(List.of("Did you get the cake?", "It is dinner time!"),
                MessageManager.getSentMessageTexts());
    }

    @Test
    public void testDisregardedMessagesArray() {
        assertEquals(List.of("Yohoooo, I am at your gate."),
                MessageManager.getDisregardedMessageTexts());
    }

    @Test
    public void testStoredMessagesArray() {
        assertEquals(List.of(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."),
                MessageManager.getStoredMessageTexts());
    }

    @Test
    public void testHashAndIdArrays() {
        assertEquals(5, MessageManager.getMessageHashes().size());
        assertEquals(5, MessageManager.getMessageIDs().size());
        assertTrue(MessageManager.getMessageHashes().contains(message2.getMessageHash()));
        assertTrue(MessageManager.getMessageIDs().contains("0838884567"));
    }

    @Test
    public void testLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                MessageManager.longestStoredMessage());
    }

    @Test
    public void testSearchByMessageID() {
        Message found = MessageManager.searchByMessageID("0838884567");
        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessage());
    }

    @Test
    public void testSearchByRecipient() {
        assertEquals(List.of(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."),
                MessageManager.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteByHash() {
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                MessageManager.deleteByHash(message2.getMessageHash()));
        assertEquals(1, MessageManager.getStoredMessageTexts().size());
    }

    @Test
    public void testDeleteByHashNotFound() {
        assertEquals("No stored message found with that hash.",
                MessageManager.deleteByHash("00:0:NOTHING"));
    }

    @Test
    public void testDisplayReport() {
        String report = MessageManager.displayReport();
        assertTrue(report.contains(message1.getMessageHash()));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
    }

    @Test
    public void testJsonSaveAndLoad() {
        MessageManager.saveStoredToJson();
        MessageManager.reset();
        MessageManager.loadStoredFromJson();
        assertEquals(2, MessageManager.getStoredMessageTexts().size());
    }
}
    


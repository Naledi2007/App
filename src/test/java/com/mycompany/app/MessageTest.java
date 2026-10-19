/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Student
 */
public class MessageTest {
 private static final String MSG1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String MSG2 = "Hi Keegan, did you receive the payment?";

    private Message message1;
    private Message message2;

    @BeforeEach
    public void setUp() {
        Message.resetAll();
        Message.storeFile = "test-messages.json";
        message1 = new Message("0012345678", 0, "+27718693002", MSG1);
        message2 = new Message("0187654321", 1, "08575975889", MSG2);
    }

    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", Message.checkMessageLength(MSG1));
    }

    @Test
    public void testMessageLengthFailure() {
        String longMessage = "a".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                Message.checkMessageLength(longMessage));
    }

    @Test
    public void testRecipientCorrectlyFormatted() {
        assertEquals("Cell phone number successfully captured.",
                message1.checkRecipientCell());
    }

    @Test
    public void testRecipientIncorrectlyFormatted() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.",
                message2.checkRecipientCell());
    }

    @Test
    public void testMessageHashTestCase1() {
        assertEquals("00:0:HITONIGHT", message1.createMessageHash());
    }

    @Test
    public void testMessageHashesInLoop() {
        Message[] messages = {message1, message2};
        String[] expected = {"00:0:HITONIGHT", "01:1:HIPAYMENT"};

        for (int i = 0; i < messages.length; i++) {
            assertEquals(expected[i], messages[i].createMessageHash());
        }
    }

    @Test
    public void testMessageIDCreated() {
        Message m = new Message(0, "+27718693002", MSG1);
        assertTrue(m.checkMessageID());
        assertEquals(10, m.getMessageID().length());
        System.out.println("Message ID generated: " + m.getMessageID());
    }

    @Test
    public void testSendMessage() {
        assertEquals("Message successfully sent.", message1.SentMessage(1));
        assertEquals(1, Message.returnTotalMessages());
    }

    @Test
    public void testDisregardMessage() {
        assertEquals("Press 0 to delete the message.", message2.SentMessage(2));
        assertEquals(0, Message.returnTotalMessages());
    }

    @Test
    public void testStoreMessage() {
        assertEquals("Message successfully stored.", message1.SentMessage(3));
    }

    @Test
    public void testTotalMessagesAndPrint() {
        message1.SentMessage(1);
        message2.SentMessage(2);
        assertEquals(1, Message.returnTotalMessages());
        assertTrue(Message.printMessages().contains("00:0:HITONIGHT"));
    }
}
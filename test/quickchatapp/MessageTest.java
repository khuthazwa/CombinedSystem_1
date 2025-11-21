package quickchatapp;

/**
 * MessageTest.java
 * Unit tests for Message class
 */

import org.junit.Before;
import org.junit.jupiter.api.Test;

public class MessageTest {
    
    @Before
    public void setUp() {
        Message.resetCounter();
    }
    
    @Test
    public void testMessageNotMoreThan250Characters() {
        Message msg = new Message();
        msg.setMessageContent("Hi Mike, can you join us for dinner tonight");
        
        String result = msg.checkMessageLength();
        assertEquals("Message ready to send.", result);
    }
    
    @Test
    public void testMessageExceeds250Characters() {
        Message msg = new Message();
        String longMessage = "a".repeat(260);
        msg.setMessageContent(longMessage);
        
        String result = msg.checkMessageLength();
        assertEquals("Message exceeds 250 characters by 10, please reduce size.", result);
    }
    
    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        Message msg = new Message();
        msg.setRecipient("+27718693002");
        
        int result = msg.checkRecipientCell();
        assertEquals("Cell phone number should be valid", 0, result);
    }
    
    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        Message msg = new Message();
        msg.setRecipient("08575975889");
        
        int result = msg.checkRecipientCell();
        assertEquals("Cell phone number should be invalid", -1, result);
    }
    
    @Test
    public void testMessageHashIsCorrect() {
        Message msg = new Message();
        msg.setMessageContent("Hi Mike, can you join us for dinner tonight");
        msg.setRecipient("+27718693002");
        
        String hash = msg.createMessageHash();
        
        // Hash format: XX:Y:FIRSTWORD:LASTWORD
        assertTrue("Hash should contain message number", hash.contains(":1:"));
        assertTrue("Hash should contain first word", hash.contains("HI"));
        assertTrue("Hash should contain last word", hash.contains("TONIGHT"));
    }
    
    @Test
    public void testMessageIDGenerated() {
        Message msg = new Message();
        
        assertTrue("Message ID should be generated", msg.checkMessageID());
        assertEquals("Message ID should be 10 characters", 10, msg.getMessageID().length());
    }
    
    @Test
    public void testUserSelectedSendMessage() {
        Message msg = new Message();
        msg.setMessageContent("Test message");
        msg.setRecipient("+27718693002");
        
        String result = msg.sentMessage(1);
        assertEquals("Message successfully sent.", result);
        assertEquals("Sent", msg.getSendStatus());
    }
    
    @Test
    public void testUserSelectedDisregardMessage() {
        Message msg = new Message();
        msg.setMessageContent("Test message");
        
        String result = msg.sentMessage(2);
        assertEquals("Press 0 to delete message.", result);
        assertEquals("Disregarded", msg.getSendStatus());
    }
    
    @Test
    public void testUserSelectedStoreMessage() {
        Message msg = new Message();
        msg.setMessageContent("Test message");
        msg.setRecipient("+27718693002");
        
        String result = msg.sentMessage(3);
        assertEquals("Message successfully stored.", result);
        assertEquals("Stored", msg.getSendStatus());
    }
    
    @Test
    public void testTotalMessagesCount() {
        Message.resetCounter();
        
        Message msg1 = new Message();
        Message msg2 = new Message();
        Message msg3 = new Message();
        
        assertEquals("Should have 3 messages", 3, Message.returnTotalMessages());
    }
    
    @Test
    public void testMessagePrintFormat() {
        Message msg = new Message();
        msg.setMessageContent("Test message");
        msg.setRecipient("+27718693002");
        msg.createMessageHash();
        msg.sentMessage(1);
        
        String output = msg.printMessage();
        
        assertTrue("Should contain Message ID", output.contains("Message ID:"));
        assertTrue("Should contain Message Hash", output.contains("Message Hash:"));
        assertTrue("Should contain Recipient", output.contains("Recipient:"));
        assertTrue("Should contain Message", output.contains("Message:"));
        assertTrue("Should contain Status", output.contains("Status:"));
    }

    private void assertEquals(String cell_phone_number_should_be_invalid, int i, int result) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertTrue(String hash_should_contain_message_number, boolean contains) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertEquals(String stored, String sendStatus) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
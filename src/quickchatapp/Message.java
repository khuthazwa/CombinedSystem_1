package quickchatapp;


import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Message {
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageContent;
    private String messageHash;
    private String sendStatus; // "Sent", "Disregarded", "Stored"
    
    private static int messageCounter = 0;
    
    // Constructor
    public Message() {
        this.messageID = generateMessageID();
        this.numMessagesSent = ++messageCounter;
        this.recipient = "";
        this.messageContent = "";
        this.messageHash = "";
        this.sendStatus = "";
    }
    
    // Getters
    public String getMessageID() {
        return messageID;
    }
    
    public int getNumMessagesSent() {
        return numMessagesSent;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public String getMessageContent() {
        return messageContent;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public String getSendStatus() {
        return sendStatus;
    }
    
    // Setters
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    public void setSendStatus(String status) {
        this.sendStatus = status;
    }
    
    /**
     * Generates a random 10-digit message ID
     * @return 10-digit message ID as String
     */
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }
    
    /**
     * Checks if message ID is exactly 10 characters
     * @return true if valid, false otherwise
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }
    
    /**
     * Validates recipient cell number format
     * Reference: AI-generated regex pattern using ChatGPT (OpenAI, 2024)
     * OpenAI. (2024). ChatGPT (Nov 12 version) [Large language model]. 
     * https://chat.openai.com
     * 
     * @return 0 if valid, error code otherwise
     */
    public int checkRecipientCell() {
        if (recipient == null) {
            return -1;
        }
        
        // Check if starts with + and has country code + 10 digits
        String regex = "^\\+\\d{1,3}\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(recipient);
        
        if (!matcher.matches()) {
            return -1; // Invalid format
        }
        
        return 0; // Valid
    }
    
    /**
     * Validates message content length (max 250 characters)
     * @return success/error message
     */
    public String checkMessageLength() {
        if (messageContent == null) {
            return "Message exceeds 250 characters by 250, please reduce size.";
        }
        
        if (messageContent.length() > 250) {
            int excess = messageContent.length() - 250;
            return "Message exceeds 250 characters by " + excess + ", please reduce size.";
        }
        
        return "Message ready to send.";
    }
    
    /**
     * Creates message hash from message ID and content
     * Format: FirstTwoDigitsOfID:MessageNum:FIRSTWORD:LASTWORD
     * @return message hash in uppercase
     */
    public String createMessageHash() {
        if (messageContent == null || messageContent.trim().isEmpty()) {
            messageHash = messageID.substring(0, 2) + ":" + numMessagesSent + ":EMPTY:EMPTY";
            return messageHash;
        }
        
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        
        messageHash = messageID.substring(0, 2) + ":" + numMessagesSent + ":" + 
                      firstWord.toUpperCase() + ":" + lastWord.toUpperCase();
        
        return messageHash;
    }
    
    /**
     * Processes send message action based on user choice
     * @param choice 1=Send, 2=Disregard, 3=Store
     * @return status message
     */
    public String sentMessage(int choice) {
        switch (choice) {
            case 1:
                sendStatus = "Sent";
                return "Message successfully sent.";
            case 2:
                sendStatus = "Disregarded";
                return "Press 0 to delete message.";
            case 3:
                sendStatus = "Stored";
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }
    

    public void storeMessage() {
        try {
            JSONArray messagesArray;
            String filename = "stored_messages.json";
            
            // Read existing messages or create new array
            try {
                String content = new String(Files.readAllBytes(Paths.get(filename)));
                messagesArray = new JSONArray(content);
            } catch (IOException e) {
                messagesArray = new JSONArray();
            }
            
            // Create message object
            JSONObject messageObj = new JSONObject();
            messageObj.put("messageID", messageID);
            messageObj.put("numMessagesSent", numMessagesSent);
            messageObj.put("recipient", recipient);
            messageObj.put("messageContent", messageContent);
            messageObj.put("messageHash", messageHash);
            messageObj.put("sendStatus", sendStatus);
            
            // Add to array
            messagesArray.put(messageObj);
            
            // Write to file
            try (FileWriter file = new FileWriter(filename)) {
                file.write(messagesArray.toString(4));
                file.flush();
            }
            
        } catch (Exception e) {
            System.err.println("Error storing message: " + e.getMessage());
        }
    }
    
    /**
     * Returns formatted message details
     * @return formatted string with all message details
     */
    public String printMessage() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageContent + "\n" +
               "Status: " + sendStatus;
    }
    
    /**
     * Returns total number of messages sent
     * @return message counter
     */
    public static int returnTotalMessages() {
        return messageCounter;
    }
    
    /**
     * Resets message counter (useful for testing)
     */
    public static void resetCounter() {
        messageCounter = 0;
    }
}
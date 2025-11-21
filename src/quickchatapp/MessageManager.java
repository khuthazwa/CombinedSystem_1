package quickchatapp;



import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageManager {
    private List<String> sentMessages;
    private List<String> disregardedMessages;
    private List<String> storedMessages;
    private List<String> messageHashes;
    private List<String> messageIDs;
    private List<String> recipients;
    private List<Message> allMessages;
    
    public MessageManager() {
        this.sentMessages = new ArrayList<>();
        this.disregardedMessages = new ArrayList<>();
        this.storedMessages = new ArrayList<>();
        this.messageHashes = new ArrayList<>();
        this.messageIDs = new ArrayList<>();
        this.recipients = new ArrayList<>();
        this.allMessages = new ArrayList<>();
    }
    
  
    public void addMessage(Message message) {
        allMessages.add(message);
        messageIDs.add(message.getMessageID());
        messageHashes.add(message.getMessageHash());
        recipients.add(message.getRecipient());
        
        String status = message.getSendStatus();
        if ("Sent".equals(status)) {
            sentMessages.add(message.getMessageContent());
        } else if ("Disregarded".equals(status)) {
            disregardedMessages.add(message.getMessageContent());
        } else if ("Stored".equals(status)) {
            storedMessages.add(message.getMessageContent());
        }
    }
    
   
    public void loadStoredMessages() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("stored_messages.json")));
                JSONArray messagesArray = new JSONArray(content);
            
            for (int i = 0; i < messagesArray.length(); i++) {
                JSONObject msgObj = messagesArray.getJSONObject(i);
                String messageContent = msgObj.getString("messageContent");
                
                if (!storedMessages.contains(messageContent)) {
                    storedMessages.add(messageContent);
                }
            }
        } catch (IOException e) {
            System.out.println("No stored messages file found or error reading file.");
        } catch (Exception e) {
            System.err.println("Error parsing stored messages: " + e.getMessage());
        }
    }
    
    /**
     * Displays sender and recipient of all sent messages
     * @return formatted string
     */
    public String displaySentMessagesInfo() {
        if (sentMessages.isEmpty()) {
            return "No sent messages available.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Sent Messages Info ===\n\n");
        
        for (int i = 0; i < allMessages.size(); i++) {
            Message msg = allMessages.get(i);
            if ("Sent".equals(msg.getSendStatus())) {
                sb.append("Recipient: ").append(msg.getRecipient())
                  .append("\nMessage: ").append(msg.getMessageContent())
                  .append("\n\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Finds and returns the longest sent message
     * @return longest message content
     */
    public String findLongestMessage() {
        String longest = "";
        
        for (Message msg : allMessages) {
            if ("Sent".equals(msg.getSendStatus()) || "Stored".equals(msg.getSendStatus())) {
                if (msg.getMessageContent().length() > longest.length()) {
                    longest = msg.getMessageContent();
                }
            }
        }
        
        return longest.isEmpty() ? "No messages available." : longest;
    }
    
    /**
     * Searches for a message by ID and returns details
     * @param messageID ID to search for
     * @return formatted message details or error message
     */
    public String searchByMessageID(String messageID) {
        for (Message msg : allMessages) {
            if (msg.getMessageID().equals(messageID)) {
                return "Recipient: " + msg.getRecipient() + "\n" +
                       "Message: " + msg.getMessageContent();
            }
        }
        return "Message ID not found.";
    }
    
    /**
     * Searches for all messages sent to a particular recipient
     * @param recipient recipient number to search
     * @return list of messages
     */
    public List<String> searchByRecipient(String recipient) {
        List<String> messages = new ArrayList<>();
        
        for (Message msg : allMessages) {
            if (msg.getRecipient().equals(recipient)) {
                if ("Sent".equals(msg.getSendStatus()) || "Stored".equals(msg.getSendStatus())) {
                    messages.add(msg.getMessageContent());
                }
            }
        }
        
        return messages;
    }
    
    /**
     * Deletes a message using its hash
     * @param messageHash hash of message to delete
     * @return confirmation message
     */
    public String deleteMessageByHash(String messageHash) {
        for (int i = 0; i < allMessages.size(); i++) {
            Message msg = allMessages.get(i);
            if (msg.getMessageHash().equals(messageHash)) {
                String content = msg.getMessageContent();
                allMessages.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                
                // Remove from appropriate list
                sentMessages.remove(content);
                disregardedMessages.remove(content);
                storedMessages.remove(content);
                
                return "Message \"" + content + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }
    
    /**
     * Generates a full report of all sent messages
     * @return formatted report
     */
    public String displayReport() {
        StringBuilder report = new StringBuilder();
        report.append("=================================================\n");
        report.append("           SENT MESSAGES REPORT\n");
        report.append("=================================================\n\n");
        
        int sentCount = 0;
        for (Message msg : allMessages) {
            if ("Sent".equals(msg.getSendStatus())) {
                sentCount++;
                report.append("Message #").append(sentCount).append("\n");
                report.append("-------------------------------------------------\n");
                report.append("Message Hash: ").append(msg.getMessageHash()).append("\n");
                report.append("Recipient: ").append(msg.getRecipient()).append("\n");
                report.append("Message: ").append(msg.getMessageContent()).append("\n");
                report.append("-------------------------------------------------\n\n");
            }
        }
        
        if (sentCount == 0) {
            report.append("No sent messages to display.\n");
        }
        
        report.append("=================================================\n");
        report.append("Total Sent Messages: ").append(sentCount).append("\n");
        report.append("=================================================\n");
        
        return report.toString();
    }
    
    // Getters for testing
    public List<String> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }
    
    public List<String> getDisregardedMessages() {
        return new ArrayList<>(disregardedMessages);
    }
    
    public List<String> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }
    
    public List<Message> getAllMessages() {
        return new ArrayList<>(allMessages);
    }
    //github link https://github.com/khuthazwa/QuickChatApp.git
}
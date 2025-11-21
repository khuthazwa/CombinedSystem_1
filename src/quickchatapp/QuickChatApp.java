package quickchatapp;

/**
 * QuickChatApp.java
 * Complete Application combining Parts 1, 2, and 3
 * Main entry point for the QuickChat messaging system
 */

import javax.swing.JOptionPane;

public class QuickChatApp {
    
    private static Login currentUser;
    private static MessageManager messageManager;
    
    public static void main(String[] args) {
        messageManager = new MessageManager();
        
        // Part 1: Registration and Login
        if (!registerAndLogin()) {
            return; // Exit if login fails
        }
        
        // Part 2 & 3: Main Application Loop
        runQuickChat();
    }
    
    /**
     * Handles user registration and login process
     * @return true if login successful, false otherwise
     */
    private static boolean registerAndLogin() {
        currentUser = new Login();
        
        // Registration
        JOptionPane.showMessageDialog(null, 
            "Welcome to QuickChat!\nLet's create your account.", 
            "Registration", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // Get registration details
        String firstName = JOptionPane.showInputDialog(null, 
            "Enter your first name:", 
            "Registration", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (firstName == null) return false;
        currentUser.setFirstName(firstName);
        
        String lastName = JOptionPane.showInputDialog(null, 
            "Enter your last name:", 
            "Registration", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (lastName == null) return false;
        currentUser.setLastName(lastName);
        
        // Username registration with validation
        boolean validUsername = false;
        while (!validUsername) {
            String username = JOptionPane.showInputDialog(null, 
                "Enter username (must contain _ and be max 5 characters):\nExample: kyl_1", 
                "Registration", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (username == null) return false;
            
            currentUser.setUsername(username);
            if (currentUser.checkUserName()) {
                JOptionPane.showMessageDialog(null, "Username successfully captured.");
                validUsername = true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Password registration with validation
        boolean validPassword = false;
        while (!validPassword) {
            String password = JOptionPane.showInputDialog(null, 
                "Enter password:\n- At least 8 characters\n- Contains capital letter\n- Contains number\n- Contains special character", 
                "Registration", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (password == null) return false;
            
            currentUser.setPassword(password);
            if (currentUser.checkPasswordComplexity()) {
                JOptionPane.showMessageDialog(null, "Password successfully captured.");
                validPassword = true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        // Cell phone registration with validation
        boolean validCell = false;
        while (!validCell) {
            String cellPhone = JOptionPane.showInputDialog(null, 
                "Enter South African cell phone number with international code:\nExample: +27834557896", 
                "Registration", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (cellPhone == null) return false;
            
            currentUser.setCellPhoneNumber(cellPhone);
            if (currentUser.checkCellPhoneNumber()) {
                JOptionPane.showMessageDialog(null, "Cell phone number successfully added.");
                validCell = true;
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Cell phone number incorrectly formatted or does not contain international code.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        JOptionPane.showMessageDialog(null, "Registration complete!");
        
        // Login
        int attempts = 0;
        while (attempts < 3) {
            String loginUsername = JOptionPane.showInputDialog(null, 
                "Enter username to login:", 
                "Login", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (loginUsername == null) return false;
            
            String loginPassword = JOptionPane.showInputDialog(null, 
                "Enter password:", 
                "Login", 
                JOptionPane.QUESTION_MESSAGE);
            
            if (loginPassword == null) return false;
            
            boolean loginSuccess = currentUser.loginUser(loginUsername, loginPassword);
            String loginMessage = currentUser.returnLoginStatus(loginSuccess);
            
            if (loginSuccess) {
                JOptionPane.showMessageDialog(null, loginMessage);
                return true;
            } else {
                attempts++;
                JOptionPane.showMessageDialog(null, 
                    loginMessage + "\nAttempts remaining: " + (3 - attempts),
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        JOptionPane.showMessageDialog(null, 
            "Maximum login attempts exceeded. Exiting.",
            "Login Failed",
            JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    /**
     * Main application loop for QuickChat
     */
    private static void runQuickChat() {
        JOptionPane.showMessageDialog(null, 
            "Welcome to QuickChat, " + currentUser.getFirstName() + "!",
            "QuickChat",
            JOptionPane.INFORMATION_MESSAGE);
        
        boolean running = true;
        
        while (running) {
            String[] options = {"Send Messages", "Show Recently Sent Messages", "View Reports", "Quit"};
            int choice = JOptionPane.showOptionDialog(null,
                "Please select an option:",
                "QuickChat - Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            
            switch (choice) {
                case 0: // Send Messages
                    sendMessages();
                    break;
                    
                case 1: // Show Recently Sent Messages
                    JOptionPane.showMessageDialog(null, 
                        "Coming Soon.",
                        "Feature in Development",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                    
                case 2: // View Reports
                    viewReports();
                    break;
                    
                case 3: // Quit
                case -1: // Window closed
                    running = false;
                    JOptionPane.showMessageDialog(null, 
                        "Thank you for using QuickChat. Goodbye!",
                        "QuickChat",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }
    
    /**
     * Handles sending messages
     */
    private static void sendMessages() {
        String input = JOptionPane.showInputDialog(null, 
            "How many messages would you like to send?",
            "Send Messages",
            JOptionPane.QUESTION_MESSAGE);
        
        if (input == null) return;
        
        try {
            int numMessages = Integer.parseInt(input);
            
            if (numMessages <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                return;
            }
            
            for (int i = 0; i < numMessages; i++) {
                Message msg = new Message();
                
                // Get recipient
                boolean validRecipient = false;
                while (!validRecipient) {
                    String recipient = JOptionPane.showInputDialog(null, 
                        "Message " + (i + 1) + " of " + numMessages + 
                        "\nEnter recipient cell phone number (with international code):",
                        "Send Message",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (recipient == null) return;
                    
                    msg.setRecipient(recipient);
                    if (msg.checkRecipientCell() == 0) {
                        JOptionPane.showMessageDialog(null, "Cell phone number successfully captured.");
                        validRecipient = true;
                    } else {
                        JOptionPane.showMessageDialog(null, 
                            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                // Get message content
                boolean validMessage = false;
                while (!validMessage) {
                    String messageContent = JOptionPane.showInputDialog(null, 
                        "Enter your message (max 250 characters):",
                        "Send Message",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (messageContent == null) return;
                    
                    msg.setMessageContent(messageContent);
                    String lengthCheck = msg.checkMessageLength();
                    
                    if (lengthCheck.equals("Message ready to send.")) {
                        JOptionPane.showMessageDialog(null, "Message ready to send.");
                        validMessage = true;
                    } else {
                        JOptionPane.showMessageDialog(null, lengthCheck, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                // Create message hash
                msg.createMessageHash();
                
                // Choose action
                String[] actions = {"Send Message", "Disregard Message", "Store Message to send later"};
                int action = JOptionPane.showOptionDialog(null,
                    "What would you like to do with this message?",
                    "Send Message",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    actions,
                    actions[0]);
                
                if (action == -1) return;
                
                String result = msg.sentMessage(action + 1);
                JOptionPane.showMessageDialog(null, result);
                
                // Add to manager
                messageManager.addMessage(msg);
                
                // Display message details
                if (action == 0) { // If sent
                    JOptionPane.showMessageDialog(null, 
                        msg.printMessage(),
                        "Message Details",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
            // Show total messages
            JOptionPane.showMessageDialog(null, 
                "Total messages sent: " + Message.returnTotalMessages(),
                "QuickChat",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Invalid number entered.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Displays various reports and search options
     */
    private static void viewReports() {
        String[] reportOptions = {
            "Display All Sent Messages",
            "Display Longest Message",
            "Search by Message ID",
            "Search by Recipient",
            "Delete Message by Hash",
            "Full Report",
            "Back to Main Menu"
        };
        
        int choice = JOptionPane.showOptionDialog(null,
            "Select a report option:",
            "QuickChat - Reports",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            reportOptions,
            reportOptions[0]);
        
        switch (choice) {
            case 0: // Display All Sent Messages
                String sentInfo = messageManager.displaySentMessagesInfo();
                JOptionPane.showMessageDialog(null, sentInfo, "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case 1: // Display Longest Message
                String longest = messageManager.findLongestMessage();
                JOptionPane.showMessageDialog(null, 
                    "Longest Message:\n\n" + longest,
                    "Longest Message",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case 2: // Search by Message ID
                String searchID = JOptionPane.showInputDialog(null, "Enter Message ID:");
                if (searchID != null) {
                    String result = messageManager.searchByMessageID(searchID);
                    JOptionPane.showMessageDialog(null, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
                
            case 3: // Search by Recipient
                String searchRecipient = JOptionPane.showInputDialog(null, "Enter Recipient Number:");
                if (searchRecipient != null) {
                    List<String> messages = messageManager.searchByRecipient(searchRecipient);
                    StringBuilder sb = new StringBuilder();
                    if (messages.isEmpty()) {
                        sb.append("No messages found for this recipient.");
                    } else {
                        sb.append("Messages for ").append(searchRecipient).append(":\n\n");
                        for (String msg : messages) {
                            sb.append("- ").append(msg).append("\n\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, sb.toString(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
                
            case 4: // Delete Message by Hash
                String deleteHash = JOptionPane.showInputDialog(null, "Enter Message Hash:");
                if (deleteHash != null) {
                    String deleteResult = messageManager.deleteMessageByHash(deleteHash);
                    JOptionPane.showMessageDialog(null, deleteResult, "Delete Message", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
                
            case 5: // Full Report
                String report = messageManager.displayReport();
                JOptionPane.showMessageDialog(null, report, "Full Report", JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case 6: // Back
            case -1:
                break;
        }
    }
}
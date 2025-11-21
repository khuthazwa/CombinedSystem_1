package quickchatapp;

/**
 * Login.java
 * Part 1 - Registration and Login Feature
 * Handles user authentication and validation
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Login {
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    // Constructor
    public Login() {
        this.username = "";
        this.password = "";
        this.cellPhoneNumber = "";
        this.firstName = "";
        this.lastName = "";
    }
    
    // Getters and Setters
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Checks if username contains underscore and is no more than 5 characters
     * @return true if valid, false otherwise
     */
    public boolean checkUserName() {
        if (username == null || username.length() > 5) {
            return false;
        }
        return username.contains("_");
    }
    
    /**
     * Checks password complexity:
     * - At least 8 characters long
     * - Contains a capital letter
     * - Contains a number
     * - Contains a special character
     * @return true if valid, false otherwise
     */
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        
        return hasUpper && hasDigit && hasSpecial;
    }
    
    /**
     * Checks if cell phone number is correctly formatted
     * Uses regex to verify international code and 10-digit number
     * Reference: AI-generated regex pattern using ChatGPT (OpenAI, 2024)
     * OpenAI. (2024). ChatGPT (Nov 12 version) [Large language model]. 
     * https://chat.openai.com
     * 
     * @return true if valid, false otherwise
     */
    public boolean checkCellPhoneNumber() {
        if (cellPhoneNumber == null) {
            return false;
        }
        
        // Regex pattern: starts with +, followed by country code and up to 10 digits
        String regex = "^\\+\\d{1,3}\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellPhoneNumber);
        
        return matcher.matches();
    }
    
    /**
     * Registers user by validating all inputs
     * @return registration status message
     */
    public String registerUser() {
        StringBuilder message = new StringBuilder();
        
        if (!checkUserName()) {
            message.append("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
        } else {
            message.append("Username successfully captured.\n");
        }
        
        if (!checkPasswordComplexity()) {
            message.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
        } else {
            message.append("Password successfully captured.\n");
        }
        
        if (!checkCellPhoneNumber()) {
            message.append("Cell phone number incorrectly formatted or does not contain international code.\n");
        } else {
            message.append("Cell phone number successfully added.\n");
        }
        
        return message.toString().trim();
    }
    
    /**
     * Verifies login credentials
     * @param inputUsername username entered during login
     * @param inputPassword password entered during login
     * @return true if credentials match, false otherwise
     */
    public boolean loginUser(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }
    
    /**
     * Returns login status message
     * @param loginSuccess result of loginUser method
     * @return appropriate login message
     */
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
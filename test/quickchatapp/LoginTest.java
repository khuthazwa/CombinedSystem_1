package quickchatapp;

/**
 * LoginTest.java
 * Unit tests for Login class
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LoginTest {
    
    @Test
    public void testUsernameCorrectlyFormatted() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        login.setCellPhoneNumber("+27838968976");
        login.setFirstName("Kyle");
        login.setLastName("Smith");
        
        assertTrue("Username should be correctly formatted", login.checkUserName());
        assertTrue("Login should succeed", login.loginUser("kyl_1", "Ch&&sec@ke99!"));
        assertEquals("Welcome Kyle Smith, it is great to see you again.", 
                     login.returnLoginStatus(true));
    }
    
    @Test
    public void testUsernameIncorrectlyFormatted() {
        Login login = new Login();
        login.setUsername("kyle!!!!!!!!");
        
        assertFalse("Username should be incorrectly formatted", login.checkUserName());
        
        String result = login.registerUser();
        assertTrue("Should contain username error message", 
                   result.contains("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length."));
    }
    
    @Test
    public void testPasswordMeetsComplexity() {
        Login login = new Login();
        login.setPassword("Ch&&sec@ke99!");
        
        assertTrue("Password should meet complexity requirements", 
                   login.checkPasswordComplexity());
        
        login.setUsername("kyl_1");
        login.setCellPhoneNumber("+27838968976");
        String result = login.registerUser();
        assertTrue("Should contain password success message", 
                   result.contains("Password successfully captured."));
    }
    
    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login login = new Login();
        login.setPassword("password");
        
        assertFalse("Password should not meet complexity requirements", 
                    login.checkPasswordComplexity());
        
        login.setUsername("kyl_1");
        login.setCellPhoneNumber("+27838968976");
        String result = login.registerUser();
        assertTrue("Should contain password error message", 
                   result.contains("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character."));
    }
    
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login login = new Login();
        login.setCellPhoneNumber("+27838968976");
        
        assertTrue("Cell phone should be correctly formatted", 
                   login.checkCellPhoneNumber());
        
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        String result = login.registerUser();
        assertTrue("Should contain cell phone success message", 
                   result.contains("Cell phone number successfully added."));
    }
    
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login login = new Login();
        login.setCellPhoneNumber("08966553");
        
        assertFalse("Cell phone should be incorrectly formatted", 
                    login.checkCellPhoneNumber());
        
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        String result = login.registerUser();
        assertTrue("Should contain cell phone error message", 
                   result.contains("Cell phone number incorrectly formatted or does not contain international code."));
    }
    
    @Test
    public void testLoginSuccessful() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        
        assertTrue("Login should be successful", 
                   login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }
    
    @Test
    public void testLoginFailed() {
        Login login = new Login();
        login.setUsername("kyl_1");
        login.setPassword("Ch&&sec@ke99!");
        
        assertFalse("Login should fail with wrong credentials", 
                    login.loginUser("wrong_user", "wrongpass"));
    }
    
    @Test
    public void testReturnLoginStatusSuccess() {
        Login login = new Login();
        login.setFirstName("John");
        login.setLastName("Doe");
        
        String status = login.returnLoginStatus(true);
        assertEquals("Welcome John Doe, it is great to see you again.", status);
    }
    
    @Test
    public void testReturnLoginStatusFailure() {
        Login login = new Login();
        
        String status = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", status);
    }

    private void assertTrue(String username_should_be_correctly_formatted, boolean checkUserName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void assertFalse(String username_should_be_incorrectly_formatted, boolean checkUserName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
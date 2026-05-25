package com.mycompany.part1;

import com.mycompany.part2.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class part1_loginRegister_test {

    // --- username tests ---

    // "kyl_1" should pass since it has an underscore and is under 5 chars
    @Test
    public void testUsernameCorrectlyFormatted() {
        login userLogin = new login();
        assertTrue(userLogin.checkUserName("kyl_1"));
    }

    // "kyle!!!!!!!" is too long and has no underscore
    @Test
    public void testUsernameIncorrectlyFormatted() {
        login userLogin = new login();
        assertFalse(userLogin.checkUserName("kyle!!!!!!!"));
    }

    // --- password tests ---

    // this password has everything it needs
    @Test
    public void testPasswordMeetsComplexityRequirements() {
        login userLogin = new login();
        assertTrue(userLogin.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    // "password" is missing a capital, number, and special character
    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        login userLogin = new login();
        assertFalse(userLogin.checkPasswordComplexity("password"));
    }

    // --- cell phone tests ---

    // valid SA number with +27
    @Test
    public void testCellPhoneCorrectlyFormatted() {
        login userLogin = new login();
        assertTrue(userLogin.checkCellPhoneNumber("+27838968976"));
    }

    // missing the country code so it should fail
    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        login userLogin = new login();
        assertFalse(userLogin.checkCellPhoneNumber("08966553"));
    }

    // --- login tests ---

    // register first then try logging in with the right details
    @Test
    public void testLoginSuccessful() {
        login userLogin = new login();
        userLogin.registerUser("kyle", "something", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(userLogin.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    // wrong username and password, should return false
    @Test
    public void testLoginFailed() {
        login userLogin = new login();
        userLogin.registerUser("kyle", "something", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(userLogin.loginUser("j_123", "wrongpassword"));
    }

    // --- return login status tests ---

    // check the welcome message is correct
    @Test
    public void testReturnLoginStatusSuccess() {
        login userLogin = new login();
        userLogin.registerUser("kyle", "something", "kyl_1", "Ch&&sec@ke99!", "+27831234567");
        String result = userLogin.returnLoginStatus(true);
        assertEquals("Welcome kyle something, it is great to see you again.", result);
    }

    // check the error message when login fails
    @Test
    public void testReturnLoginStatusFailure() {
        login userLogin = new login();
        String result = userLogin.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", result);
    }
}
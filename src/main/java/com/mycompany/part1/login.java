package com.mycompany.part1;


public class login{

    private String registeredUsername;
    private String registeredPassword;
    private String registeredFirstName;
    private String registeredLastName;

    // username must have an underscore and be 5 chars or less
    public boolean checkUserName(String userName) {
        if (userName.contains("_") && userName.length() <= 5) {
            return true;
        }
        return false;
    }

    // password needs 8+ chars, a capital, a number, and a special character
    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasDigit && hasSpecial;
    }

    // checks that the number starts with +27 and has 9 digits after it
    public boolean checkCellPhoneNumber(String phoneNumber) {
        String regex = "^\\+27[0-9]{9}$";
        return phoneNumber.matches(regex);
    }

    // registers the user if all three fields are valid, otherwise returns what went wrong
    public String registerUser(String firstName, String lastName,
                               String userName, String password, String phoneNum) {

        if (!checkUserName(userName)) {
            return "Username is not correctly formatted, please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number and a "
                    + "special character.";
        }

        if (!checkCellPhoneNumber(phoneNum)) {
            return "Cell phone number incorrectly formatted or does not contain an international "
                    + "code; please correct the number and try again.";
        }

        // save the user's details
        this.registeredFirstName = firstName;
        this.registeredLastName = lastName;
        this.registeredUsername = userName;
        this.registeredPassword = password;

        return "Registration successful.";
    }

    // returns true if the username and password match what was registered
    public boolean loginUser(String userName, String password) {
        if (registeredUsername == null || registeredPassword == null) {
            return false;
        }
        return registeredUsername.equals(userName) && registeredPassword.equals(password);
    }

    // shows a welcome message or an error depending on login result
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + registeredFirstName + " " + registeredLastName
                    + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public String getRegisteredUsername() {
        return registeredUsername;
    }

    public String getRegisteredPassword() {
        return registeredPassword;
    }

    public String getRegisteredFirstName() {
        return registeredFirstName;
    }

    public String getRegisteredLastName() {
        return registeredLastName;
    }
}
package com.mycompany.part1;

import java.util.Scanner;

public class RegisterAndLogin {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        login userLogin = new login();

        String firstName, lastName, userName, password, phoneNum;
        String registrationResult;

        // get the user's name first
        System.out.print("Enter your first name: ");
        firstName = input.nextLine();

        System.out.print("Enter your last name: ");
        lastName = input.nextLine();

        // keep asking until everything is valid
        do {
            System.out.print("Enter your username (must contain '_' and be <= 5 characters): ");
            userName = input.nextLine();

            System.out.print("Enter your password: ");
            password = input.nextLine();

            System.out.print("Enter your cell phone number (e.g. +27831234567): ");
            phoneNum = input.nextLine();

            registrationResult = userLogin.registerUser(firstName, lastName, userName, password, phoneNum);
            System.out.println(registrationResult);

        } while (!registrationResult.equals("Registration successful."));

        System.out.println("Username successfully captured");
        System.out.println("Password successfully captured");
        System.out.println("Cell phone number successfully captured");

        // now handle login
        System.out.print("\nEnter your username to log in: ");
        String enteredUsername = input.nextLine();

        System.out.print("Enter your password to log in: ");
        String enteredPassword = input.nextLine();

        boolean loginResult = userLogin.loginUser(enteredUsername, enteredPassword);
        System.out.println(userLogin.returnLoginStatus(loginResult));
    }
}
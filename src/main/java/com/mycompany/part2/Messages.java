package com.mycompany.part2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Messages {

    // variables
    // stores the current generated message ID
    static String messageId = "";
    // keeps count of how many messages were sent
    static int totalSent = 0;
     // these arrays store all the message data while the program is running
    static ArrayList<String> sentMessagesArr = new ArrayList<>();
    static ArrayList<String> disregardedMessagesArr = new ArrayList<>();
    static ArrayList<String> storedMessagesArr = new ArrayList<>();
    static ArrayList<String> messageHashArr = new ArrayList<>();
    static ArrayList<String> messageIdArr = new ArrayList<>();
    static ArrayList<String> recipientArr = new ArrayList<>();

    // generates a random 10 digit message ID
    static void generateMessageId() {
       Random rand = new Random();
       String digits = "";
       for (int i=0;i < 10; i++){
           digits = digits + rand.nextInt(10);
       }
       // saves the result to the messageId variable
       messageId = digits;
               
    }

    // checks the ID is exactly 10 digits, returns true or false
    static boolean checkMessageId() {
      generateMessageId();
      return messageId.length() == 10;
    }

    // checks the recipient number starts with +27 and is correct length
    // returns success or failure message
    static String checkRecipientCell(String number) {
       String regex = "^\\+27[0-9]{9}$";
        if (number.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number incorrectly formatted, please try again.";
        }
    }

    // builds the message hash e.g. 00:0:HITONIGHT
    // uses first 2 of ID + message count + first and last word of message
    static String createMessageHash(String id, int count, String message) {
       // get first 2 characters of the ID
       String first2 = id.substring(0, 2);
    
      // split the message into words using spaces 
      String[] words = message.trim().split(" ");
              
      // get the first word and make it uppercase
      String firstWord = words[0].toUpperCase();
      
      // get the last word and make it uppercase
      // words.length - 1 gives us the index of the last word
      String lastWord = words[words.length - 1].toUpperCase();
     
     
      // join everything together with colons
      return first2 + ":" + count + ":" + firstWord + lastWord;
    }

    //asks user to send, disregard or store the message
    //adds message to the correct array depending on choice
    static String sentMessage(String id, String hash, String recipient, String message) {
     Scanner input = new Scanner(System.in);
        System.out.println("\nWhat do you want to do with this message?");
        System.out.println("1) Send message");
        System.out.println("2) Disregard message");
        System.out.println("3) Store message");

        int choice = input.nextInt();
        
         if (choice == 1) {
            // add all details to the sent arrays
            sentMessagesArr.add(message);
            messageHashArr.add(hash);
            messageIdArr.add(id);
            recipientArr.add(recipient);
            // increment the sent counter
            totalSent++;
            return "Message successfully sent.";
            
        } else if (choice == 2) {
            // only add to disregarded array
            // we dont save the hash, ID or recipient
            // because this message is being thrown away
            disregardedMessagesArr.add(message);
            return "Press 0 to delete the message.";
            
        } else if (choice == 3) {
            // add to stored arrays
            storedMessagesArr.add(message);
            messageHashArr.add(hash);
            messageIdArr.add(id);
            recipientArr.add(recipient);
            // save to JSON file as well
            storeMessagesToJson();
            return "Message successfully stored.";
            
        } else {
            return "Invalid option.";
        }

    }

    // prints all sent messages with their details
    // loops through sentMessagesArr and builds a string
    // with all the details of every sent message
    // returns "No messages sent yet." if the array is empty
    static String printMessages() {
      if (sentMessagesArr.isEmpty()){
          return "No messages sent yet. ";
      }
      String result = "";
       for (int i = 0; i < sentMessagesArr.size(); i++) {
            result = result + "Hash: " + messageHashArr.get(i)
                   + " | To: " + recipientArr.get(i)
                   + " | Message: " + sentMessagesArr.get(i) + "\n";
        }
        return result;
        
    }

    // returns the total number of messages sent
    static int returnTotalMessages() {
      return totalSent;
    }

    // saves all sent messages to a messages.json file
    static void storeMessagesToJson() {
       // start the JSON array
        String json = "[\n";
        
        for (int i = 0; i < sentMessagesArr.size(); i++) {
            // start each message object
            json = json + "  {\n";
            json = json + "    \"messageId\": \"" + messageIdArr.get(i) + "\",\n";
            json = json + "    \"messageHash\": \"" + messageHashArr.get(i) + "\",\n";
            json = json + "    \"recipient\": \"" + recipientArr.get(i) + "\",\n";
            json = json + "    \"message\": \"" + sentMessagesArr.get(i) + "\"\n";
            
            // add a comma after every object except the last one
            if (i < sentMessagesArr.size() - 1) {
                json = json + "  },\n";
            } else {
                json = json + "  }\n";
            }
        }
        // close the JSON array
        json = json + "]";

        // write the string to a file
        // try-catch handles any file writing errors
        try {
            java.io.FileWriter file = new java.io.FileWriter("messages.json");
            file.write(json);
            file.close();
            System.out.println("Messages saved to messages.json");
        } catch (Exception e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // PART 3 methods below

    // a) displays recipient and message for all stored messages
    static void displayStoredRecipients() {
       if (storedMessagesArr.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        for (int i = 0; i < storedMessagesArr.size(); i++) {
            System.out.println("Recipient: " + recipientArr.get(i)
                             + " | Message: " + storedMessagesArr.get(i));
        }
       
    }

    // b) finds and displays the longest message in the array
    static void displayLongestMessage() {
       if (sentMessagesArr.isEmpty()) {
            System.out.println("No messages.");
            return;
        }
        String longest = sentMessagesArr.get(0);
        for (int i = 1; i < sentMessagesArr.size(); i++) {
            if (sentMessagesArr.get(i).length() > longest.length()) {
                longest = sentMessagesArr.get(i);
            }
        }
        System.out.println("Longest message: " + longest);
    }

    // c) searches for a message using its ID and displays it
    static void searchById(String searchId) {
       boolean found = false;
        for (int i = 0; i < messageIdArr.size(); i++) {
            if (messageIdArr.get(i).equals(searchId)) {
                System.out.println("Recipient: " + recipientArr.get(i));
                System.out.println("Message: " + sentMessagesArr.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No message found with that ID.");
        }
    }

    // d) finds all messages sent to a specific recipient
    static void searchByRecipient(String searchRecipient) {
       boolean found = false;
        for (int i = 0; i < messageIdArr.size(); i++) {
            if (messageIdArr.get(i).equals(searchRecipient)) {
                System.out.println("Recipient: " + recipientArr.get(i));
                System.out.println("Message: " + sentMessagesArr.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No message found with that ID.");
        }
    }

    // e) deletes a message from the array using its hash
    static void deleteByHash(String searchHash) {
      boolean found = false;
        for (int i = 0; i < messageHashArr.size(); i++) {
            if (messageHashArr.get(i).equals(searchHash)) {
                System.out.println("Message: \"" + sentMessagesArr.get(i)
                                 + "\" successfully deleted.");
                sentMessagesArr.remove(i);
                messageHashArr.remove(i);
                messageIdArr.remove(i);
                recipientArr.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No message found with that hash.");
        }
    }

    // f) prints a full report of all messages with hash, recipient and message
    static void displayReport() {
        if (sentMessagesArr.isEmpty()) {
            System.out.println("No messages to report.");
            return;
        }
        System.out.println("\n--- FULL REPORT ---");
        for (int i = 0; i < sentMessagesArr.size(); i++) {
            System.out.println("Message Hash: " + messageHashArr.get(i));
            System.out.println("Recipient:    " + recipientArr.get(i));
            System.out.println("Message:      " + sentMessagesArr.get(i));
            System.out.println("-------------------");
        }
    }
}
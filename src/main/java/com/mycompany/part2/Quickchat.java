package com.mycompany.part2;

import java.util.Scanner;
import com.mycompany.part1.login;

public class Quickchat {

    public static void main(String[] args) {
        
        
        Scanner input = new Scanner(System.in);
        
        
        //---object for so we can use login from part 1----
        login userLogin = new login();
        
        //---method object
         Messages m = new Messages();
        
        // step 1: register the user-----------------------------------------------------
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        // keeps asking until register is successful
        String regResult ="";
        do {
          System.out.print("enter username");
          String userName  = input.nextLine();
          
          System.out.print("enter password");
          String password  = input.nextLine();
          
          System.out.print("Enter cell number e.g +27831234567: ");
          String phone = input.nextLine();
          
          //using registerUser() from part1 to check info and return a message
          regResult = userLogin.registerUser(firstName, lastName, userName, password, phone);
          System.out.println(regResult);
          
        } while (!!regResult.equals("Registration successful."));
        
        
        // step 2: log the user in
        //----------------------login---------------------------------------
        boolean loggedIn =  false;
                
        do {
        System.out.print("Enter username: ");
        String enteredUser = input.nextLine();

        System.out.print("Enter password: ");
        String enteredPass = input.nextLine();
        
        // loginUser()from part1 checks if username and password match
        loggedIn = userLogin.loginUser(enteredUser, enteredPass);
        
        // returnLoginStatus()from part1 prints welcome or error message
        System.out.println(userLogin.returnLoginStatus(loggedIn));
        } while(!loggedIn);        
                
              
        // step 3: show welcome message
        // prints "Welcome to QuickChat"
        System.out.println("\nWelcome to QuickChat.");
        
        
        // step 4: show the main menu in a loop
        // option 1 - send messages
        // option 2 - show recently sent messages
        // option 3 - quit
        // option 4 - stored messages menu
        // the loop keeps running until the user picks 3 to quit
        int option = 0;
        
        while (option !=3 ){
            System.out.println("\nChoose one of the following:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");

            option = input.nextInt();
            input.nextLine();
        } //endwhile
        
        

        // OPTION 1:
        // ask how many messages to send
        // ask for recipient number and validate it
        // loop for each message:
        //   - get the message text
        //   - check it is not over 250 characters
        //   - generate message ID
        //   - create message hash
        //   - ask to send, disregard or store
        //   - print message details
        // print total messages sent
        if  (option == 1){
         // ask how many messages the user wants to send
                System.out.print("How many messages do you want to send? ");
                int numMessages = input.nextInt();
                input.nextLine(); // clear buffer

                // ask for the recipient number
                System.out.print("Enter recipient cell number: ");
                String recipient = input.nextLine();

                // checkRecipientCell() validates the number
                // returns success or failure message
                String cellCheck = m.checkRecipientCell(recipient);
                System.out.println(cellCheck); 
                
                // only continues if number is valid
                if (cellCheck.equals("Cell phone number successfully captured.")){
                    
                // loop once for each message the user wants to send
                    for (int i = 0; i < numMessages; i++){
                      
                        System.out.println("\nEnter message " + (i + 1) + ":");
                        String message = input.nextLine();
                        
                        
                        // check the message is not longer than 250 characters
                        // if it is, show how many characters over it is
                        // i-- and continue means we redo this iteration
                        // so the user gets another chance for this message
                        if (message.length() > 250) {
                            int over = message.length() - 250;
                            System.out.println("Message exceeds 250 characters by "
                                    + over + "; please reduce the size.");
                            i--;
                            continue;
                        }
                        
                        // checkMessageId() generates a random 10 digit ID
                        // and stores it in Messages.messageId
                        m.checkMessageId();
                        String msgId = Messages.messageId;
                        
                        // createMessageHash() builds the hash
                        // e.g. 00:0:HITONIGHT
                        String hash = m.createMessageHash(msgId, i, message);
                        
                        // sentMessage() asks the user to send, disregard or store
                        // it adds the message to the correct array
                        // and returns a result message
                        String result = m.sentMessage(msgId, hash, recipient, message);
                        System.out.println(result);
                        
                        // print all the details of this message
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID:   " + msgId);
                        System.out.println("Message Hash: " + hash);
                        System.out.println("Recipient:    " + recipient);
                        System.out.println("Message:      " + message);
                                
                    } //endfor
                   System.out.println("\nTotal messages sent: " + m.returnTotalMessages()); 
                }//endif
                
                
        // OPTION 2:
        // call printMessages() and display results       
        }else if (option == 2){
           
             System.out.println(m.printMessages());
             
           
        // OPTION 3:
        // print "Exiting program..." and quit
        }else if (option == 3) {
        
            
        System.out.println("Exiting program...");
        // OPTION 4:
        // show sub menu:
        }else if (option == 4) {

                System.out.println("\na) Display stored message recipients");
                System.out.println("b) Display longest message");
                System.out.println("c) Search by message ID");
                System.out.println("d) Search by recipient");
                System.out.println("e) Delete message by hash");
                System.out.println("f) Display full report");
                
                
                String subOption = input.nextLine();
                
                // a) shows all recipients and their stored messages
                if (subOption.equals("a")) {
                    m.displayStoredRecipients();

                // b) finds and prints the longest message in the array
                } else if (subOption.equals("b")) {
                    m.displayLongestMessage();

                // c) asks for an ID and finds the matching message
                } else if (subOption.equals("c")) {
                    System.out.print("Enter message ID to search: ");
                    String searchId = input.nextLine();
                    m.searchById(searchId);

                // d) asks for a recipient number and finds all their messages
                } else if (subOption.equals("d")) {
                    System.out.print("Enter recipient number to search: ");
                    String searchRec = input.nextLine();
                    m.searchByRecipient(searchRec);

                // e) asks for a hash and deletes the matching message
                } else if (subOption.equals("e")) {
                    System.out.print("Enter message hash to delete: ");
                    String searchHash = input.nextLine();
                    m.deleteByHash(searchHash);

                // f) prints a full report of all messages
                } else if (subOption.equals("f")) {
                    m.displayReport();
                }
                
                
        
         // INVALID OPTION 
        }else {
                System.out.println("Please enter 1, 2, 3 or 4.");
            }//end if
    
     
   }         
}
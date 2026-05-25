/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part2;

import com.mycompany.part2.Messages;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lindelani
 */
public class Part2_Messages_Test {
    
    public Part2_Messages_Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    // runs before every test - loads the 4 test messages
    @BeforeEach
    public void setUp() {
        Messages.sentMessagesArr.clear();
        Messages.messageHashArr.clear();
        Messages.messageIdArr.clear();
        Messages.recipientArr.clear();
        Messages.totalSent = 0;

        // 4 test messages from the assignment
        Messages.sentMessagesArr.add("Hi Mike, can you join us for dinner tonight?");
        Messages.sentMessagesArr.add("Where are you? You are late! I have asked you to be on time.");
        Messages.sentMessagesArr.add("Ok, I am leaving without you.");
        Messages.sentMessagesArr.add("It is dinner time!");

        Messages.recipientArr.add("+27718693002");
        Messages.recipientArr.add("+27838884567");
        Messages.recipientArr.add("+27838884567");
        Messages.recipientArr.add("+27838884567");

        Messages.messageIdArr.add("0034781923");
        Messages.messageIdArr.add("0056781234");
        Messages.messageIdArr.add("0078901234");
        Messages.messageIdArr.add("0838884567");

        Messages.messageHashArr.add("00:0:HITONIGHT");
        Messages.messageHashArr.add("00:1:WHERETIME");
        Messages.messageHashArr.add("00:2:OKYOU");
        Messages.messageHashArr.add("08:3:ITTIME");

        Messages.totalSent = 4;
    }

    @AfterEach
    public void tearDown() {
    }

    // TEST 1: sent messages array is correctly populated
    @Test
    public void testSentMessagesArray() {
        assertEquals("It is dinner time!", Messages.sentMessagesArr.get(3));
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                Messages.sentMessagesArr.get(1));
    }

    // TEST 2: longest message
    @Test
    public void testLongestMessage() {
        String longest = Messages.sentMessagesArr.get(0);
        for (int i = 1; i < Messages.sentMessagesArr.size(); i++) {
            if (Messages.sentMessagesArr.get(i).length() > longest.length()) {
                longest = Messages.sentMessagesArr.get(i);
            }
        }
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // TEST 3: search by message ID returns correct message
    @Test
    public void testSearchByMessageId() {
        String found = "";
        for (int i = 0; i < Messages.messageIdArr.size(); i++) {
            if (Messages.messageIdArr.get(i).equals("0838884567")) {
                found = Messages.sentMessagesArr.get(i);
            }
        }
        assertEquals("It is dinner time!", found);
    }

    // TEST 4: search by recipient returns correct messages
    @Test
    public void testSearchByRecipient() {
        String results = "";
        for (int i = 0; i < Messages.recipientArr.size(); i++) {
            if (Messages.recipientArr.get(i).equals("+27838884567")) {
                results = results + Messages.sentMessagesArr.get(i) + " ";
            }
        }
        assertTrue(results.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(results.contains("Ok, I am leaving without you."));
    }

    // TEST 5: delete message by hash
    @Test
    public void testDeleteByHash() {
        for (int i = 0; i < Messages.messageHashArr.size(); i++) {
            if (Messages.messageHashArr.get(i).equals("00:1:WHERETIME")) {
                Messages.sentMessagesArr.remove(i);
                Messages.messageHashArr.remove(i);
                Messages.messageIdArr.remove(i);
                Messages.recipientArr.remove(i);
                break;
            }
        }
        assertFalse(Messages.sentMessagesArr.contains(
                "Where are you? You are late! I have asked you to be on time."));
    }

    // TEST 6: message hash is built correctly
    @Test
    public void testCreateMessageHash() {
        String hash = Messages.createMessageHash("0034781923", 0, "Hi tonight");
        assertEquals("00:0:HITONIGHT", hash);
    }

    // TEST 7: message ID is exactly 10 digits
    @Test
    public void testMessageIdLength() {
        Messages.checkMessageId();
        assertEquals(10, Messages.messageId.length());
    }

    // TEST 8: recipient cell number valid
    @Test
    public void testRecipientCellSuccess() {
        String result = Messages.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    // TEST 9: recipient cell number invalid
    @Test
    public void testRecipientCellFailure() {
        String result = Messages.checkRecipientCell("0838884567");
        assertEquals("Cell phone number incorrectly formatted, please try again.", result);
    }

    // TEST 10: total messages count is correct
    @Test
    public void testReturnTotalMessages() {
        assertEquals(4, Messages.returnTotalMessages());
    }
}
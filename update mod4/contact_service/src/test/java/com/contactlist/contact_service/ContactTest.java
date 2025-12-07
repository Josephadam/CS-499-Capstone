package com.contactlist.contact_service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * ContactTest
 *
 * What these tests assert your Contact class must enforce:
 *  - contactId: non-null, non-blank, length <= 10
 *  - firstName: non-null, non-blank, length <= 10
 *  - lastName:  non-null, non-blank, length <= 10
 *  - phone:     non-null, exactly 10 digits (0-9 only, no separators)
 *  - address:   non-null, non-blank, length <= 30
 *
 * Notes:
 *  1) Each test is single-purpose so failures are easy to diagnose.
 *  2) Boundary tests are included (exactly 10 chars, exactly 30 chars, exactly 10 digits).
 *  3) Method names clearly describe behavior under test.
 */
public class ContactTest {

    // -------------------- Happy Path / Positive Cases --------------------

    @Test
    public void testValidContactCreation() {
        // Typical valid input should construct successfully and preserve values through getters.
        Contact contact = new Contact("001", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("001", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    @Test
    public void testValidBoundaryMaxLengths() {
        // Exactly at the maximum lengths should be accepted.
        String ten = "ABCDEFGHIJ"; // 10 chars
        String addr30 = "123456789012345678901234567890"; // 30 chars

        Contact c = new Contact(ten, ten, ten, "0123456789", addr30);

        assertEquals(ten, c.getContactId());
        assertEquals(ten, c.getFirstName());
        assertEquals(ten, c.getLastName());
        assertEquals("0123456789", c.getPhone());
        assertEquals(addr30, c.getAddress());
    }

    @Test
    public void testValidTenDigitPhone() {
        Contact c = new Contact("A1", "Amy", "Lee", "5551234567", "789 Oak Ave");
        assertEquals("5551234567", c.getPhone());
    }

    // -------------------- Null Validation --------------------

    @Test
    public void testNullContactId() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(null, "John", "Doe", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testNullFirstName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("002", null, "Doe", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testNullLastName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("003", "John", null, "1234567890", "123 Main St")
        );
    }

    @Test
    public void testNullPhone() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("004", "John", "Doe", null, "123 Main St")
        );
    }

    @Test
    public void testNullAddress() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("005", "John", "Doe", "1234567890", null)
        );
    }

    // -------------------- Blank / Whitespace Validation --------------------
    // If your spec allows trimming instead, adjust these to expected behavior.

    @Test
    public void testBlankContactId() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("", "John", "Doe", "1234567890", "123 Main St")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("   ", "John", "Doe", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testBlankFirstName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("006", "", "Doe", "1234567890", "123 Main St")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("006", "   ", "Doe", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testBlankLastName() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("007", "John", "", "1234567890", "123 Main St")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("007", "John", "   ", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testBlankPhone() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("008", "John", "Doe", "", "123 Main St")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("008", "John", "Doe", "   ", "123 Main St")
        );
    }

    @Test
    public void testBlankAddress() {
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("009", "John", "Doe", "1234567890", "")
        );
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("009", "John", "Doe", "1234567890", "   ")
        );
    }

    // -------------------- Length Constraints --------------------

    @Test
    public void testInvalidContactIdTooLong() {
        // 11 chars -> should fail (max is 10)
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testFirstNameTooLong() {
        // > 10 chars should fail
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("010", "TooLongFirst", "Doe", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testLastNameTooLong() {
        // > 10 chars should fail
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("011", "John", "TooLongLast", "1234567890", "123 Main St")
        );
    }

    @Test
    public void testAddressTooLong() {
        // 31 chars -> should fail (max is 30)
        String addr31 = "1234567890123456789012345678901";
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("012", "John", "Doe", "1234567890", addr31)
        );
    }

    // -------------------- Phone Format Constraints --------------------

    @Test
    public void testPhoneNotTenDigits() {
        // Too short
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("013", "John", "Doe", "12345", "123 Main St")
        );
        // Too long
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("014", "John", "Doe", "12345678901", "123 Main St")
        );
        // Non-numeric
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("015", "John", "Doe", "abcdefghij", "123 Main St")
        );
        // With separators (if your spec forbids anything but digits)
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("016", "John", "Doe", "123-456-7890", "123 Main St")
        );
    }
}

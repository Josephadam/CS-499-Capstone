package com.contactlist.contact_service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {
	// Test adding a new contact
    @Test
    public void testAddContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact("001", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);
    }
    // Test adding a duplicate id 
    @Test
    public void testAddDuplicateContactThrowsException() {
        ContactService service = new ContactService();
        Contact contact1 = new Contact("001", "John", "Doe", "1234567890", "123 Main St");
        Contact contact2 = new Contact("001", "Jane", "Smith", "0987654321", "456 Oak Ave");
        service.addContact(contact1);
        assertThrows(IllegalArgumentException.class, () -> {
            service.addContact(contact2);
        });
    }
    
    // Test deleting new contact
    @Test
    public void testDeleteContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact("002", "Alice", "Brown", "1112223333", "789 Pine Rd");
        service.addContact(contact);
        service.deleteContact("002");
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteContact("002");
        });
    }
    
    // Test updating a first name
    @Test
    public void testUpdateFirstName() {
        ContactService service = new ContactService();
        Contact contact = new Contact("003", "Mike", "Wazowski", "2223334444", "Monstropolis");
        service.addContact(contact);
        service.updateFirstName("003", "James");
        assertEquals("James", contact.getFirstName());
    }
    // Test update last name
    @Test
    public void testUpdateLastName() {
        ContactService service = new ContactService();
        Contact contact = new Contact("004", "Sally", "Ride", "3334445555", "NASA Blvd");
        service.addContact(contact);
        service.updateLastName("004", "Fields");
        assertEquals("Fields", contact.getLastName());
    }
    // Test update phone number
    @Test
    public void testUpdatePhone() {
        ContactService service = new ContactService();
        Contact contact = new Contact("005", "Buzz", "Lightyear", "4445556666", "Infinity St");
        service.addContact(contact);
        service.updatePhone("005", "9998887777");
        assertEquals("9998887777", contact.getPhone());
    }
    
    // Test update update address
    @Test
    public void testUpdateAddress() {
        ContactService service = new ContactService();
        Contact contact = new Contact("006", "Woody", "Pride", "5556667777", "Andy's Room");
        service.addContact(contact);
        service.updateAddress("006", "Western Town");
        assertEquals("Western Town", contact.getAddress());
    }

    // Test update non existent contact throws exception
    @Test
    public void testUpdateNonexistentContactThrowsException() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName("999", "NewName");
        });
    }
    
    // Test delete non existent contact throws exception
    @Test
    public void testDeleteNonexistentContactThrowsException() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> {
            service.deleteContact("999");
        });
    }
    
    // test update first name with null value
    @Test
    public void testUpdateFirstNameWithInvalidValue() {
        ContactService service = new ContactService();
        Contact contact = new Contact("010", "Ann", "Lee", "1234567890", "101 Ocean Dr");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateFirstName("010", null);
        });
    }

    // Test update last name with too long value
    @Test
    public void testUpdateLastNameWithTooLongValue() {
        ContactService service = new ContactService();
        Contact contact = new Contact("011", "Tom", "Brady", "1234567890", "Patriot Way");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateLastName("011", "SuperLongLastName");
        });
    }
    
    // Test update phone with invalid value
    @Test
    public void testUpdatePhoneWithInvalidValue() {
        ContactService service = new ContactService();
        Contact contact = new Contact("012", "Jerry", "Smith", "1234567890", "Cartoon Lane");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> {
            service.updatePhone("012", "123"); // Invalid length
        });
    }
    
    // Test update address with null value
    @Test
    public void testUpdateAddressWithNullValue() {
        ContactService service = new ContactService();
        Contact contact = new Contact("013", "Rick", "Sanchez", "0987654321", "Dimension C-137");
        service.addContact(contact);
        assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress("013", null);
        });
    }

}

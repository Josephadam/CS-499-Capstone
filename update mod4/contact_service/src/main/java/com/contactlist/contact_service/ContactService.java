package com.contactlist.contact_service;

import java.util.*; // Needed for List, ArrayList, Comparator, Locale, Map, HashMap

public class ContactService {

    // ENHANCEMENT: DATA STRUCTURE IMPROVEMENT
    // Using a HashMap<String, Contact> instead of a simple List<Contact>.
    // This gives us O(1) average-time lookups, inserts, and deletes by contactId,
    // instead of O(n) time when scanning a list.
    private final Map<String, Contact> contacts = new HashMap<>();

    // Adds a new contact if the ID doesn't already exist
    public void addContact(Contact contact) {
        // Uses HashMap.containsKey -> O(1) average time
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists.");
        }
        // HashMap.put is O(1) average time
        contacts.put(contact.getContactId(), contact);
    }

    // Deletes a contact by ID if it exists
    public void deleteContact(String contactId) {
        // containsKey + remove are both O(1) average time
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found.");
        }
        contacts.remove(contactId);
    }

    // Updates the first name of an existing contact
    public void updateFirstName(String contactId, String firstName) {
        Contact contact = getContactById(contactId); // O(1) lookup
        contact.setFirstName(firstName);
    }

    // Updates the last name of an existing contact
    public void updateLastName(String contactId, String lastName) {
        Contact contact = getContactById(contactId); // O(1) lookup
        contact.setLastName(lastName);
    }

    // Updates the phone number of an existing contact
    public void updatePhone(String contactId, String phone) {
        Contact contact = getContactById(contactId); // O(1) lookup
        contact.setPhone(phone);
    }

    // Updates the address of an existing contact
    public void updateAddress(String contactId, String address) {
        Contact contact = getContactById(contactId); // O(1) lookup
        contact.setAddress(address);
    }

    // Retrieves a contact by ID or throws an exception if not found
    // Uses HashMap.get -> O(1) average time
    private Contact getContactById(String contactId) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID not found.");
        }
        return contact;
    }

    // ================== ALGORITHMIC ENHANCEMENTS ==================

    /**
     * ALGORITHMIC ENHANCEMENT – SORTING
     *
     * Returns all contacts sorted by:
     *   1) last name
     *   2) first name
     *   3) contactId
     *
     * Complexity:
     *   - Copy values from the map into a list -> O(n)
     *   - Sort the list using List.sort (TimSort) -> O(n log n)
     *   - Overall: O(n log n)
     */
    public List<Contact> getContactsSortedByName() {
        // Copy map values into a list so they can be sorted
        List<Contact> list = new ArrayList<>(contacts.values());

        // Sorting by lastName, then firstName, then contactId
        list.sort(Comparator
                .comparing(Contact::getLastName)
                .thenComparing(Contact::getFirstName)
                .thenComparing(Contact::getContactId));

        return list;
    }

    /**
     * ALGORITHMIC ENHANCEMENT – SEARCHING
     *
     * Finds all contacts whose last name starts with the given prefix (case-insensitive).
     *
     * Complexity:
     *   - Scan all contacts once -> O(n)
     *   - Each check is O(1) string work (ignoring constant factors)
     *   - Overall: O(n)
     */
    public List<Contact> searchByLastNamePrefix(String prefix) {
        String normalized = prefix.toLowerCase(Locale.ROOT);
        List<Contact> results = new ArrayList<>();

        // Linear scan through all contacts in the map: O(n)
        for (Contact contact : contacts.values()) {
            if (contact.getLastName().toLowerCase(Locale.ROOT).startsWith(normalized)) {
                results.add(contact);
            }
        }

        // Keep search results in a consistent sorted order
        results.sort(Comparator
                .comparing(Contact::getLastName)
                .thenComparing(Contact::getFirstName)
                .thenComparing(Contact::getContactId));

        return results;
    }

    /**
     * Helper for tests or debugging – returns how many contacts are stored.
     * Complexity: O(1)
     */
    public int size() {
        return contacts.size();
    }
}

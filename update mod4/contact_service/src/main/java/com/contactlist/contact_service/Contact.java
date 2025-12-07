package com.contactlist.contact_service;

public class Contact {
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    /**
     * Constructor with explicit, predictable validation order:
     * 1) Null checks (must throw IllegalArgumentException, not NPE)
     * 2) Blank checks (reject "", "   ")
     * 3) Length checks
     * 4) Phone format check (exactly 10 digits)
     */
    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        // --- Null checks (tests require IllegalArgumentException) ---
        this.contactId = requireNonNull(contactId, "contactId");
        this.firstName = requireNonNull(firstName, "firstName");
        this.lastName  = requireNonNull(lastName,  "lastName");
        this.phone     = requireNonNull(phone,     "phone");
        this.address   = requireNonNull(address,   "address");

        // --- Blank checks (reject pure whitespace) ---
        requireNotBlank(this.contactId, "contactId");
        requireNotBlank(this.firstName, "firstName");
        requireNotBlank(this.lastName,  "lastName");
        requireNotBlank(this.address,   "address");

        // --- Length checks (same limits you used) ---
        requireMaxLen(this.contactId, 10, "contactId");
        requireMaxLen(this.firstName, 10, "firstName");
        requireMaxLen(this.lastName,  10, "lastName");
        requireMaxLen(this.address,   30, "address");

        // --- Phone: exactly 10 digits (no spaces/dashes/letters) ---
        requirePhone10Digits(this.phone);
    }

    // ----------------- Validation helpers -----------------

    private static String requireNonNull(String v, String field) {
        if (v == null) {
            throw new IllegalArgumentException(field + " cannot be null");
        }
        return v;
    }

    private static void requireNotBlank(String v, String field) {
        if (v.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank");
        }
    }

    private static void requireMaxLen(String v, int max, String field) {
        if (v.length() > max) {
            throw new IllegalArgumentException(field + " length must be <= " + max);
        }
    }

    private static void requirePhone10Digits(String p) {
        if (!p.matches("^\\d{10}$")) {
            throw new IllegalArgumentException("phone must be exactly 10 digits");
        }
    }

    // ----------------- Getters -----------------
    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getPhone()     { return phone; }
    public String getAddress()   { return address; }

    // ----------------- Setters with same validation -----------------

    public void setFirstName(String firstName) {
        requireNonNull(firstName, "firstName");
        requireNotBlank(firstName, "firstName");
        requireMaxLen(firstName, 10, "firstName");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        requireNonNull(lastName, "lastName");
        requireNotBlank(lastName, "lastName");
        requireMaxLen(lastName, 10, "lastName");
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        requireNonNull(phone, "phone");
        requirePhone10Digits(phone);
        this.phone = phone;
    }

    public void setAddress(String address) {
        requireNonNull(address, "address");
        requireNotBlank(address, "address");
        requireMaxLen(address, 30, "address");
        this.address = address;
    }
}

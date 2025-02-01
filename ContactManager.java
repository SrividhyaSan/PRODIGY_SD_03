package mypackage;


import java.io.*;
import java.util.*;

public class ContactManager {
    private static final String FILE_NAME = "contacts.txt"; // File for persistent storage
    private static final List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        loadContacts(); // Load contacts from file

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Contact Manager ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    editContact(scanner);
                    break;
                case 4:
                    deleteContact(scanner);
                    break;
                case 5:
                    saveContacts(); // Save before exiting
                    System.out.println("Exiting Contact Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }

    // Add a new contact
    private static void addContact(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        contacts.add(new Contact(name, phoneNumber, email));
        System.out.println("Contact added successfully!");
        saveContacts();
    }

    // View all contacts
    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println("\n--- Contact List ---");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println((i + 1) + ". " + contacts.get(i));
        }
    }

    // Edit an existing contact
    private static void editContact(Scanner scanner) {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the number of the contact to edit: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number!");
            return;
        }

        System.out.print("Enter new Name (or press Enter to keep current): ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Phone Number (or press Enter to keep current): ");
        String newPhone = scanner.nextLine();
        System.out.print("Enter new Email (or press Enter to keep current): ");
        String newEmail = scanner.nextLine();

        Contact contact = contacts.get(index);
        if (!newName.isEmpty()) contact.setName(newName);
        if (!newPhone.isEmpty()) contact.setPhoneNumber(newPhone);
        if (!newEmail.isEmpty()) contact.setEmail(newEmail);

        System.out.println("Contact updated successfully!");
        saveContacts();
    }

    // Delete a contact
    private static void deleteContact(Scanner scanner) {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter the number of the contact to delete: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume newline

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number!");
            return;
        }

        contacts.remove(index);
        System.out.println("Contact deleted successfully!");
        saveContacts();
    }

    // Save contacts to a file
    private static void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                writer.println(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    // Load contacts from a file
    private static void loadContacts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                if (data.length == 3) {
                    contacts.add(new Contact(data[0], data[1], data[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
}

// Contact class to store contact details
class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

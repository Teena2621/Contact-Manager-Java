import java.io.*;
import java.util.*;

public class ContactManager {
    static final String FILE_NAME = "contacts.txt";
    static ArrayList<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        loadContacts();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Contact Manager ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> addContact(sc);
                case 2 -> viewContacts();
                case 3 -> searchContact(sc);
                case 4 -> deleteContact(sc);
                case 5 -> saveContacts();
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        System.out.println("Exiting... Goodbye!");
        sc.close();
    }

    static void addContact(Scanner sc) {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter phone: ");
        String phone = sc.nextLine();
        contacts.add(new Contact(name, phone));
        System.out.println("Contact added!");
    }

    static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    static void searchContact(Scanner sc) {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(name)) {
                System.out.println(c);
                found = true;
            }
        }
        if (!found) System.out.println("Contact not found.");
    }

    static void deleteContact(Scanner sc) {
        System.out.print("Enter name to delete: ");
        String name = sc.nextLine().toLowerCase();
        Iterator<Contact> iterator = contacts.iterator();
        boolean deleted = false;

        while (iterator.hasNext()) {
            Contact c = iterator.next();
            if (c.getName().toLowerCase().contains(name)) {
                iterator.remove();
                deleted = true;
                System.out.println("Contact deleted.");
            }
        }

        if (!deleted) System.out.println("No matching contact found.");
    }

    static void saveContacts() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact c : contacts) {
                writer.println(c.getName() + "," + c.getPhone());
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }

    static void loadContacts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.add(new Contact(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            
        }
    }
}

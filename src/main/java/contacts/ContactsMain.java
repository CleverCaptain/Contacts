package contacts;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.System.in;

@SuppressWarnings("unchecked")
public class ContactsMain {
    static Map<Integer, Integer> helpSearch = new LinkedHashMap<>();
    static List<Contacts> contacts;
    public static void main(String[] args) {
        try (final Scanner kb = new Scanner(in)) {
            contacts = new LinkedList<>();
            boolean isEmptyFile = args.length == 0;
            String fileName = "";
            File file = null;
            if (!isEmptyFile) {
                fileName = args[0];
                file = new File(args[0]);
            }
            if (!isEmptyFile) {
                if (!file.exists()) {
                    contacts = new LinkedList<>();
                } else {
                    contacts = (ArrayList<Contacts>) deserialize(fileName);
                }
            }
            boolean isRunning = true;
            while (isRunning) {
                System.out.print("[menu] Enter action (add, list, search, count, exit): ");
                String action = kb.next();
                switch (action) {
                    case "add" -> {
                        kb.nextLine();
                        System.out.print("Enter the type (person, organization): ");
                        String type = kb.nextLine();
                        switch (type) {
                            case "person" -> {
                                PersonContacts toAdd = new PersonContacts();
                                System.out.print("Enter the name: ");
                                String name = kb.nextLine();
                                toAdd.setName(name);
                                System.out.print("Enter the surname: ");
                                String surName = kb.nextLine();
                                toAdd.setSurName(surName);
                                System.out.print("Enter the birth date: ");
                                String birthDateString = kb.nextLine();
                                toAdd.setBirthDate(birthDateString);
                                System.out.print("Enter the gender (M, F): ");
                                String gender = kb.nextLine();
                                toAdd.setGender(gender);
                                System.out.print("Enter the number: ");
                                String phoneNumber = kb.nextLine();
                                toAdd.setPhoneNumber(phoneNumber);
                                toAdd.setTimeCreated(LocalDateTime.now());
                                toAdd.setTimeLastEdited(LocalDateTime.now());
                                contacts.add(toAdd);
                                System.out.println("Saved");
                            }
                            case "organization" -> {
                                OrganizationContacts orgToAdd = new OrganizationContacts();
                                System.out.print("Enter the organization name: ");
                                orgToAdd.setName(kb.nextLine());
                                System.out.print("Enter the address: ");
                                orgToAdd.setAddress(kb.nextLine());
                                System.out.print("Enter the number: ");
                                orgToAdd.setPhoneNumber(kb.nextLine());
                                orgToAdd.setTimeCreated(LocalDateTime.now());
                                orgToAdd.setTimeLastEdited(LocalDateTime.now());
                                contacts.add(orgToAdd);
                                System.out.println("Saved");
                            }
                            default -> System.out.println("Incorrect Type!");
                        }
                    }
                    case "count" -> System.out.println("The Phone Book has " + contacts.size() + " records.");
                    case "list" -> {
                        listData(contacts);
                        if (!contacts.isEmpty()) {
                            System.out.print("\n[list] Enter action ([number], back): ");
                            kb.nextLine();
                            String inp = kb.nextLine();
                            if (!inp.equalsIgnoreCase("back")) {
                                int index = Integer.parseInt(inp);
                                System.out.println(contacts.get(index - 1).toString());
                                for (int i = 0; i < contacts.size(); i++) {
                                    helpSearch.put(i + 1, i);
                                }
                                recordMenu(index);
                            }
                        }
                    }
                    case "search" -> {
                        kb.nextLine();
                        searchMenu(contacts);
                        boolean isSearch = true;
                        while (isSearch) {
                            System.out.print("\n[search] Enter action ([number], back, again): ");
                            String toDo = kb.nextLine();
                            switch (toDo) {
                                case "back":
                                    isSearch = false;
                                    break;
                                case "again":
                                    searchMenu(contacts);
                                    break;
                                default:
                                    if (toDo.matches("\\d")) {
                                        int num = Integer.parseInt(toDo);
                                        System.out.println(contacts.get(num - 1).toString());
                                        recordMenu(num);
                                        isSearch = false;
                                    }

                            }
                        }
                    }
                    case "exit" -> {
                        if (!isEmptyFile) {
                            serialize(contacts, fileName);
                        }
                        isRunning = false;
                    }
                    default -> System.out.println("Wrong input!");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getClass().getName());
        }
    }

    static void recordMenu(int num) {
        Scanner kb = new Scanner(in);
        boolean isRecord = true;
        while (isRecord) {
            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            String recordAction = kb.nextLine();
            switch (recordAction) {
                case "edit" -> {
                    editMenu(contacts.get(helpSearch.get(num)));
                    System.out.println(contacts.get(helpSearch.get(num)).toString());
                }
                case "delete" -> {
                    System.out.print("Select a record: ");
                    num = Integer.parseInt(kb.nextLine().trim());
                    try {
                        contacts.remove(num - 1);
                        System.out.println("The record removed!");
                    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        System.out.println("Index not available!");
                    }
                }
                case "menu" -> isRecord = false;
                default -> System.out.println("Wrong option");
            }
        }
    }

    static void editMenu(Contacts contact) {
        Scanner kb = new Scanner(in);
        if (contact instanceof PersonContacts) {
            try {
                System.out.print("Select a field (name, surname, birth, gender, number): ");
                String field = kb.next();
                kb.nextLine();
                switch (field) {
                    case "name" -> {
                        System.out.print("Enter name: ");
                        String name = kb.nextLine();
                        contact.setName(name);
                        System.out.println("The record updated!");
                    }
                    case "surname" -> {
                        System.out.print("Enter surname: ");
                        String surName = kb.nextLine();
                        ((PersonContacts) (contact)).setSurName(surName);
                        System.out.println("The record updated!");
                    }
                    case "birth" -> {
                        System.out.println("Enter the birth date: ");
                        String birthDateString = kb.nextLine();
                        ((PersonContacts) (contact)).setBirthDate(birthDateString);
                    }
                    case "gender" -> {
                        System.out.println("Enter the gender (M, F): ");
                        String gender = kb.nextLine();
                        ((PersonContacts) (contact)).setGender(gender);
                    }
                    case "number" -> {
                        System.out.print("Enter number: ");
                        String phoneNumber = kb.nextLine();
                        contact.setPhoneNumber(phoneNumber);
                        System.out.println("The record updated!");
                    }
                    default -> System.out.print("Field Not found!");
                }
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.out.println("Index not available!");
            }
        } else {
            try {
                System.out.print("Select a field (name, address, number): ");
                String field = kb.next();
                kb.nextLine();
                switch (field) {
                    case "name" -> {
                        System.out.print("Enter the organization name: ");
                        contact.setName(kb.nextLine());
                    }
                    case "address" -> {
                        System.out.print("Enter the organization address: ");
                        ((OrganizationContacts) (contact)).setAddress(kb.nextLine());
                    }
                    case "number" -> {
                        System.out.print("Enter the number: ");
                        contact.setPhoneNumber(kb.nextLine());
                    }
                    default -> System.out.print("Incorrect input!");
                }
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                System.out.println("Index not available!");
            }
        }
        contact.setTimeLastEdited(LocalDateTime.now());
        System.out.println("Saved");
    }

    static void searchMenu(List<Contacts> contacts) {
        Scanner kb = new Scanner(in);
        System.out.print("Enter search query: ");
        String query = kb.nextLine();
        Searcher searcher = new Searcher(contacts, query);
        LinkedHashMap<String, Integer> searchResults = searcher.search();
        System.out.println("Found " + searchResults.size() + " results:");
        Set<String> searchResultsKeys = searchResults.keySet();

        int index = 1;
        helpSearch.clear();
        for (String str : searchResultsKeys) {
            System.out.println((index) + ". " + str);
            helpSearch.put(index++, searchResults.get(str));
        }
    }

    /**
     * Serialize the given object to the file
     */
    public static void serialize(Object obj, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    public static List<?> deserialize(String fileName) throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        List<?> obj = (List<?>) ois.readObject();
        ois.close();
        return obj;
    }

    static void listData(List<Contacts> contacts) {
        if (contacts.isEmpty()) {
            System.out.print("No records found!");
        } else {
            int index = 0;
            for (Contacts contact : contacts) {
                System.out.println(++index + ". " + contact.listName());
            }
//            out.print("Select a record: ");
        }
    }
}

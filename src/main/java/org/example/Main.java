package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static MobilePhone mobilePhone = new MobilePhone();
    private static boolean exitKey = false;

    public static void main(String[] args) {

        printMenu();

        while (!exitKey) {
            String choice;

            while (true) {
                choice = scanner.nextLine();

                if (choice.isEmpty() || choice.trim().isEmpty() ||
                        !choice.matches("[0-8]")) {
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                } else {
                    break;
                }
            }

            switch (Integer.parseInt(choice)) {
                case 0:
                    printMenu();
                    break;
                case 1:
                    mobilePhone.printContactsList();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 2:
                    addContact();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 3:
                    modifyContactName();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 4:
                    modifyContactNumber();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 5:
                    removeContact();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 6:
                    removeContactNumber();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 7:
                    findContactInList();
                    System.out.println("\n" + Constants.ANSI_PURPLE + "Please make your choice:");
                    break;
                case 8:
                    exitApp();
                    break;
            }
        }
    }

    private static void printMenu() {

        System.out.println(Constants.ANSI_PURPLE +
                "Menu: " + "\n" +
                "0 - show menu;" + "\n" +
                "1 - show contacts list;" + "\n" +
                "2 - add new contact;" + "\n" +
                "3 - modify contact name;" + "\n" +
                "4 - modify contact phone number;" + "\n" +
                "5 - remove contact;" + "\n" +
                "6 - remove contact phone number;" + "\n" +
                "7 - find contact in list;" + "\n" +
                "8 - exit application." + "\n" +
                "\n" +
                "Please make your choice:"
        );
    }

    private static void addContact(){

        System.out.println(Constants.ANSI_YELLOW + "Enter contact details:");
        boolean exitCode = true;

        while (exitCode) {
            System.out.println(Constants.ANSI_GREEN + "Enter contact name (or type 'e' + 'Enter' to escape):");
            String contName = typeName();

            if (contName.matches("e")) {
                System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                exitCode = false;
            } else if (mobilePhone.findContact(contName).getContactName() == null ||
                    mobilePhone.findContact(contName).getContactName().matches("")) {
                System.out.println(Constants.ANSI_GREEN + "\n" +
                        "Enter at least one phone number (Type 'e' to escape, or '+' and 11 digits and hit 'Enter' to finish): ");
                ArrayList<String> contNums = new ArrayList<>();
                String contNum = "";
                boolean exitCode0 = true;

                while (exitCode0) {

                    while (true) {
                        contNum = typeNumber();
                        assert contNum != null;
                        boolean contNumEmpty = contNum.trim().isEmpty() || contNum.isEmpty();

                        if (!contNumEmpty && contNum.matches("e")) {
                            System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                            exitCode0 = false;
                            break;
                        } else if (!contNumEmpty && contNums.contains(contNum)) {
                            System.out.println(Constants.ANSI_RED +
                                    "You've already entered this phone number, enter another (or type 'e' + 'Enter' to escape): ");
                        } else if (!contNumEmpty && contNum.matches("\\+(\\d{11})") && !contNums.contains(contNum) &&
                                !mobilePhone.searchNumInList(contNum)) {
                            contNums.add(contNum);
                            System.out.println(Constants.ANSI_GREEN + "Enter next phone number: ");
                        } else if (!contNumEmpty && mobilePhone.searchNumInList(contNum)) {
                            System.out.println(Constants.ANSI_RED + "This phone number is already assigned to another contact, enter new one: ");
                        }

                        if (contNumEmpty && !contNums.isEmpty()) {
                            exitCode0 = false;
                            break;
                        }
                    }
                }

                System.out.println();

                if (!contNums.isEmpty() && !contNum.contains("e")) {
                    mobilePhone.addContact(contName, contNums);
                }
                exitCode = false;

            } else {
                System.out.println(Constants.ANSI_RED + "There is a contact with such name in the list!");
                exitCode = false;
            }
        }
    }

    private static void modifyContactName() {

        boolean exitCode0 = true;

        while (exitCode0) {
            System.out.println(Constants.ANSI_GREEN + "Enter contact name to modify (or type 'e' + 'Enter' to escape): ");
            String oldName = typeName();
            boolean oldNameEmpty = oldName.trim().isEmpty() || oldName.isEmpty();

            if (!oldNameEmpty && oldName.matches("e")) {
                System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                exitCode0 = false;
            } else if (!oldNameEmpty && mobilePhone.findContact(oldName) != null &&
                    mobilePhone.findContact(oldName).getContactName().contains(oldName)) {
                boolean exitCode1 = true;
                String newName;

                while (exitCode1) {
                    System.out.println(Constants.ANSI_GREEN + "Enter new name of the contact (or type 'e' + 'Enter' to escape): ");
                    newName = typeName();
                    boolean newNameEmpty = newName.trim().isEmpty() || newName.isEmpty();

                    if (!newNameEmpty && newName.matches("e")) {
                        System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                        exitCode0 = false;
                        exitCode1 = false;
                    } else if (!newNameEmpty && mobilePhone.findContact(newName).getContactName() == null) {
                        mobilePhone.changeContact(oldName, newName);
                        exitCode0 = false;
                        exitCode1 = false;
                    } else {
                        System.out.println(Constants.ANSI_RED + "There is a contact with this name in contact list!");
                        exitCode1 = false;
                    }
                }
            } else {
                System.out.println(Constants.ANSI_BLUE + "No contact with this name found in contact list!");
                exitCode0 = false;
            }
        }
    }

    private static void modifyContactNumber() {
        boolean exitCode2 = true;
        String contactName;
        String oldNumber;
        String newNumber;
        while (exitCode2) {
            System.out.println(Constants.ANSI_GREEN + "Enter contact name to change number (or type 'e' + 'Enter' to escape): ");
            contactName = typeName();
            boolean contactNameEmpty = contactName.trim().isEmpty() || contactName.isEmpty();
            if (!contactNameEmpty && contactName.matches("e")) {
                System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                exitCode2 = false;
            } else if (!contactNameEmpty && mobilePhone.findContact(contactName).getContactName().contains(contactName)) {
                boolean exitCode3 = true;
                while (exitCode3) {
                    System.out.println(Constants.ANSI_GREEN + "Enter contact's old number (or type 'e' + 'Enter' to escape): ");
                    oldNumber = typeNumber();
                    boolean oldNumberEmpty = oldNumber.trim().isEmpty() || oldNumber.isEmpty();
                    if (!oldNumberEmpty && oldNumber.matches("e")) {
                        System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                        exitCode2 = false;
                        exitCode3 = false;
                    } else if (!oldNumberEmpty && mobilePhone.findContact(contactName, oldNumber)) {
                        boolean exitCode4 = true;
                        while (exitCode4) {
                            System.out.println(Constants.ANSI_GREEN + "Enter contact's new number (or type 'e' + 'Enter' to escape): ");
                            newNumber = typeNumber();
                            boolean newNumberEmpty = newNumber.trim().isEmpty() || newNumber.isEmpty();
                            if (!newNumberEmpty && newNumber.matches("e")) {
                                System.out.println(Constants.ANSI_CYAN + "Ok, returning back.");
                                exitCode2 = false;
                                exitCode3 = false;
                                exitCode4 = false;
                            } else if (!newNumberEmpty && !mobilePhone.searchNumInList(newNumber)) {
                                mobilePhone.changeContact(contactName, oldNumber, newNumber);
                                exitCode2 = false;
                                exitCode3 = false;
                                exitCode4 = false;
                            } else {
                                System.out.println(Constants.ANSI_RED + "There is another contact with this number in contact list!");
                                exitCode2 = false;
                                exitCode3 = false;
                                exitCode4 = false;
                            }
                        }
                    } else {
                        System.out.println(Constants.ANSI_RED + "There is no contact with this number in contact list!");
                        exitCode3 = false;
                    }
                }
            } else {
                System.out.println(Constants.ANSI_BLUE + "There is no contact with this name found in contact list!");
                exitCode2 = false;
            }
        }

    }

    private static void removeContact() {
        System.out.println(Constants.ANSI_GREEN + "Enter contact name to remove: ");
        String removeContactName = typeName();
        mobilePhone.removeContact(removeContactName);
    }

    private static void removeContactNumber() {
        System.out.println(Constants.ANSI_GREEN + "Enter contact name: ");
        String remContName = typeName();
        System.out.println(Constants.ANSI_GREEN + "Enter contact's number to remove: ");
        String remContNum = typeNumber();
        mobilePhone.removeContact(remContName, remContNum);
    }

    private static void addNumberToContact() {
//        Add num to contact functionality here!!!
    }

    private static void findContactInList() {
        System.out.println(Constants.ANSI_GREEN + "Enter name of the contact to find: ");
        Contacts foundContact = mobilePhone.findContact(typeName());
        if (foundContact == null) {
            System.out.println(Constants.ANSI_RED + "Can't find contact!");
        } else {
            System.out.println(Constants.ANSI_GREEN + "Contact: " + foundContact.getContactName() + " numbers: " +
                    foundContact
                            .getContactNum()
                            .toString()
                            .replace("[", "")
                            .replace("]", ""));
        }
    }

    private static String typeName() {
        String name;
        while (true) {
            name = scanner.nextLine();
            if (name != null && name.matches("e")) {
                name = "e";
                break;
            } else if (name == null || name.trim().isEmpty() || name.isEmpty() ||
                    !name.matches("[A-Z][a-z]{2,20}")) {
                System.out.println(Constants.ANSI_RED + "Name must consist of capital and small letters, try again: ");
            } else  {
                break;
            }
        }
        return name;
    }

    private static String typeNumber() {
        String number;
        while (true) {
            number = scanner.nextLine();
            if (number == null || number.trim().isEmpty() || number.isEmpty()) {
                break;
            } else if (number.matches("e")) {
                number = "e";
                break;
            } else if (!number.matches("\\+(\\d{11})")) {
                System.out.println(Constants.ANSI_RED + "Number must consist of '+' and 11 digits, try again: ");
            } else {
                break;
            }
        }
        return number;
    }

    private static void exitApp() {
        System.out.println(Constants.ANSI_PURPLE + "Good-bye!");
        scanner.close();
        exitKey = true;
    }
}

package org.example;

import java.util.ArrayList;

public class MobilePhone {

    private Contacts contact = new Contacts();
    private ArrayList<Contacts> contactsList = new ArrayList<>();

    public void addContact(String name, ArrayList<String> numbers) {
        contact = new Contacts();
        contact.setContactName(name);
        contact.setContactNum(numbers);
        contactsList.add(contact);
        System.out.println(Constants.ANSI_GREEN + "Contact " + name + " added with " +
                numbers
                        .toString()
                        .replace("[", "")
                        .replace("]", "") +
                " numbers.");
    }

    public void changeContact(String oldName, String newName) {
        contact = new Contacts();
        if (searchContact(oldName) >= 0) {
            for (int i = 0; i < contactsList.size(); i++) {
                if (contactsList.get(i).getContactName().matches(oldName)) {
                    contact.setContactName(contactsList.get(i).getContactName().replace(oldName, newName));
                    contact.setContactNum(contactsList.get(i).getContactNum());
                    contactsList.set(i, contact);
                }
            }
            System.out.println(Constants.ANSI_GREEN + "Contact " + oldName + " name changed to " + newName + ".");
        } else {
            System.out.println(Constants.ANSI_RED + "Something gone wrong, no changes were made.");
        }
    }

    public void changeContact(String name, String oldNum, String newNum) {
        contact = new Contacts();
        int index = 0;
        if (searchContact(name) >= 0) {
            for (int i = 0; i < contactsList.size(); i++) {
                if (contactsList.get(i).getContactName().matches(name)) {
                    index = i;
                    ArrayList<String> tmpNums = contactsList.get(i).getContactNum();
                    tmpNums.set(tmpNums.indexOf(oldNum), tmpNums.get(tmpNums.indexOf(oldNum)).replace(oldNum, newNum));
                    contact.setContactName(contactsList.get(i).getContactName());
                    contact.setContactNum(tmpNums);
                }
            }
            contactsList.set(index, contact);
            System.out.println(Constants.ANSI_GREEN + "Contact " + contactsList.get(index).getContactName() +
                    " number " + oldNum +
                    " was changed to " + newNum);
        } else {
            System.out.println(Constants.ANSI_RED + "Something gone wrong, no changes were made.");
        }
    }

    public void removeContact(String name) {
        contact = new Contacts();
        int index = 0;
        if (searchContact(name) >= 0) {
            for (int i = 0; i < contactsList.size(); i++) {
                if (contactsList.get(i).getContactName().matches(name)) {
                    index = i;
                }
            }
            contactsList.remove(index);
            System.out.println(Constants.ANSI_GREEN + "Contact " + name + " removed!");
        } else {
            System.out.println(Constants.ANSI_RED + "Something gone wrong, no changes were made.");
        }
    }

    public void removeContact(String name, String number) {
        contact = new Contacts();
        if (searchContact(name) >= 0) {
            int removeIndex = searchContact(name);
            ArrayList<String> remContNums = contactsList.get(removeIndex).getContactNum();
            for (int i = 0; i < remContNums.size(); i++) {
                if (remContNums.get(i).matches(number)) {
                    remContNums.remove(number);
                }
            }
            contact.setContactName(contactsList.get(removeIndex).getContactName());
            contact.setContactNum(remContNums);
            contactsList.set(removeIndex, contact);
            System.out.println(Constants.ANSI_GREEN + "Contact " + name + " number " + number +
                    " removed from contact's numbers list!");
        } else {
            System.out.println(Constants.ANSI_RED + "Something gone wrong, no changes were made.");
        }
    }

    public Contacts findContact(String name) {
        contact = new Contacts();
        for (Contacts c : contactsList) {
            if (c.getContactName().contains(name)) {
                contact.setContactName(c.getContactName());
                contact.setContactNum(c.getContactNum());
            }
        }
//        System.out.println("------------------");
//        System.out.println(contact.getContactName() + " " +
//                contact.getContactNum());
//        System.out.println("------------------");
        return contact;
    }

    public boolean findContact(String name, String number) {
        contact = new Contacts();
        contact = findContact(name);
        ArrayList<String> contNums = contact.getContactNum();
        return contNums.get(contNums.indexOf(number)) == null;
    }

    public boolean searchNumInList(String number) {
        for (Contacts c : contactsList) {
            for (String s : c.getContactNum()) {
                if (s.contains(number)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printContactsList() {
        if (contactsList.size() != 0) {
            for (int i = 0; i < contactsList.size(); i++) {
                String contactName = contactsList.get(i).getContactName();
                ArrayList<String> contactNums = contactsList.get(i).getContactNum();
                System.out.println(Constants.ANSI_GREEN + "Contact № " + i + ": " + contactName + ", with numbers " +
                        contactNums.toString().replace("[", "").replace("]", "")
                    );
            }
        } else {
            System.out.println(Constants.ANSI_BLUE + "No contacts in you list!");
        }
    }

    private int searchContact(String name) {
        int index = 0;
        for (int i = 0; i < contactsList.size(); i++) {
            if (contactsList.get(i).getContactName().contains(name)) {
                index = i;
            }
        }
        return index;
    }
}

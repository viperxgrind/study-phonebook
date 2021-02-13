package org.example;

import java.util.ArrayList;

public class Contacts {
    private String contactName;
    private ArrayList<String> contactNum;

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setContactNum(ArrayList<String> contactNum) {
        this.contactNum = contactNum;
    }

    public String getContactName() {
        return contactName;
    }

    public ArrayList<String> getContactNum() {
        return contactNum;
    }
}

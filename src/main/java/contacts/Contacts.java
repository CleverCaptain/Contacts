package contacts;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contacts implements Serializable {
    protected String name;
    protected String phoneNumber = "";
    protected LocalDateTime timeCreated;
    protected LocalDateTime timeLastEdited;

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setTimeLastEdited(LocalDateTime timeLastEdited) {
        this.timeLastEdited = timeLastEdited;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        boolean isValid = checkIfValidPhoneNumber(phoneNumber);
        if (isValid) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Wrong number format!");
            this.phoneNumber = "";
        }
    }

    private boolean checkIfValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        String[] phoneNumberGrouped = phoneNumber.split("[\\s-]+");
        Pattern group1Pattern = Pattern.compile("(\\+?\\([\\da-zA-Z]+\\))|(\\+?[\\da-zA-Z]+)");
        Pattern otherGroupsPattern = Pattern.compile("(\\([\\da-zA-Z]{2,}\\))|([\\da-zA-Z]{2,})");

        boolean parenthesisFound = false;
        boolean isValid = false;
        for (int i = 0; i < phoneNumberGrouped.length; i++) {
            String g = phoneNumberGrouped[i];
            Matcher matcher;
            if (i == 0) {
                matcher = group1Pattern.matcher(g);
                isValid = matcher.matches();
                parenthesisFound = g.contains("(");
            } else {
                matcher = otherGroupsPattern.matcher(g);
                isValid = matcher.matches() && (!parenthesisFound || !g.contains("("));
                parenthesisFound = g.contains("(") || parenthesisFound;
            }
            if (!isValid) {
                return false;
            }
        }
        return isValid;
    }

    public abstract String toSearch();

    public abstract String getFullName();

    public abstract String listName();


    @Override
    public String toString() {
        return "Number: " + (phoneNumber == null ? "[no data]" : phoneNumber) + "\n" +
                "Time created: " + timeCreated.withSecond(0).withNano(0) + "\n" +
                "Time last edit: " + timeLastEdited.withSecond(0).withNano(0);
    }
}

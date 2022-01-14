package contacts;

import java.time.LocalDate;

public class PersonContacts extends Contacts {
    private String surName;
    private LocalDate birthDate;
    private String gender;

    public String toSearch() {
        return getFullName() + ", " + birthDate + ", " + gender + ", " + phoneNumber;
    }
    public String getFullName() {
        return name + " " + surName;
    }
    public String listName() {
        return name + " " + surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = checkDate(birthDate);
    }

    public void setGender(String gender) {
        boolean isValidGender = checkGender(gender);
        if (isValidGender) {
            this.gender = gender;
        } else {
            System.out.println("Bad gender!");
        }
    }
    LocalDate checkDate(String date) {
        try {
            int[] dateNum = new int[3];
            String[] split = date.split("-");
            for (int i = 0; i < split.length; i++) {
                String d = split[i];
                dateNum[i] = Integer.parseInt(d);
            }
            return LocalDate.of(dateNum[0], dateNum[1], dateNum[2]);

        } catch (Exception e) {
            System.out.println("Bad birth date!");
        }
        return null;
    }

    boolean checkGender(String g) {
        return g.equalsIgnoreCase("m") || g.equalsIgnoreCase("f");
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Surname: " + surName + "\n" +
                "Birth date: " + (birthDate == null ? "[no data]" : birthDate) + "\n" +
                "Gender: " + (gender == null ? "[no data]" : gender) + "\n" +
                super.toString();
    }

}

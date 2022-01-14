package contacts;


public class OrganizationContacts extends Contacts {

    private String address;

    public String getFullName() {
        return name;
    }

    public String listName() {
        return name;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Organization name: " + name + "\n" +
                "Address: " + address + "\n" +
                super.toString();
    }

    public String toSearch() {
        return getFullName() + ", " + address + ", " + phoneNumber;
    }
}

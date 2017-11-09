package np.org.psi.dhis2.datacapture.objects;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String employer;

    public User() {}

    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber, String employer) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.employer = employer;
    }

    public int getId() {return id;}
    public void setId(int id) {id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {password = password;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {email = email;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {phoneNumber = phoneNumber;}

    public String getEmployer() {return employer;}
    public void setEmployer(String employer) {employer = employer;}
}

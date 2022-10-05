import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;


    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID() {
        String uuid;
        Random rnd = new Random();
        int len = 8;
        boolean nonUnique;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rnd.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u : users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }

    public String getNewAccountUUID() {
        String uuid;
        Random rnd = new Random();
        int len = 10;
        boolean nonUnique;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) rnd.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account account : accounts) {
                if (uuid.compareTo(account.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);

        return uuid;
    }


    /**
     * Add an account
     * @param anAccount the account to add
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }


    /**
     * Create a new user of the bank
     * @param firstName givenName
     * @param lastName  surName
     * @param pin       the user's pin
     * @return          the new User object
     */
    public User addUser(String firstName, String lastName, String pin) {

        // create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user and add to User and Bank accounts lists
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {
        for (User u : this.users) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin));
            return u;
        }

        // if we haven't found the user or have in incorrect pin
        return null;
    }
}

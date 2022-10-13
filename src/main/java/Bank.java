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
        Random random = new Random();
        int len = 6;
        boolean nonUnique;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User user : users) {
                if (uuid.compareTo(user.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }

    public String getNewAccountUUID() {
        String uuid;
        Random random = new Random();
        int len = 10;
        boolean nonUnique;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10)).toString();
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
     * @param account the account to add
     */
    public void addAccount(Account account) {
        this.accounts.add(account);
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
        Account newAccount = new Account("Tinkoff Black", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public User userLogin(String userID, String pin) {
        for (User user : this.users) {
            if (user.getUUID().compareTo(userID) == 0 && user.validatePin(pin));
            return user;
        }
        return null; // if we haven't found the user or have in incorrect pin
    }

    public String getName() {
        return name;
    }
}

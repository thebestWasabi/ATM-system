import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuId;
    private byte pinHash[]; // MD5 hash
    private ArrayList<Account> accounts;


    /**
     * @param firstName the user's givenName
     * @param lastName the user's surName
     * @param pin the user's account pin
     * @param theBank the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught NoSuchAlgorithmException");
            ex.printStackTrace();
            System.exit(1);
        }
        uuId = theBank.getNewUserUUID();
        accounts = new ArrayList<Account>();
        System.out.printf("New user %s %s with ID %s created.\n", lastName, firstName, this.uuId);
    }

    /**
     * Add an account for the user
     * @param anAccount the account to add
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    public String getUUID() {
        return uuId;
    }


    /**
     * Check whether a given pin matches the true User pin
     * @param aPin the pin to check
     * @return whether the pin is valid or not
     */
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught NoSuchAlgorithmException");
            ex.printStackTrace();
            System.exit(1);
        }
        return false;
    }
}

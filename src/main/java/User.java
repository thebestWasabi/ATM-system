import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuID;
    private byte pinHash[]; // the MD5 hash
    private ArrayList<Account> accounts;


    /**
     * @param firstName the user's givenName
     * @param lastName  the user's surName
     * @param pin       the user's account pin
     * @param bank   the Bank object that the user is a customer of
     */
    public User(String firstName, String lastName, String pin, Bank bank) {
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
        uuID = bank.getNewUserUUID();
        accounts = new ArrayList<Account>();
        System.out.printf("New user %s %s with ID %s created.\n", lastName, firstName, this.uuID);
    }

    /**
     * Add an account for the user
     * @param anAccount the account to add
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
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

    /**
     * Print summaries for the account of this user
     */
    public void printAccountsSummary() {
        System.out.printf("\n\n%s, это список ваших карт зарегистрированных в нашем банке\n", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d) %s\n", i + 1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return accounts.size();
    }

    /**
     * Print transactions history for a particular account
     * @param accountIdx the index of the account to use
     */
    public void printAccountTransactionHistory(int accountIdx) {
        accounts.get(accountIdx).printTransHistory();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUUID() {
        return uuID;
    }

    /**
     * Get the balance account of a particular account
     * @param acctIdx the index of the account to use
     * @return the balance of the account
     */
    public double getAcctBalance(int acctIdx) {
        return accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the UUID of a particular account
     * @param accountIdx   the index of the account to use
     * @return          the UUID of the account
     */
    public String getAccountUUID(int accountIdx) {
        return accounts.get(accountIdx).getUUID();
    }

    /**
     * Add a transaction to a particular account
     * @param acctIdx   the index of the account
     * @param amount    the amount of the transaction
     * @param memo      the memo of the transaction
     */
    public void addAcctTransaction(int acctIdx, double amount, String memo) {
        accounts.get(acctIdx).addTransaction(amount, memo);
    }
}

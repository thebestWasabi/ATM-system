import java.util.ArrayList;

public class Account {

    private String name;
    private String uuId;
    private User holder;
    private ArrayList<Transaction> transactions;


    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;
        this.uuId = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Get summary line for the account
     * @return  the string summary
     */
    public String getSummaryLine() {
        double balance = this.getBalance();
        // format the summary line, depending on the whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : ₽%.02f : %s", this.uuId, balance, this.name);
        } else {
            return String.format("%s : ₽(%.02f) : %s", this.uuId, balance, this.name);
        }
    }

    /**
     * Get the balance of this account by adding the amounts of the transactions
     * @return  the balance value
     */
    private double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public String getUUID() {
        return uuId;
    }
}

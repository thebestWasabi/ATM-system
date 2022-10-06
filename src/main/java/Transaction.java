import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account inAccount;


    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        timestamp = new Date();
        memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;

    }


    /**
     * Get the amount of the transaction
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }
}

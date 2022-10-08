import java.util.Date;

public class Transaction {

    private double amount;
    private Date timestamp;
    private String memo;
    private Account account;


    public Transaction(double amount, Account account) {
        this.amount = amount;
        this.account = account;
        timestamp = new Date();
        memo = "";
    }

    public Transaction(double amount, String memo, Account account) {
        this(amount, account);
        this.memo = memo;

    }

    /**
     * Get a string summarizing the transaction
     * @return the summary string
     */
    public String getSummaryLine() {
        if (amount >= 0) {
            return String.format("%s : ₽%.02f : %s\n", timestamp.toString(), amount, memo);
        } else {
            return String.format("%s : ₽(%.02f) : %s\n", timestamp.toString(), amount, memo);
        }
    }

    /**
     * Get the amount of the transaction
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }
}

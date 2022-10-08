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
            return String.format("%s : ₽%.02f : %s", uuId, balance, name);
        } else {
            return String.format("%s : ₽(%.02f) : %s", uuId, balance, name);
        }
    }

    /**
     * Print the transaction history of the account
     */
    public void printTransHistory() {
        System.out.printf("\nИстория транзакций по карте %s\n", uuId);
        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.print(transactions.get(i).getSummaryLine());
        }
    }

    /**
     * Get the balance of this account by adding the amounts of the transactions
     * @return  the balance value
     */
    public double getBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public String getUUID() {
        return uuId;
    }

    /**
     * Add a new transaction in the account
     * @param amount    the amount transacted
     * @param memo      the transaction memo
     */
    public void addTransaction(double amount, String memo) {
        // create new transaction object and add it to our list
        Transaction newTransaction = new Transaction(amount, memo,this);
        transactions.add(newTransaction);
    }
}

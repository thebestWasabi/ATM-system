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

    public String getUUID() {
        return uuId;
    }
}

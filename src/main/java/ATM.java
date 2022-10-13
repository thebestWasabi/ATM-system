import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("TinkOff");
        User aUser = theBank.addUser("Максим", "Лесник", "1234");
        Account newAccount = new Account("Tinkoff plus Yandex", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {
            curUser = ATM.mainMenuPrompt(theBank, sc);
            ATM.printUserMenu(curUser, sc);
        }
    }

    /**
     * Print the ATM's login menu
     * @param bank the Bank object whose account to use
     * @param sc   the Scanner object to use for user input
     * @return the authenticated User object
     */
    private static User mainMenuPrompt(Bank bank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nЗдравствуйте, вас приветствует банкомат %s\n\n", bank.getName());
            System.out.print("Введите ID аккаунта: ");
            userID = sc.nextLine();
            System.out.print("Введите пин: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = bank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Ошибка, неверна комбинация ID/пин, пожалуйста попробйте еще раз");
            }
        } while (authUser == null); // continue looping until successful login!

        return authUser;
    }

    private static void printUserMenu(User user, Scanner sc) {
        user.printAccountsSummary();

        int choice;
        do {
            System.out.printf("Здравствуйте, %s, выберите действие из меню:\n", user.getFirstName());
            System.out.println(" 1. Посмотреть историю транзакций");
            System.out.println(" 2. Снять наличные");
            System.out.println(" 3. Положить наличные");
            System.out.println(" 4. Перевести средства на другой счет");
            System.out.println(" 5. Выход");
            System.out.println();
            System.out.print("Введите номер из списка сюда: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Ошибка ввода, введен неверный номер, введите номер от 1 до 5");
            }
        } while (choice < 1 || choice > 5);

        switch (choice) {
            case 1 -> ATM.showTransactionHistory(user, sc);
            case 2 -> ATM.withdrawFunds(user, sc);
            case 3 -> ATM.depositFunds(user, sc);
            case 4 -> ATM.transferFunds(user, sc);
            case 5 -> sc.nextLine(); // rest of the previous input
        }
        if (choice != 5) {
            printUserMenu(user, sc);
        }
    }

    /**
     * Show the transaction history for an account
     * @param user the logged-in User object
     * @param sc   the scanner object used for user input
     */
    private static void showTransactionHistory(User user, Scanner sc) {
        int theAcct;
        do {
            System.out.printf("Введите карту, по которой хотите посмотреть историю транзакций (1-%d): \n", user.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= user.numAccounts()) {
                System.out.println("Такой карты не существует. Введите корректный номер");
            }
        } while (theAcct < 0 || theAcct >= user.numAccounts());

        user.printAccountTransactionHistory(theAcct);
    }

    /**
     * Process transferring funds from one account to another
     * @param user the logged-in User object
     * @param sc   the Scanner object used for user input
     */
    private static void transferFunds(User user, Scanner sc) {
        int fromAccount;
        int toAccount;
        double amount;
        double accountBalance;

        // get the account for transfer form
        do {
            System.out.printf("Введите карту с которой хотите перевести деньги (1-%d): \n", user.numAccounts());
            fromAccount = sc.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Такой карты не существует. Введите корректный номер");
            }
        } while (fromAccount < 0 || fromAccount >= user.numAccounts());
        accountBalance = user.getAcctBalance(fromAccount);

        // get the account to transfer
        do {
            System.out.printf("Введите карту на которую хотите перевести деньги (1-%d): \n", user.numAccounts());
            toAccount = sc.nextInt() - 1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Такой карты не существует. Введите корректный номер");
            }
        } while (toAccount < 0 || toAccount >= user.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Введите сумму для перевода между счетами (max ₽%.02f) : ₽", accountBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Ошибка, сумма должна быть выше нуля");
            } else if (amount > accountBalance) {
                System.out.printf("Ошибка, сумма не может привышать ₽%.02f", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        // finally, do the transfer
        user.addAcctTransaction(fromAccount, -1 * amount, String.format(
                "Перевод на карту %s", user.getAccountUUID(toAccount)));
        user.addAcctTransaction(toAccount, amount, String.format(
                "Перевод с карты %s", user.getAccountUUID(fromAccount)));
    }

    /**
     * Process a fund withdraw from an account
     * @param user  the logged-in User object
     * @param sc    the Scanner object user from user input
     */
    public static void withdrawFunds(User user, Scanner sc) {
        int fromAccount;
        double amount;
        double accountBalance;
        String memo;

        // get the account for transfer form
        do {
            System.out.printf("Введите карту с которой хотите снять наличные (1-%d): \n", user.numAccounts());
            fromAccount = sc.nextInt() - 1;
            if (fromAccount < 0 || fromAccount >= user.numAccounts()) {
                System.out.println("Такой карты не существует. Введите корректный номер");
            }
        } while (fromAccount < 0 || fromAccount >= user.numAccounts());
        accountBalance = user.getAcctBalance(fromAccount);

        do {
            System.out.printf("Введите сумму для снятия(max ₽%.02f) : ₽", accountBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Ошибка, сумма должна быть выше нуля");
            } else if (amount > accountBalance) {
                System.out.printf("Ошибка, сумма не может привышать ₽%.02f", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        // rest of the previous input
        sc.nextLine();

        // get a memo
        System.out.print("Введите коментарий: ");
        memo = sc.nextLine();

        // do the withdrawFunds
        user.addAcctTransaction(fromAccount, -1 * amount, memo);
    }

    /**
     * Process a fund deposit to an account
     * @param user  the logged-in User object
     * @param sc    the Scanner object used for user input
     */
    public static void depositFunds(User user, Scanner sc) {
        int toAccount;
        double amount;
        double accountBalance;
        String memo;

        // get the account for transfer form
        do {
            System.out.printf("Введите карту на которую хотите положить деньги (1-%d): \n", user.numAccounts());
            toAccount = sc.nextInt() - 1;
            if (toAccount < 0 || toAccount >= user.numAccounts()) {
                System.out.println("Такой карты не существует. Введите корректный номер");
            }
        } while (toAccount < 0 || toAccount >= user.numAccounts());
        accountBalance = user.getAcctBalance(toAccount);

        do {
            System.out.printf("Введите сумму(max ₽%.02f) : ₽", accountBalance);
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Ошибка, сумма должна быть выше нуля");
            }
        } while (amount < 0);

        // rest of the previous input
        sc.nextLine();

        // get a memo
        System.out.print("Введите коментарий: ");
        memo = sc.nextLine();

        // do the withdrawFunds
        user.addAcctTransaction(toAccount, amount, memo);
    }
}

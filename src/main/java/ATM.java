import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("TinOff");
        User aUser = theBank.addUser("Daria", "Skvortsova", "1234");
        Account newAccount = new Account("Checking", aUser, theBank);
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
     *
     * @param bank the Bank object whose account to use
     * @param sc   the Scanner object to use for user input
     * @return the authenticated User object
     */
    private static User mainMenuPrompt(Bank bank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        do { // prompt the user for user ID/pin combo until a correct one is reached
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.println("Enter user ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter pin; ");
            pin = sc.nextLine();

            authUser = bank.userLogin(userID, pin); // try to get the user object corresponding to the ID and pin combo
            if (authUser == null) {
                System.out.println("Incorrect userID/pin combination. Please, try again");
            }
        } while (authUser == null); // continue looping until successful login!

        return authUser;
    }

    private static void printUserMenu(User user, Scanner sc) {
        user.printAccountsSummary();

        int choice;
        do {
            System.out.printf("Здравствуйте, %s, выберите действие из меню: ", user.getFirstName());
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
            case 2 -> ATM.withdrawlFunds(user, sc);
            case 3 -> ATM.depositFunds(user, sc);
            case 4 -> ATM.transferFunds(user, sc);
        }
        if (choice != 5) {
            printUserMenu(user, sc);
        }
    }

    private static void showTransactionHistory(User user, Scanner sc) {
        int theAcct;
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "whose transactions you want to see: ", user.numAccounts());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= user.numAccounts()) {
                System.out.println("Invalid account. Pls try again");
            }
        } while (theAcct < 0 || theAcct >= user.numAccounts());

        user.printAcctTransHistory(theAcct);
    }
}

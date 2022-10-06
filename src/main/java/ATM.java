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
     * @param theBank the Bank object whose account to use
     * @param sc      the Scanner object to use for user input
     * @return the authenticated User object
     */
    private static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        // prompt the user for user ID/pin combo until a correct one is reached
        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.println("Enter user ID: ");
            userID = sc.nextLine();
            System.out.printf("Enter pin; ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect userID/pin combination. Please, try again");
            }
        } while (authUser == null); // continue looping until successful login!

        return authUser;
    }

    private static void printUserMenu(User theUser, Scanner sc) {
        theUser.printAccountsSummary();

        int choice;
        do {
            System.out.printf("Здравствуйте, %s, выберите действие из меню: ", theUser.getFirstName());
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
            case 1 -> ATM.showTransactionHistory(theUser, sc);
            case 2 -> ATM.withdrawlFunds(theUser, sc);
            case 3 -> ATM.depositFunds(theUser, sc);
            case 4 -> ATM.transferFunds(theUser, sc);
        }

        if (choice != 5) {
            printUserMenu(theUser, sc);
        }
    }
}

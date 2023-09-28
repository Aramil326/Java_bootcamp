import java.util.Scanner;
import java.util.UUID;

public class Menu {
    TransactionsService facade = new TransactionsService();

    public void start(boolean developMode) {
        int userCommand = 0;
        int finishExecution = developMode ? 7 : 5;

        while (userCommand != finishExecution) {
            if (developMode) {
                setDevelopModeMenu();
                userCommand = getUserAnswer(developMode);
                processTheCommand(userCommand, developMode);
            } else {
                setNormalModeMenu();
                userCommand = getUserAnswer(developMode);
                processTheCommand(userCommand, developMode);
            }

        }
    }

    private void setDevelopModeMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV – remove a transfer by ID");
        System.out.println("6. DEV – check transfer validity");
        System.out.println("7. Finish execution");
    }

    private void setNormalModeMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. Finish execution");
    }

    private int getUserAnswer(boolean developMode) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        int userCommand = 0;
        try {
            userCommand = Integer.parseInt(input);
            if (developMode && (userCommand <= 0 || userCommand > 7)) {
                throw new RuntimeException("Invalid action.");
            } else if (!developMode && (userCommand <= 0 || userCommand > 5)) {
                throw new RuntimeException("Invalid action.");
            }
        } catch (RuntimeException e) {
            System.out.println(e);
            userCommand = getUserAnswer(developMode);
        }
        return userCommand;
    }

    private void processTheCommand(int command, boolean developMode) {
        switch (command) {
            case 1:
                addUser();
                break;
            case 2:
                viewBalance();
                break;
            case 3:
                performTransfer();
                break;
            case 4:
                viewTransactionsByUser();
                break;
            case 5:
                if (developMode)
                    removeTransfer();
                break;
            case 6:
                if (developMode)
                    checkTransferValidity();
                break;
            case 7:
                break;
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();

        try {
            int balance = Integer.parseInt(scanner.next().trim());
            User newUser = new User(name, balance);
            facade.userAdd(newUser);
            System.out.println("User with id = " + newUser.getId() + " is added");
            System.out.println("---------------------------------------------------------");
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    private void viewBalance() {
        System.out.println("Enter a user id");
        Scanner scanner = new Scanner(System.in);

        try {
            int id = Integer.parseInt(scanner.next().trim());
            int balance;
            try {
                balance = facade.getUserBalance(id);
                System.out.println(facade.userList.getUserById(id).getName() + " - " + balance);
                System.out.println("---------------------------------------------------------");
            } catch (UserNotFoundException e) {
                System.out.println(e);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        Scanner scanner = new Scanner(System.in);

        try {
            int userId1 = Integer.parseInt(scanner.next().trim());
            int userId2 = Integer.parseInt(scanner.next().trim());
            int amount = Integer.parseInt(scanner.next().trim());
            try {
                facade.addTransaction(userId2, userId1, amount);
                System.out.println("The transfer is completed");
                System.out.println("---------------------------------------------------------");
            } catch (IllegalTransactionException e) {
                System.out.println(e);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    private void viewTransactionsByUser() {
        System.out.println("Enter a user ID");
        Scanner scanner = new Scanner(System.in);

        try {
            int id = Integer.parseInt(scanner.next().trim());
            try {
                Transaction[] transactions = facade.getUserTransactionsArray(id);
                for (Transaction transaction : transactions) {
                    System.out.println("To " + transaction.getRecipient().getName() + "(id = " + transaction.getRecipient().getId() + ") " +
                            transaction.getTransferAmount() + " with id = " + transaction.getId());
                }
                System.out.println("---------------------------------------------------------");
            } catch (UserNotFoundException e) {
                System.out.println(e);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    private void removeTransfer() {
        System.out.println("Enter a user ID and a transfer ID");
        Scanner scanner = new Scanner(System.in);

        try {
            int userId = Integer.parseInt(scanner.next().trim());
            UUID transactionId = UUID.fromString(scanner.next());
            try {
                Transaction removedTransaction = facade.removeTransaction(transactionId, userId);
                System.out.print("Transfer");
                if (removedTransaction.getTransferCategory() == Transaction.Category.DEBIT) {
                    System.out.print(" From ");
                } else {
                    System.out.print(" To ");
                }
                System.out.print(removedTransaction.getRecipient().getName() +
                        "(id = " + removedTransaction.getRecipient().getId() + ") " +
                        Math.abs(removedTransaction.getTransferAmount()));
                System.out.println(" removed");
                System.out.println("---------------------------------------------------------");
            } catch (TransactionNotFoundException e) {
                System.out.println(e);
            }
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }

    private void checkTransferValidity() {
        Transaction[] unpairedTransactions = facade.getUnpairedTransactions();
        if (unpairedTransactions.length == 0) {
            System.out.println("Everything is fine");
        } else {
            for (Transaction transaction : unpairedTransactions) {
                System.out.println(transaction.getRecipient().getName() + "(id = " +
                        transaction.getRecipient().getId() + ") has an unacknowledged transfer id = " +
                        transaction.getId() + " from " +
                        transaction.getSender().getName() + "(id = " +
                        transaction.getSender().getId() + ") for " +
                        Math.abs(transaction.getTransferAmount()));
            }
        }
        System.out.println("---------------------------------------------------------");
    }
}

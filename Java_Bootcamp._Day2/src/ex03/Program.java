import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Alex", 10000);
        User user2 = new User("John", 50000);

        TransactionsLinkedList listOfTransactionUser1 = user1.getTransactionsList();
        TransactionsLinkedList listOfTransactionUser2 = user2.getTransactionsList();

        for (int i = 0; i < 5; i++) {
            listOfTransactionUser1.addTransaction(new Transaction(user1, user2, Transaction.Category.CREDIT, -i * 10));
        }
        for (int i = 0; i < 6; i++) {
            listOfTransactionUser1.addTransaction(new Transaction(user1, user2, Transaction.Category.DEBIT, i * 10));
        }

        for (int i = 0; i < 7; i++) {
            listOfTransactionUser2.addTransaction(new Transaction(user2, user1, Transaction.Category.CREDIT, -i * 10));
        }
        for (int i = 0; i < 5; i++) {
            listOfTransactionUser2.addTransaction(new Transaction(user2, user1, Transaction.Category.DEBIT, i * 10));
        }

        Transaction transactionToRemove1 = new Transaction(user1, user2, Transaction.Category.DEBIT, 50);
        Transaction transactionToRemove2 = new Transaction(user1, user2, Transaction.Category.CREDIT, -50);

        listOfTransactionUser1.addTransaction(transactionToRemove1);
        listOfTransactionUser1.addTransaction(transactionToRemove2);

        listOfTransactionUser2.addTransaction(transactionToRemove1);
        listOfTransactionUser2.addTransaction(transactionToRemove2);

        System.out.println("Number of transactions of the first user: " + user1.getTransactionsList().getSize());
        System.out.println("Number of transactions of the second user: " + user2.getTransactionsList().getSize());
        System.out.println();

        System.out.println("Delete 2 transactions for first user");
        listOfTransactionUser1.removeTransactionById(transactionToRemove1.getId());
        listOfTransactionUser1.removeTransactionById(transactionToRemove2.getId());

        System.out.println("Delete 2 transactions for second user");
        listOfTransactionUser2.removeTransactionById(transactionToRemove1.getId());
        listOfTransactionUser2.removeTransactionById(transactionToRemove2.getId());
        System.out.println();

        System.out.println("Number of transactions of the first user: " + user1.getTransactionsList().getSize());
        System.out.println("Number of transactions of the second user: " + user2.getTransactionsList().getSize());
        System.out.println();

        System.out.println("Delete non-existent operation: ");
        try {
            listOfTransactionUser1.removeTransactionById(UUID.randomUUID());
        } catch (TransactionNotFoundException e) {
            System.out.println(e);
        }
        System.out.println();

        System.out.println("Displaying all transactions for the first user: ");
        Transaction[] arrayOfTransaction1 = listOfTransactionUser1.toArray();

        for (Transaction transaction : arrayOfTransaction1) {
            System.out.println(transaction);
        }
        System.out.println();

        System.out.println("Displaying all transactions for the second user: ");
        Transaction[] arrayOfTransaction2 = listOfTransactionUser2.toArray();

        for (Transaction transaction : arrayOfTransaction2) {
            System.out.println(transaction);
        }
    }
}

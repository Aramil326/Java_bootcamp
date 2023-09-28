import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        TransactionsService facade = new TransactionsService();

        User user1 = new User("user1", 123000);
        User user2 = new User("user2", 321000);
        User user3 = new User("user3", 456000);
        User user4 = new User("user4", 654000);

        System.out.println("Users created:");
        System.out.println(user1 + "\n");
        System.out.println(user2 + "\n");
        System.out.println(user3 + "\n");
        System.out.println(user4 + "\n");

        facade.userAdd(user1);
        facade.userAdd(user2);
        facade.userAdd(user3);
        facade.userAdd(user4);

        System.out.println("Get users balance from facade:");
        System.out.println(user1.getName() + " balance: " + facade.getUserBalance(user1.getId()));
        System.out.println(user2.getName() + " balance: " + facade.getUserBalance(user2.getId()));
        System.out.println(user3.getName() + " balance: " + facade.getUserBalance(user3.getId()));
        System.out.println(user4.getName() + " balance: " + facade.getUserBalance(user4.getId()) + "\n");

        for (int i = 0; i < 3; i++) {
            facade.addTransaction(user1.getId(), user2.getId(), 10 * i);
        }
        for (int i = 0; i < 3; i++) {
            facade.addTransaction(user2.getId(), user1.getId(), 10 * i);
        }
        for (int i = 0; i < 3; i++) {
            facade.addTransaction(user3.getId(), user4.getId(), 10 * i);
        }
        for (int i = 0; i < 3; i++) {
            facade.addTransaction(user4.getId(), user3.getId(), 10 * i);
        }

        System.out.println("Get users balance from facade after transactions:");
        System.out.println(user1.getName() + " balance: " + facade.getUserBalance(user1.getId()));
        System.out.println(user2.getName() + " balance: " + facade.getUserBalance(user2.getId()));
        System.out.println(user3.getName() + " balance: " + facade.getUserBalance(user3.getId()));
        System.out.println(user4.getName() + " balance: " + facade.getUserBalance(user4.getId()) + "\n");

        System.out.println("Get user1 transaction from facade:");
        Transaction[] user1Tr = facade.getUserTransactionsArray(user1);
        for (int i = 0; i < user1Tr.length; i++) {
            System.out.println(i + ": " + user1Tr[i]);
        }
        System.out.println();

        System.out.println("Get user2 transaction from facade:");
        Transaction[] user2Tr = facade.getUserTransactionsArray(user2);
        for (int i = 0; i < user2Tr.length; i++) {
            System.out.println(i + ": " + user2Tr[i]);
        }
        System.out.println();

        System.out.println("Get user3 transaction from facade:");
        Transaction[] user3Tr = facade.getUserTransactionsArray(user3);
        for (int i = 0; i < user3Tr.length; i++) {
            System.out.println(i + ": " + user3Tr[i]);
        }
        System.out.println();

        System.out.println("Get user4 transaction from facade:");
        Transaction[] user4Tr = facade.getUserTransactionsArray(user4);
        for (int i = 0; i < user4Tr.length; i++) {
            System.out.println(i + ": " + user4Tr[i]);
        }
        System.out.println();

        System.out.println("Removed 4 operations");
        facade.removeTransaction(user1.getTransactionsList().toArray()[0].getId(), user1.getId());
        facade.removeTransaction(user2.getTransactionsList().toArray()[1].getId(), user2.getId());
        facade.removeTransaction(user3.getTransactionsList().toArray()[2].getId(), user3.getId());
        facade.removeTransaction(user4.getTransactionsList().toArray()[3].getId(), user4.getId());

        System.out.println("Get one occurrence transactions");
        Transaction[] unpairedTransactions = facade.getUnpairedTransactions();
        for (Transaction unpairedTransaction : unpairedTransactions) {
            System.out.println(unpairedTransaction);
        }
        System.out.println();

        System.out.println("Let's try to make the operation so that there is not enough money: ");
        try {
            facade.addTransaction(user1.getId(), user2.getId(), 10000000);
        } catch (IllegalTransactionException e) {
            System.out.println(e);
        }
    }
}

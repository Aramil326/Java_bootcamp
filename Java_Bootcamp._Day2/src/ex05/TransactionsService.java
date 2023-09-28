import java.util.UUID;

public class TransactionsService {
    UsersList userList = new UsersArrayList();

    public void userAdd(User user) {
        userList.addNewUser(user);
    }

    public Integer getUserBalance(User user) {
        for (int i = 0; i < userList.getUsersNumber(); i++) {
            if (user == userList.getUserByIndex(i)) {
                return userList.getUserByIndex(i).getBalance();
            }
        }
        throw new UserNotFoundException("User not found");
    }

    public Integer getUserBalance(int id) {
        return userList.getUserById(id).getBalance();
    }

    public void addTransaction(int recipientId, int senderId, int amount) {
        User recipient = userList.getUserById(recipientId);
        User sender = userList.getUserById(senderId);

        if (recipientId == senderId || amount < 0 || sender.getBalance() < amount) {
            throw new IllegalTransactionException("The transfer amount is greater than the balance on the sender's account or the transfer amount is less than zero or the sender is equal to the recipient");
        }

        Transaction debit = new Transaction(recipient, sender, Transaction.Category.DEBIT, amount);
        Transaction credit = new Transaction(recipient, sender, Transaction.Category.CREDIT, -amount);

        credit.setId(debit.getId());

        recipient.getTransactionsList().addTransaction(debit);
        sender.getTransactionsList().addTransaction(credit);

        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }

    public Transaction[] getUserTransactionsArray(int id) {
        return userList.getUserById(id).getTransactionsList().toArray();
    }

    public Transaction[] getUserTransactionsArray(User user) {
        return userList.getUserById(user.getId()).getTransactionsList().toArray();
    }

    public Transaction removeTransaction(UUID transactionId, int userId) {
        return userList.getUserById(userId).getTransactionsList().removeTransactionById(transactionId);
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsList allTransactions = new TransactionsLinkedList();

        for (int i = 0; i < userList.getUsersNumber(); i++) {
            User curUser = userList.getUserByIndex(i);
            if (curUser != null) {
                int curUserTransactionsSize = curUser.getTransactionsList().getSize();
                for (int j = 0; j < curUserTransactionsSize; j++) {
                    allTransactions.addTransaction(curUser.getTransactionsList().toArray()[j]);
                }
            }
        }

        Transaction[] allTransactionsInArray = allTransactions.toArray();
        TransactionsList result = new TransactionsLinkedList();

        int size = allTransactions.toArray().length;
        for (int i = 0; i < size; i++) {
            int count = 0;
            for (int j = 0; j < size; j++) {
                if (allTransactionsInArray[i].getId() == allTransactionsInArray[j].getId()) {
                    count++;
                }
            }
            if (count != 2) {
                result.addTransaction(allTransactionsInArray[i]);
            }
        }
        return result.toArray();
    }
}

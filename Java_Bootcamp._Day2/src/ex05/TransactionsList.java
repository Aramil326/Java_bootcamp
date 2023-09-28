import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction newTransaction);

    Transaction removeTransactionById(UUID id);

    Transaction[] toArray();
}

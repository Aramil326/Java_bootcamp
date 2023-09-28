import java.util.UUID;

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private Integer transferAmount;

    public Transaction(User recipient, User sender, Category transferCategory, Integer transferAmount) {
        if ((transferCategory == Category.DEBIT && transferAmount < 0) ||
                (transferCategory == Category.CREDIT && transferAmount > 0)) {
            System.err.println("Illegal argument");
        } else if ((transferCategory == Category.DEBIT && transferAmount > sender.getBalance()) ||
                (transferCategory == Category.CREDIT && -transferAmount > sender.getBalance())) {
            System.err.println("Not enough money to transfer");
        } else {
            this.id = UUID.randomUUID();
            this.recipient = recipient;
            this.sender = sender;
            this.transferCategory = transferCategory;
            setTransferAmount(transferAmount);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(Category transferCategory) {
        this.transferCategory = transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return "recipient:\t" + recipient.getName() + "\t=> sender:\t" + sender.getName() + ";\ttransferCategory:\t" +
                transferCategory + ";\ttransferAmount:\t" + transferAmount;
    }

    public enum Category {
        DEBIT, CREDIT
    }
}

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node first;
    private Node last;
    private int size;

    TransactionsLinkedList() {
    }

    @Override
    public void addTransaction(Transaction newTransaction) {
        last = new Node(newTransaction, this.last, null);
        if (size == 0) {
            first = last;
        }
        if (size > 0) {
            last.prev.next = last;
        }
        size++;
    }

    @Override
    public void removeTransactionById(UUID id) {
        if (size == 0 || id == null) {
            throw new TransactionNotFoundException("remove a transaction with non-existent ID");
        }
        for (Node curElem = first; curElem != null; curElem = curElem.next) {
            if (curElem.elem.getId() == id && curElem.elem != null) {
                removeLinks(curElem);
                size--;
                return;
            }
        }
        throw new TransactionNotFoundException("remove a transaction with non-existent ID");
    }

    private void removeLinks(Node curElem) {
        if (curElem.prev != null && curElem.next != null) {
            curElem.prev.next = curElem.next;
            curElem.next.prev = curElem.prev;
        } else if (curElem.prev != null && curElem.next == null) {
            last = curElem.prev;
            last.next = null;
        } else if (curElem.prev == null && curElem.next != null) {
            first = curElem.next;
            first.prev = null;
        } else if (curElem.prev == null && curElem.next == null) {
            first = null;
            last = null;
        }
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] transactionsArr = new Transaction[size];
        int i = 0;
        for (Node curElem = first; curElem != null; curElem = curElem.next, i++) {
            transactionsArr[i] = curElem.elem;
        }
        return transactionsArr;
    }

    public int getSize() {
        return size;
    }

    private static class Node {
        Transaction elem;
        Node next;
        Node prev;

        Node(Transaction elem, Node prev, Node next) {
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }
    }
}

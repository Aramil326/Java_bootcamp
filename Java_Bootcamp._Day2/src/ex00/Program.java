public class Program {
    public static void main(String[] args) {
        User testUser1 = new User(1, "User_1", 156);
        User testUser2 = new User(2, "User_2", 651);
        testUser1.setBalance(900);
        testUser2.setBalance(15900);

        User testUser3 = new User();
        User testUser4 = new User();
        testUser3.setId(3);
        testUser3.setName("User_3");
        testUser3.setBalance(9979);

        testUser4.setId(4);
        testUser4.setName("User_4");
        testUser4.setBalance(9999);

        System.out.println("Users info:");
        System.out.println(testUser1);
        System.out.println(testUser2);
        System.out.println(testUser3);
        System.out.println(testUser4);

        Transaction testTransaction1 = new Transaction(testUser1, testUser2, Transaction.Category.CREDIT, -250);
        Transaction testTransaction2 = new Transaction(testUser2, testUser1, Transaction.Category.DEBIT, 250);
        Transaction testTransaction3 = new Transaction(testUser3, testUser4, Transaction.Category.CREDIT, -777);
        Transaction testTransaction4 = new Transaction(testUser4, testUser3, Transaction.Category.DEBIT, 999);

        System.out.println("\nTransactions info:");
        System.out.println(testTransaction1);
        System.out.println(testTransaction2);
        System.out.println(testTransaction3);
        System.out.println(testTransaction4);
    }
}

public class User {
    private Integer id;
    private String name;
    private Integer balance;

    public User() {
        id = 0;
        name = "";
        balance = 0;
    }

    public User(String name, Integer balance) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance < 0 ? 0 : balance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance < 0 ? 0 : balance;
    }

    @Override
    public String toString() {
        return "name: " + name + "\tid: " + id + "\tbalance: " + balance;
    }
}

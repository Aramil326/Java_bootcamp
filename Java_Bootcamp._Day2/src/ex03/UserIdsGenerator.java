public class UserIdsGenerator {
    private static final UserIdsGenerator INSTANCE = new UserIdsGenerator();
    private static Integer id = 0;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return INSTANCE;
    }

    public static Integer generateId() {
        return ++id;
    }
}

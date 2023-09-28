public interface UsersList {
    void addNewUser(User user);

    User getUserById(Integer id);

    User getUserByIndex(Integer index);

    Integer getUsersNumber();
}

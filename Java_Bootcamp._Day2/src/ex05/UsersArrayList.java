import java.util.Objects;

public class UsersArrayList implements UsersList {
    private User[] users;
    private Integer size;
    private Integer usersNumber;

    public UsersArrayList() {
        users = new User[10];
        size = 10;
        usersNumber = 0;
    }

    @Override
    public void addNewUser(User newUser) {
        if (Objects.equals(size, usersNumber)) {
            User[] newUsers = new User[size * 2];
            for (int i = 0; i < users.length; i++) {
                newUsers[i] = users[i];
            }
            size = size * 2;
            users = newUsers;
        }
        users[usersNumber++] = newUser;
    }

    @Override
    public User getUserById(Integer id) {
        for (int i = 0; i < usersNumber; i++) {
            if (Objects.equals(users[i].getId(), id)) {
                return users[i];
            }
        }
        throw new UserNotFoundException("User with this identifier does not exist");
    }

    @Override
    public User getUserByIndex(Integer index) {
        if (index < usersNumber && index >= 0) {
            return users[index];
        }
        throw new UserNotFoundException("User with this index does not exist");
    }

    @Override
    public Integer getUsersNumber() {
        return usersNumber;
    }
}

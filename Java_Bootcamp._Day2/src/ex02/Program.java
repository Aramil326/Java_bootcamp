public class Program {
    public static void main(String[] args) {
        UsersList listOfUsers = new UsersArrayList();
        for (int i = 0; i < 30; i++) {
            listOfUsers.addNewUser(new User("User" + i, 0));
        }

        for (int i = 0; i < 30; i++) {
            System.out.println("getUser: " + listOfUsers.getUserByIndex(i).getName());
        }

        System.out.println("\n" + "users number: " + listOfUsers.getUsersNumber());

        System.out.println("\ngetUserById: " + listOfUsers.getUserById(listOfUsers.getUserByIndex(0).getId()).getName() + "\n");

        try {
            System.out.println(listOfUsers.getUserByIndex(30));
        } catch (UserNotFoundException e) {
            System.out.println(e);
        }

        try {
            System.out.println(listOfUsers.getUserById(-300));
        } catch (UserNotFoundException e) {
            System.out.println(e);
        }
    }
}

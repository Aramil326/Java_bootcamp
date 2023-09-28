package edu.school21.models;

public class User {
    private final String name;
    private final String surname;
    private Integer age;
    private Boolean havePet;

    public User() {
        name = "defaultName";
        surname = "defaultSurname";
        age = 0;
        havePet = false;
    }

    public User(String name, String surname, Integer age, Boolean havePet) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.havePet = havePet;
    }

    public int incrementAge() {
        return ++age;
    }

    public boolean toggleHavePet() {
        havePet = !havePet;
        return havePet;
    }


    @Override
    public String toString() {
        return "User[" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", havePet=" + havePet +
                ']';
    }
}

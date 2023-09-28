Reflection

Сегодня я ознакомился с механизмом рефлексии.

# Содержание

1. [Exercise 00 – Work with Classes](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day8/README.md#exercise-00--work-with-classes-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)
2. [Exercise 01 – Annotations – SOURCE](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day8/README.md#exercise-01--annotations--source-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)


## Exercise 00 – Work with Classes ([директория задания](src/ex00))

| Exercise 00: Work with Classes |                    |
|--------------------------------|--------------------|
| Директория сдачи	              | ex00               |
| Файлы сдачи                    | 	Reflection-folder |

Теперь вам нужно реализовать проект Maven, который взаимодействует с классами вашего приложения. Нам нужно создать как минимум два класса, каждый из которых имеет:
- частные поля (поддерживаемые типы: String, Integer, Double, Boolean, Long)
- публичные методы
- пустой конструктор
- конструктор с параметром
- метод toString()

В этой задаче вам не нужно реализовывать методы get/set. Вновь создаваемые классы должны находиться в отдельном пакете классов (этот пакет может находиться в других пакетах). Предположим, что в приложении есть классы User и Car. Класс пользователя описан ниже:

```java
public class User {
   private String firstName;
   private String lastName;
   private int height;

   public User() {
       this.firstName = "Default first name";
       this.lastName = "Default last name";
       this.height = 0;
   }

   public User(String firstName, String lastName, int height) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.height = height;
   }

   public int grow(int value) {
       this.height += value;
       return height;
   }

   @Override
   public String toString() {
       return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
               .add("firstName='" + firstName + "'")
               .add("lastName='" + lastName + "'")
               .add("height=" + height)
               .toString();
   }
}
```

Реализованное приложение должно работать следующим образом:

- Предоставьте информацию о классе в пакете классов.
- Разрешить пользователю создавать объекты указанного класса с определенными значениями полей.
- Отображение информации о созданном объекте класса.
- Вызов методов класса.

Пример работы программы:

```
Classes:
  - User
  - Car
---------------------
Enter class name:
-> User
---------------------
fields:
	String firstName
	String lastName
	int height
methods:
	int grow(int)
---------------------
Let’s create an object.
firstName:
-> UserName
lastName:
-> UserSurname
height:
-> 185
Object created: User[firstName='UserName', lastName='UserSurname', height=185]
---------------------
Enter name of the field for changing:
-> firstName
Enter String value:
-> Name
Object updated: User[firstName='Name', lastName='UserSurname', height=185]
---------------------
Enter name of the method for call:
-> grow(int)
Enter int value:
-> 10
Method returned:
195
```

- Если метод содержит более одного параметра, вам необходимо установить значения для каждого из них.
- Если метод имеет тип void, строка с информацией о возвращаемом значении не отображается.
- В сеансе программы возможно взаимодействие только с одним классом; можно изменить одно поле его объекта и вызвать один метод
- Вы можете использовать оператор throws.


## Exercise 01 – Annotations – SOURCE ([директория задания](src/ex02))

| Exercise 01: Annotations – SOURCE |                     |
|-----------------------------------|---------------------|
| Директория сдачи                  | 	ex01               |
| Файлы сдачи                       | 	Annotations-folder |

Аннотации позволяют хранить метаданные непосредственно в программном коде. Теперь ваша цель — реализовать класс HtmlProcessor (производный отAbstractProcessor), который обрабатывает классы со специальными аннотациями @HtmlForm и @Htmlnput и генерирует код формы HTML внутри папки target/classes после выполнения команды компиляции mvn clean. Предположим, у нас есть класс UserForm:

```java
@HtmlForm(fileName = “user_form.html”, action = “/users”, method = “post”)
public class UserForm {
	@HtmlInput(type = “text”, name = “first_name”, placeholder = “Enter First Name”)
	private String firstName;

	@HtmlInput(type = “text”, name = “last_name”, placeholder = “Enter Last Name”)
	private String lastName;
	
	@HtmlInput(type = “password”, name = “password”, placeholder = “Enter Password”)
	private String password;
}
```

Затем его следует использовать в качестве основы для создания файла «user_form.html» со следующим содержимым:

```HTML
<form action = "/users" method = "post">
	<input type = "text" name = "first_name" placeholder = "Enter First Name">
	<input type = "text" name = "last_name" placeholder = "Enter Last Name">
	<input type = "password" name = "password" placeholder = "Enter Password">
	<input type = "submit" value = "Send">
</form>
```

- Аннотации @HtmlForm и @HtmlInput должны быть доступны только во время компиляции.
- Структура проекта на усмотрение разработчика.
- Для правильной обработки аннотаций мы рекомендуем использовать специальные настройки maven-compiler-plugin и зависимость автосервиса от com.google.auto.service.

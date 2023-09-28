SQL/JDBC

Сегодня мы использовали ключевые механизмы работы с СУБД PostgreSQL через JDBC.

# Содержание

1. [Exercise 00 – Tables & Entities](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day6/README.md#exercise-00--tables--entities-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)
2. [Exercise 01 – Read/Find](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day6/README.md#exercise-01--readfind-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)
3. [Exercise 02 – Create/Save](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day6/README.md#exercise-02--createsave-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)
4. [Exercise 03 – Update](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day6/README.md#exercise-03--update-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)
5. [Exercise 04 – Find All](https://github.com/Aramil326/Java_bootcamp/blob/master/Java_Bootcamp._Day6/README.md#exercise-03--update-%D0%B4%D0%B8%D1%80%D0%B5%D0%BA%D1%82%D0%BE%D1%80%D0%B8%D1%8F-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D1%8F)

## Exercise 00 – Tables & Entities ([директория задания](src/ex00))

| Exercise 00: Tables & Entities |             |
|--------------------------------|-------------|
| Директория сдачи               | 	ex00       |
| Файлы сдачи	                   | Chat-folder |

Ключевые модели предметной области, для которых должны быть реализованы как таблицы SQL, так и классы Java:

- User
    - User ID
    - Login
    - Password
    - List of created rooms
    - List of chatroom's where a user socializes
- Chatroom
    - Chatroom id
    - Chatroom name
    - Chatroom owner
    - List of messages in a chatroom
- Message
    - Message id
    - Message author
    - Message room
    - Message text
    - Message date/time

Создайте файл Schema.sql, в котором опишите операции CREATE TABLE для создания таблиц для проекта. Вам также следует
создать файл data.sql с INSERTS текстовых данных (не менее пяти в каждой таблице).

Важно выполнить следующее требование!

Предположим, что сущность «Курс» имеет отношение «один ко многим» с сущностью «Урок». Тогда их объектно-ориентированное
отношение должно выглядеть следующим образом:

```java
class Course {
   private Long id;
   private List<Lesson> lessons;// there are numerous lessons in the course
   ...
}
class Lesson {
   private Long id;
   private Course course; // the lesson contains a course it is linked to
   ...
}
```

Дополнительные требования:

- Для реализации реляционных ссылок используйте типы ссылок «один-ко-многим» и «многие-ко-многим».
- Идентификаторы должны быть числовыми.
- Идентификаторы должны генерироваться СУБД.
- Equals(), hashCode() и toString() должны быть правильно переопределены внутри классов Java.

Структура проекта упражнения:

- Chat
    - src
        - main
            - java
                - edu.school21.chat
                - models - модели предметных знаний
            - resources
                - schema.sql
                - data.sql
    - pom.xml

## Exercise 01 – Read/Find ([директория задания](src/ex01))

| Exercise 01: Read/Find |              |
|------------------------|--------------|
| Директория сдачи       | 	ex01        |
| Файлы сдачи            | 	Chat-folder |

Объект доступа к данным (DAO, Repository) — это популярный шаблон дизайна, который позволяет отделить ключевую
бизнес-логику от логики обработки данных в приложении.

Предположим, у нас есть интерфейс CoursesRepository, который обеспечивает доступ к урокам курса. Этот интерфейс может
выглядеть следующим образом:

```java
public interface CoursesRepository {
    Optional<Course> findById(Long id);

    void delete(Course course);

    void save(Course course);

    void update(Course course);

    List<Course> findAll();
}
```

Вам необходимо реализовать MessagesRepository с помощью SINGLE Optional<Message> findById(Long id)метода и его
реализации MessagesRepositoryJdbcImpl.

Этот метод должен возвращать объект Message, в котором будут указаны автор и чат. В свою очередь, НЕ НУЖНО вводить
подсущности (список чатов, создатель чата и т.п.) для автора и чата.

Реализованный код необходимо протестировать в классе Program.java. Пример работы программы следующий (вывод может
отличаться):

```
$ java Program
Enter a message ID
-> 5
Message : {
  id=5,
  author={id=7,login=“user”,password=“user”,createdRooms=null,rooms=null},
  room={id=8,name=“room”,creator=null,messages=null},
  text=“message”,
  dateTime=01/01/01 15:69
}
```

Структура проекта упражнения:

- Chat
    - src
        - main
            - java
                - edu.school21.chat
                - models - модели предметных знаний
                    - repositories - репозитории
                    - app
                        - Program.java
            - resources
                - schema.sql
                - data.sql
    - pom.xml

MessagesRepositoryJdbcImpl должен принимать интерфейс DataSource пакета java.sql в качестве параметра конструктора. Для
реализации DataSource используйте библиотеку HikariCP — пул подключений к базе данных, который значительно ускоряет
использование хранилища.

## Exercise 02 – Create/Save ([директория задания](src/ex02))

| Exercise 02: Create/Save |              |
|--------------------------|--------------|
| Директория сдачи	        | ex02         |
| Файлы сдачи              | 	Chat-folder |

Теперь вам нужно реализовать метод save(Message message) для MessagesRepository.

Таким образом, нам нужно определить следующие подсущности для сохраняемой сущности — автора сообщения и чат. Также важно
присвоить чату и автору идентификаторы, существующие в базе данных.

Пример использования метода сохранения:

```java
public static void main(String args[]){
        ...
        User creator=new User(7L, “user”, “user”,new ArrayList(),new ArrayList());
        User author=creator;
        Room room=new Room(8L, “room”,creator,new ArrayList());
        Message message=new Message(null,author,room, “Hello!”,LocalDateTime.now());
        MessagesRepository messagesRepository=new MessagesRepositoryJdbcImpl(...);
        messagesRepository.save(message);
        System.out.println(message.getId()); // ex. id == 11
        }
```

Таким образом, метод save должен присвоить значение идентификатора входящей модели после сохранения данных в базе
данных. Если у автора и комнаты нет идентификатора в назначенной базе данных или эти идентификаторы имеют значение NULL,
выдайте RuntimeException NotSavedSubEntityException (реализуйте это исключение самостоятельно).

Протестируйте реализованный код в классе Program.java.

## Exercise 03 – Update ([директория задания](src/ex03))

| Exercise 03: Update |              |
|---------------------|--------------|
| Директория сдачи    | 	ex03        |
| Файлы сдачи         | 	Chat-folder |

Теперь нам нужно реализовать метод обновления в MessageRepository. Этот метод должен полностью обновить существующий
объект в базе данных. Если новое значение поля обновляемого объекта равно нулю, это значение должно быть сохранено в
базе данных.

Пример использования метода обновления:

```java
public static void main(String args[]){
        MessagesRepository messagesRepository=new MessagesRepositoryJdbcImpl(...);
        Optional<Message> messageOptional=messagesRepository.findById(11);
        if(messageOptinal.isPresent()){
        Message message=messageOptional.get();
        message.setText(“Bye”);
        message.setDateTime(null);
        messagesRepository.update(message);
        }
        ...
        }
```

В этом примере значение столбца, в котором хранится текст сообщения, будет изменено, а время сообщения будет равно нулю.

## Exercise 04 – Find All ([директория задания](src/ex04))

| Exercise 04: Find All |              |
|-----------------------|--------------|
| Директория сдачи      | 	ex04        |
| Файлы сдачи           | 	Chat-folder |

Теперь вам нужно реализовать интерфейс UsersRepository и класс UsersRepositoryJdbcImpl с
помощью `SINGLE List<User> findAll(int page, int size)` метода.
Этот метод должен возвращать размер — пользователей, показанных на странице с номером страницы. Этот «кусочный» поиск
данных называется нумерацией страниц. Таким образом, СУБД делит общий набор на страницы, каждая из которых содержит
записи о размере. Например, если набор содержит 20 записей со страницей = 3 и размером = 4, вы получаете пользователей с
12 по 15 (нумерация пользователей и страниц начинается с 0).
Самая сложная ситуация при преобразовании реляционных ссылок в объектно-ориентированные ссылки возникает, когда вы
извлекаете набор сущностей вместе с их подсущностями. В этой задаче каждый пользователь в результирующем списке должен
включать зависимости — список чатов, созданных этим пользователем, а также список чатов, в которых участвует
пользователь.
Каждая подсущность пользователя НЕ ДОЛЖНА включать свои зависимости, т.е. список сообщений внутри каждой комнаты должен
быть пустым.
Реализованную работу метода следует продемонстрировать в Program.java.

**Примечание**:

- Метод findAll(int page, int size) должен быть реализован с помощью ОДНОГО запроса к базе данных. Не допускается
  использование дополнительных SQL-запросов для получения информации по каждому пользователю.
- Мы рекомендуем использовать CTE PostgreSQL.
- UsersRepositoryJdbcImpl должен принимать интерфейс DataSource пакета java.sql в качестве параметра конструктора.

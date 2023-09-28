JUnit/Mockito

Сегодня я изучил основы модульного и интеграционного тестирования.


# Содержание
4.1. [Exercise 00 – First Tests]()
5.1. [Exercise 01 – Embedded DataBase]()
6.1. [Exercise 02 – Test for JDBC Repository]()
7.1. [Exercise 03 – Test for Service]()


## Exercise 00 – First Tests ([директория задания](src/ex00))

| Exercise 00: First Tests |               |
|--------------------------|---------------|
| Директория сдачи         | 	ex00         |
| Файлы сдачи              | 	Tests-folder |

Теперь вам нужно реализовать класс NumberWorker, который содержит следующую функциональность:

```java
public boolean isPrime(int number) {
  ...
}

```

Этот метод определяет, является ли число простым, и возвращает значение true/false для всех натуральных (положительных целых) чисел. Для отрицательных чисел, а также 0 и 1 программа должна выдать непроверяемое исключение. Исключение IllegalNumberException.

```java
public int digitsSum(int number) {
  ...
}
```

Этот метод возвращает сумму цифр исходного номера.

Нам также необходимо создать класс NumberWorkerTest, реализующий логику тестирования модуля. Методы класса NumberWorkerTest должны проверять корректность работы методов NumberWorker для различных входных данных:

1. метод isPrimeForPrimes для проверки isPrime с использованием простых чисел (не менее трех)
2. метод isPrimeForNotPrimes для проверки isPrime с использованием составных чисел (минимум три)
3. метод isPrimeForIncorrectNumbers для проверки isPrime с использованием неправильных чисел (минимум три)
4. метод проверки суммы цифр с использованием набора из не менее 10 чисел

**Требования**:

- Класс NumberWorkerTest должен содержать не менее 4 методов для проверки функциональности NumberWorker.
- Использование @ParameterizedTest и @ValueSource является обязательным для методов 1–3.
- Использование @ParameterizedTest и @CsvFileSource является обязательным для метода 4.
- Вам необходимо подготовить файл data.csv для метода 4, в котором необходимо указать не менее 10 чисел и их правильную сумму цифр. Пример содержимого файла: 1234, 10.
  

**Структура проекта**:

- Tests
    - src
        - main
            - java
                - edu.school21.numbers
                    - NumberWorker
            - resources
        - test
            - java
                - edu.school21.numbers
                    - NumberWorkerTest
            - resources
                - data.csv
    - pom.xml


## Exercise 01 – Embedded DataBase ([директория задания](src/ex01))

| Exercise 01: Embedded DataBase |        |
|--------------------------------|--------|
| Директория сдачи               | 	ex01  |
| Файлы сдачи                    | 	Tests |

Не используйте тяжелую СУБД (например, PostgreSQL) для реализации интеграционного тестирования компонентов, взаимодействующих с базой данных. Лучше всего создать легкую базу данных в памяти с заранее подготовленными данными.

Реализовать механизм создания DataSource для СУБД HSQL. Для этого подключите к проекту зависимости Spring-jdbc и hsqldb. Подготовьте файлы Schema.sql и data.sql, в которых опишите структуру таблицы продуктов и тестовые данные (не менее пяти).

Структура таблицы товаров:
- идентификатор
- имя
- цена

Также создайте класс EmbeddedDataSourceTest. В этом классе реализуйте метод init(), отмеченный аннотацией @BeforeEach. В этом классе реализуйте функциональность для создания источника данных с помощью EmbeddedDataBaseBuilder (класс в библиотеке Spring-jdbc). Реализуйте простой тестовый метод для проверки возвращаемого значения метода getConnection(), созданного DataSource (это значение не должно быть нулевым).

**Структура проекта**:

- Tests
    - src
        - main
            - java
                - edu.school21.numbers
                    - NumberWorker
            - resources
        - test
            - java
                - edu.school21
                    - numbers
                        - NumberWorkerTest
                    - repositories
                        - EmbeddedDataSourceTest
            - resources
                - data.csv
                - schema.sql
                - data.sql
    - pom.xml


## Exercise 02 – Test for JDBC Repository ([директория задания](src/ex02))

| Exercise 02: Test for JDBC Repository |        |
|---------------------------------------|--------|
| Директория сдачи                      | 	ex02  |
| Файлы сдачи                           | 	Tests |

Реализуйте пару интерфейс/класс ProductsRepository/ProductsRepositoryJdbcImpl с помощью следующих методов:

```java
List<Product> findAll()

Optional<Product> findById(Long id)

void update(Product product)

void save(Product product)

void delete(Long id)
```

Вы должны реализовать класс ProductsRepositoryJdbcImplTest, содержащий методы, проверяющие функциональность репозитория, с использованием базы данных в памяти, упомянутой в предыдущем упражнении. На этом занятии следует заранее подготовить объекты модели, которые будут использоваться для сравнения во всех тестах.

Пример объявления тестовых данных приведен ниже:

```java
class ProductsRepositoryJdbcImplTest {
  final List<Product> EXPECTED_FIND_ALL_PRODUCTS = ...;
  final Product EXPECTED_FIND_BY_ID_PRODUCT = ...;
  final Product EXPECTED_UPDATED_PRODUCT = ...;
}
```

**Примечания**:

1. Каждый тест должен быть изолирован от поведения других тестов. Таким образом, перед запуском каждого теста база данных должна находиться в исходном состоянии.
2. Методы тестирования могут вызывать другие методы, которые не участвуют в текущем тесте. Например, при тестировании метода update() может быть вызван метод findById() для проверки достоверности обновления объекта в базе данных.
   

**Структура проекта**:

- Tests
    - src
        - main
            - java
                - edu.school21
                    - numbers
                        - NumberWorker
                    - models
                        - Product
                    - repositories
                        - ProductsRepository
                        - ProductsRepositoryJdbcImpl
            - resources
        - test
            - java
                - edu.school21
                    - numbers
                        - NumberWorkerTest
                    - repositories
                        - EmbeddedDataSourceTest
                        - ProductsRepositoryJdbcImplTest
            - resources
                - data.csv
                - schema.sql
                - data.sql
    - pom.xml


## Exercise 03 – Test for Service ([директория задания](src/ex03))

| Exercise 03: Test for Service |        |
|-------------------------------|--------|
| Директория сдачи              | 	ex03  |
| Файлы сдачи                   | 	Tests |

Важное правило для модульных тестов: тестируется отдельный компонент системы без вызова функциональности его зависимостей. Такой подход позволяет разработчикам самостоятельно создавать и тестировать компоненты, а также откладывать реализацию отдельных частей приложения.

Теперь вам нужно реализовать уровень бизнес-логики, представленный классом UsersServiceImpl. Этот класс содержит логику аутентификации пользователя. Он также зависит от интерфейса UsersRepository (в этой задаче вам не нужно реализовывать этот интерфейс).

Интерфейс UsersRepository (описанный вами) должен содержать следующие методы:

```java
User findByLogin(String login);
void update(User user);
```

Предполагается, что метод findByLogin возвращает объект User, найденный при входе в систему, или выдает EntityNotFoundException, если не найден ни один пользователь с указанным именем входа. Метод Update выдает аналогичное исключение при обновлении пользователя, которого нет в базе данных.

Пользовательская сущность должна содержать следующие поля:
- Идентификатор
- Авторизоваться
- Пароль
- Статус успешной аутентификации (true — аутентифицировано, false — не аутентифицировано)

В свою очередь класс UsersServiceImpl вызывает эти методы внутри функции аутентификации:

```java
boolean authenticate(String login, String password)
```

Этот метод:
1. Проверяет, прошел ли пользователь аутентификацию в системе с использованием данного логина. Если аутентификация была выполнена, необходимо создать исключение AlwaysAuthenticatedException.
2. Пользователь с этим логином извлекается из UsersRepository.
3. Если полученный пароль пользователя соответствует указанному паролю, метод устанавливает статус успешной аутентификации пользователя, обновляет его информацию в базе данных и возвращает true. Если пароли не совпадают, метод возвращает false.

Ваша цель:
1. Создать интерфейс UsersRepository
2. Создайте класс UsersServiceImpl и метод аутентификации.
3. Создайте тест модуля для класса UsersServiceImpl.

Поскольку ваша цель — проверить корректность работы метода аутентификации независимо от компонента UsersRepository, вам следует использовать фиктивный объект и заглушки методов findByLogin и update (см. библиотеку Mockito).

Метод аутентификации проверяется в трех случаях:
1. Правильный логин/пароль (проверьте метод обновления вызова, используя инструкцию проверки библиотеки Mockito)
2. Неверный логин
3. неверный пароль

**Структура проекта**:

- Tests
    - src
        - main
            - java
                - edu.school21
                    - exceptions
                        - AlreadyAuthenticatedException
                    - numbers
                        - NumberWorker
                    - models
                        - Product
                        - User
                    - services
                        - UsersServiceImpl
                    - repositories
                        - ProductsRepository
                        - ProductsRepositoryJdbcImpl
                        - UsersRepository
            - resources
        - test
            - java
                - edu.school21
                    - services
                        - UsersServiceImplTest
                    - numbers
                        - NumberWorkerTest
                    - repositories
                        - EmbeddedDataSourceTest
                        - ProductsRepositoryJdbcImplTest
            - resources
                - data.csv
                - schema.sql
                - data.sql
    - pom.xml

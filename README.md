### Представляю свой проект по теме автоматизированная система в сфере ремонта бытовой техники
#### Проект полностью написан на языке java с использованием популярного фреймворка spring boot версии 3.2.5
#### spring boot - это это фреймворк для разработки приложений на языке Java, который помогает упростить процесс создания и настройки приложений. Он предоставляет множество готовых инструментов и функций, которые делают разработку проще и быстрее. Spring Boot также упрощает конфигурирование приложений и автоматически управляет зависимостями, что позволяет разработчикам сосредоточиться на самом коде приложения.

### Основные требования по проекту:
1) Требования к аппаратному  обеспечению
 - Windows 7+/MacOS 13+/Linux9+;
 - Оперативная   память 8 гб+;
 - Свободное место на диске 250 гб+
2) Требования к программному обеспечению
 - СУБД PostgreSQL 9.2-9.5;
 - Java Development Kit 17;
 - серевер Tomcat 10+
 - Maven 3+
3) Использование библиотек
 - Lombok
 - Spring Web
 - Spring Security
 - Spring Validation
 - Spring Thymeleaf
 - Driver postgres
 - Tomcat
 - Spring Data JPA
4) Использования front-end файлы
 - Java-script
 - html
 - css
5) Использования SQL запросов и миграция баз данных
 - Transaction
 - FlyWay
 - Database postgresql
 - CRUD операции
6) Функциональность для spring Security
 - Аутентификация и авторизация пользователей
 - Разграничение прав доступа к ресурсам
 - Шифрование паролей пользователей
 - Логирование действий пользователей
### Для запуска проекта 
1) Надо иметь IntelliJ IDEA 2023.2.5
2) Postgresql
3) Browser Chrome

### Иерархия-классов проекта
![img.png](img.png)
>Начне с самого главного мой проект работает на MVC приложение Model-View-Controller
1) Controller 
>В моем проекте есть 2 папки controller, они отличаются тем, что одна папка создана для securty в данном случае это регистрация и авторизация 
> """
> 
    @Autowired
    public RegistrationController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        userSecurityService.registeredUser(userDto);

        return "redirect:/login-user";
    }
"""
>Здесь я показал маленький код для контроллера RegistrationController, суть это контроллера заключается в следующем 
> 
>Метод showRegistrationForm обрабатывает GET запрос на страницу регистрации и добавляет модель UserDto в атрибуты, чтобы передать ее на страницу register.html для отображения формы регистрации.
> 
>Метод registerUser обрабатывает POST запрос с данными, отправленными со страницы регистрации.
> 
>Аннотация @Valid используется для валидации данных, переданных с формы (UserDto), согласно правилам, определенным в классе UserDto.
BindingResult содержит результаты валидации данных, и если есть ошибки, метод возвращает страницу регистрации с сообщениями об ошибках.
Если данные прошли валидацию успешно, выполняется метод registeredUser из сервиса userSecurityService для регистрации нового пользователя на основе данных из UserDto.
После успешной регистрации, метод возвращает redirect на страницу для входа пользователя.
Таким образом, данный контроллер обрабатывает запросы связанные с регистрацией новых пользователей, валидирует данные, регистрирует нового пользователя и перенаправляет пользователя на страницу входа.
> 
> 
> Далее расскажи как просиходит регистрация пользователя в сервисе 
> 
> В сервисе UserSecurityService для регистрации пользователя вызывается метод registeredUser, который принимает на вход объект UserDto, содержащий информацию о новом пользователе (логин, пароль, email, имя, фамилия, возраст, город, интересы).
>
>В начале метода происходит проверка наличия пользователя с таким же логином в базе данных. Если пользователь с таким логином уже существует, то выбрасывается исключение SameUserInDatabase.
>
>Затем создается объект User и заполняется информацией о пользователе из UserDto. Этот объект сохраняется в репозитории UserRepository, чтобы получить уникальный идентификатор (id) пользователя.

>Далее создается объект UserSecurity и заполняется информацией о пользователе из UserDto, а также устанавливается хешированный пароль с помощью PasswordEncoder. Роль пользователя устанавливается в Roles.MODERATION. Уникальный идентификатор пользователя (user_id) устанавливается в соответствии с id пользователя, сохраненного в репозитории UserRepository.

>И наконец, объект UserSecurity сохраняется в репозитории UserSecurityRepository, завершая процесс регистрации нового пользователя.

>Таким образом, при вызове метода registeredUser происходит создание записей о новом пользователе как в репозитории UserRepository, так и в репозитории UserSecurityRepository, обеспечивая его доступ к системе с уникальными данными и защищенным паролем.
> 
```java
     @Transactional(rollbackFor = Exception.class)
public void registeredUser(UserDto userDto){
        Optional<UserSecurity> security = userSecurityRepository.findByLogin(userDto.getLogin());
        if (security.isPresent()) {
        throw new SameUserInDatabase(userDto.getLogin());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setCity(userDto.getCity());
        user.setInteresting(userDto.getInteresting());
        User savedUser = userRepository.save(user);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setLogin(userDto.getLogin());
        userSecurity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userSecurity.setEmail(userDto.getEmail());
        userSecurity.setRole(Roles.MODERATION);
        userSecurity.setUser_id(savedUser.getId());
        userSecurityRepository.save(userSecurity);
}
 ```
>Также можно увидеть как создаются модельки для базы данных
> 
```java

package com.example.project.model; 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity(name = "responses")
public class Response {
@Id
@SequenceGenerator(name = "ResGenSeq",sequenceName = "responses_id_seq", allocationSize = 1)
@GeneratedValue(generator = "ResGenSeq")
private Long id;
@Column(name = "request_id")
private Long request_id;
@Column(name = "user_id")
private Long user_security_id;
@Column(name = "response_text")
private String response_text;
@Column(name = "response_date")
@Temporal(TemporalType.TIMESTAMP)
private Timestamp response_date;
@Column(name = "login")
private String login;
@Column(name = "model_name")
private String model_name;
}
```
>Здесь создан класс Response, который представляет собой сущность в базе данных. Аннотации указывают на то, что класс является сущностью (Entity), определяют идентификатор (Id) с автоинкрементом, задают генератор последовательности (SequenceGenerator), указывают на колонки в таблице (Column) и их типы (Temporal), а также указывают на связь с таблицей "responses". Класс также содержит конструкторы, геттеры и сеттеры для свойств.
> 
> Рассмотрим настройки для security c назначением правд доступа для url-адрессов
>
```java
return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        new AntPathRequestMatcher("/", "GET"),
                                        new AntPathRequestMatcher("/developer", "GET"),
                                        new AntPathRequestMatcher("/register", "POST"),
                                        new AntPathRequestMatcher("/register", "GET"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/images/**"),
                                        new AntPathRequestMatcher("/profile"),
                                        new AntPathRequestMatcher("/js/**")
                                ).permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/requests/create", "GET"),
                                        new AntPathRequestMatcher("/create", "POST"),
                                        new AntPathRequestMatcher("/completed-requests", "GET"),
                                        new AntPathRequestMatcher("/completed-requests/{id}", "GET")
                                ).hasRole("USER")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/requests/create-response", "GET"),
                                        new AntPathRequestMatcher("/requests/create-response", "POST")
                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/responses/all", "GET")
                                ).hasAnyRole("USER", "ADMIN", "MODERATION")
                                .requestMatchers(
                                        new AntPathRequestMatcher("/user/all", "GET"),
                                        new AntPathRequestMatcher("/user/remove/{login}", "GET"),
                                        new AntPathRequestMatcher("/user/details/{login}", "POST"),
                                        new AntPathRequestMatcher("/responses/all_moderator", "GET"),
                                        new AntPathRequestMatcher("/responses/delete/{responseId}", "GET"),
                                        new AntPathRequestMatcher("/requests/all_moderator", "GET"),
                                        new AntPathRequestMatcher("/requests/moderator/details/{id}", "GET"),
                                        new AntPathRequestMatcher("/requests/delete/{id}")
                                ).hasRole("MODERATION")
                                .anyRequest().authenticated())
```
>Видно что здесь есть работа с ролями для пользователей и каждый пользователь под опредленной ролью имеет переход на свой url-адрес

### Иерархия классов базы данных
![img_1.png](img_1.png)
> Здесь также есть отдельная таблица от плагина flyway, которая при подключении к проекту автоматически создает эту таблицу, чтобы в будущем отслеживать кто какие изменения внес в базу данных
>
> Также наша база данных перешла в 3 нормальную форму, что делает приложение более эффективным и правильным
> 
> Рассмотрим некоторые запросы которые не встроенны в spring Data JPA 
```java
        @Query(nativeQuery = true, value = "SELECT * FROM requests WHERE user_security_id = :userSecurityId")
        List<Request> findByUser_security_id(Long userSecurityId);
    
        @Query(nativeQuery = true,value = "SELECT * FROM requests;")
        List<Request> allRequest();
```
>Первый запрос findByUser_security_id находит все записи из таблицы requests, где значение столбца user_security_id равно заданному значению userSecurityId. Здесь используется параметризированное значение для выполнения запроса.
>
>Второй запрос allRequest просто выбирает все записи из таблицы requests без каких-либо фильтров или условий. Этот запрос просто возвращает все данные из таблицы.

### Как работает программа 
>Пользователь отправляется на главную страницу c url адресом localhost8080:
```html
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>MainPage</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/main.css}">
</head>
<body>
<header>
    <h2 class="Mex">Mex</h2>
    <nav class="navigation">
        <a th:href="@{/login}" th:if="${!showProfileLink}">Логин</a>
        <a th:href="@{/register}" th:if="${!showProfileLink}">Регистрация</a>
        <a th:href="@{/developer}" th:if="${!showProfileLink}">Об разработчике</a>
        <a th:href="@{/profile}" th:if="${showProfileLink}">Мой профиль</a>
        <span th:if="${showProfileLink}" class="logged-in">Вошел пользователь: <span th:text="${username}"></span></span>
        <a th:if="${showProfileLink}" th:href="@{/logout}">Выйти</a>
        <a th:if="${showProfileLink }" th:href="@{/responses/all}">Ответы от Админов</a>
    </nav>
</header>
</body>
</html>
```
>Здесь есть реализация о том что при первом запуске программы, когда пользователь еще не авторизовался , то у него отображаются конкретные кнопки, а как только авторизовался то отображаются другие кнопки
> 
> Можно реализацию увидеть в этом контроллере: 
> 
```java
@GetMapping("/")
public String mainPage(Model model, Principal principal){
if (principal == null) {
// Пользователь не авторизован, возвращаем модель с ссылками на login, register и developer
model.addAttribute("showProfileLink", false);
} else {
// Пользователь авторизован, возвращаем модель с ссылкой на profile
model.addAttribute("showProfileLink", true);
model.addAttribute("username", principal.getName()); // Добавляем имя пользователя в модель
}
return "mainPage";
}
```
>Далее когда пользователь прошел авторизацию , то под ролью USER у него есть 3 кнопки это вернуться обратно , создать заявку на ремонт и просмотреть свои заявки
> 
> Если же пользователь имеет роль Admin то он моежт просмотреть все заявки и дать на конкретную заявку ответ и этот ответ будет отображаться на главной странице для всех пользователей и админов, а также модераторов
> 
> Что касается пользователя с ролью MODERATION то он имеет функцию просмотр всех пользователь , подробную информацию о них, а также удаление их из базы данных, тоже самое и для заявок от пользователей и ответов от админов , точно также и удаления их
> 
> Можно сказать что данный проект помогает практически познакомиться со spring boot и spring Securiry, что в дальнейшем это поможет при трудоустройстве на работу, данный проект расчитать на аудиторию, которая знакомы с базовыми знаниями о Java и базовые знания про frond-end и базу данных

### Лицензия проекта
>Данный проект можно применять для практический занятий в любые учебные заведения, также разрешается копирование созданных мной методов, а также изменения моего проекта, но не забудьте что данные проект расчитан на аудиторию которая имеет базовые знания о программировании 

### Разработал проект
- Механиков Артём Денисович 
- тел. +375(29) 689 84 07
- телеграм: @unstoppable_pancakes





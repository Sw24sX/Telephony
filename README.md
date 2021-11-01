# Проект по обзвону клиентов ITalks

<h2 name="context">Содержание</h2>

* <a href="#preface">Предисловие</a>
* <a href="#java_install"> Установка Java</a>
  - <a href="#java_intsall_windows"> Установка на Windows</a>
  - <a href="#java_install_linux"> Установка на Linux</a>
* <a href="#maven_install"> Установка Maven </a>
  - <a href="#maven_install_windows"> Установка на Windows</a>
  - <a href="#maven_install_linux"> Установка на Linux</a>
* <a href="#postgresql_install"> Установка PostgreSQL</a>
* <a href="#change_project_settings"> Настройка проекта </a>
* <a href="#run_server_commands">Команды для запуска</a>
* <a href="#link_for_work">Ссылки для работы</a>
* <a href="#info">Заметки и полезная информация</a>

<h2 name = "preface">Предисловие</h2> <a href="#context">(к содержанию)</a>

----

Предполагается, что у вас уже установлен и настроен сервер asterisk. Если нет, то вот инструкция: <a href="https://github.com/Alllex202/asterisk_setup#readme" target="_blank">ссылка</a>.

Если планируется запускать проект исключительно в IDE, то можно переместиться сразу на пункт <a href="#postgresql_install"> Установка PostgreSQL</a>


<h2 name="java_install">Установка Java</h2> <a href="#context">(к содержанию)</a>

-------

<h3 name="java_intsall_windows">Установка на Windows</h3>

Скачать и установить <a href="https://www.oracle.com/java/technologies/downloads/#jdk17-windows">Installer</a>, запоминая, куда устанавливаем. 

После установки нажимаем Win+R и выполняем команду `control /name microsoft.system`. Далее переходим в "Дополнительные параметры системы" -> "Переменные среды". Для блока "Системные переменные" нажимаем "Создать". Имя указываем `JAVA_HOME`, значение - расположение установленной Java, которое запоминали ранее. В моем случае `C:\Program Files\Java\jdk-17.0.1`.

После создания новой переменной необходимо найти уже существующую переменную "Path" и изменить ее, добавив в конце `%JAVA_HOME%\bin`. Изменения вступят в силу после перезагрузки.

 Для проверки можно в командной строке (Win+R -> cmd) исполнить команду `java -version`. Если все предыдущие шаги были проделаны верно, то в ответ должна вернуться информация о текущей версии Java.

<h3 name="java_install_linux">Установка на Linux</h3> 

будет в будущем

<h2 name="maven_install">Установка Maven</h2> <a href="#context">(к содержанию)</a>

------

<h3 name="maven_install_windows">Установка на Windows</h3>

Скачать <a href="https://maven.apache.org/download.cgi">Binary zip archive</a>, распаковать и переместить папку с файлами в место для хранения бинарных файлов Maven. В моём случае это `C:\Program Files\Maven` и итоговый путь к файлам получился `C:\Program Files\Maven\apache-maven-3.8.3`. 

Далее переходим к переменным среды (нажимаем Win+R и выполняем команду `control /name microsoft.system`, переходим в "Дополнительные параметры системы" -> "Переменные среды") и создаем переменную с именем `MAVEN_HOME` и значением - путем к бинарным файлам. В моем случае `C:\Program Files\Maven\apache-maven-3.8.3`

После этого ищем уже созданную переменную "Path" и изменяем ее, добавляя в конце строку `%MAVEN_HOME%\bin`. Все изменения вступят в силу после перезагрузки.

Для проверки результата в командой строке (Win+R -> cmd) можем исполнить команду `mvn -v`. В случае, если все сделано верно, на экране появятся данные о версии и расположении Maven и текущая версия Java 


<h3 name="maven_install_linux">Установка Linux</h3>

будет в будущем

<h2 name = "postgresql_install">Установка PostgreSQL</h2> <a href="#context">(к содержанию)</a>

-----

Скачать <a href="https://www.postgresql.org/download/"> installer </a> в соответствии с используемой системой. При установке на этапе выбора пароля рекомендую установить `123`, имя пользователя рекомендую оставить по умолчанию. Это не обязательно, но в будущем позволит не изменять настройки сервера каждый раз при поступлении обновлений.

После установки необходимо открыть psql консоль. В Windows ее можно найти в поиске, введя `shell`. На данный момент сервер работает с базой данных `telephony`, ее и необходимо создать в psql командой `create database telephony;`

Не обязательный пункт: в качестве удобного административного инструмента и алтернативы pgAdmin могу предложить DBeaver: <a href = "https://dbeaver.io/"> ссылка </a>. Для подключения необходимо нажать на соответсвующую кнопку, выбрать PostgreSQL, в поле "база данных" вводим `telephony`, пароль ставим тот, который указывали при установке. Остальные параметры можно оставить без изменений. Перед подключением рекомендуется нажать на "Тест подключения".

<h2 name="change_project_settings">Настройка проекта</h2> <a href="#context">(к содержанию)</a>

----

Настроки расположены по пути `*папка проекта*\src\main\resources\application.properties`. 

Обязательно:
- `asterisk.url` - необходимо изменить ip на данные вашего сервера asterisk
- `asterisk.username` и `asterisk.password` - логин и пароль для доступа к ARI (swagger) сервера asterisk
- `server.address` - необходимо указать основной IPv4-адрес. Узнать его можно введя в консоль (Win+R -> cmd) команду `ipconfig /all`

Если была установка с параметрами НЕ по умолчанию:
- `spring.datasource.url` - путь к базе данных. Если была создана база данных `telephony`, то менять нет необхоимости
- `spring.datasource.username` и `spring.datasource.password` - имя пользователя и пароль, которые вы указывали при установке PostgreSQL. Если был установлен пароль `123`, а имя пользователя установлено по умолчанию, то изменения не требуются

<h2 name = "run_server_commands">Команды для запуска</h2> <a href="#context">(к содержанию)</a>

----

Перед запуском приложения стоит убедиться, что сервер asterisk запущен и корректно работает.

Для запуска приложения из командной строки можно использовать одну из этих команд (указатель командной строки должен стоять на корень проекта):

- `mvn spring-boot:run` или `mvnw spring-boot:run`: команды эквивалентны друг другу. Для завершения работы сервера необхоимо будет нажать Ctrl+C.
- `java -jar target/telephony-0.0.1-SNAPSHOT.jar`: запуск jar файла. Может появиться проблема, если проект не собран в исполняемый файл. Для исправления необходимо выполнить команду `mvn clean package`. Во время сборки maven запустит имеющиеся тесты. После этого можно повторить  предыдущую команду.

<h2 name="link_for_work">Ссылки для работы</h2> <a href="#context">(к содержанию)</a>

----

- Инструкция по настройке сервера asterisk: <a href="https://github.com/Alllex202/asterisk_setup#readme">ссылка</a>
- Документация по приложению (Swagger): `http://*server.address*:8080/docApi/swagger-ui.html`, где `server.address` - свойство, настроенное пунктом выше
- Ссылка на репозиторий web-приложения: <a href="#">ссылка</a>
- Ссылка на репозиторий мобильного приложения: <a href="https://github.com/dekabrsky/ITalks">ссылка</a>

<h2 name= "info">Заметки и полезная информация</h2> <a href="#context">(к содержанию)</a>

----

- здесь скоро появится информация
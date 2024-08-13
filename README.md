#Backend for TODO MVC app.

It uses: 
* Java 21
* Spring Boot 3.3.2
* Lombok
* H2 database (in memory mode)

The API is available at the path ``/todos``

### How to Run

* Clone git repo and build

```
git clone https://github.com/sovereignstack/todo.git
cd todo
./mvnw clean package
java -jar target/todo-1.0.0.jar

```
* Open http://localhost:8080 in the browser 
* Enter http://localhost:8080/todos in the "test target root" text box
* Tests can be run from externally hosted test suite as well



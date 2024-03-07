### Terms:
`CDATA` => character data, is used for distinct, but related, purposes in the markup languages SGML and XML (also HTML). The term indicates that a certain portion of the document is general character data (for other purpose), rather than non-character data or character data with a more specific

`mvnw`: it's an executable Unix shell script used in place of a fully installed Maven. 

`mvnw.cmd`: it's the windows os version of `mvnw`. 

`.mvn`: the hidden folder that holds the Maven Wrapper Java library and its properties file

### javaX to Jakarta (Java EE to Jakarta EE):
After Java EE was open sourced by Oracle and gave the rights to the Eclipse Foundation they were legally required to change the name from Java as Oracle has the rights over the Java brand. The name Jakarta was chosen by the community.

Spring Boot 3/Spring Framework 6 have upgraded to Jakarta EE 9+, hence the corresponding version of Spring Data JPA is now on the "jakarta" versions of things. Spring Boot 2.7 and earlier are still on "javax" versions of things.


`javax.persistence.*` becomes `jakarta.persistence`
`javax.faces:*` becomes `jakarta.faces:*` And so on

https://blogs.oracle.com/javamagazine/post/transition-from-java-ee-to-jakarta-ee


### Spring @Been:
A bean is an object that is instantiated, assembled, and managed by a Spring IoC container (` IoC: Inversion of Control`). It's like seed in other terms, but rather than we creating, we supplied the requirements and Spring will do the rest of job, like creating the object/s in a specific point where everything is ready and manage it's lifecycle. 
```java
@Bean
CommandLineRunner initDatabase(EmployeeRepository repository) {
    return args -> {
        log.info("Preloading " + repository.save(new Employee("Bilbo Baggins", "burglar")));
        log.info("Preloading " + repository.save(new Employee("Frodo Baggins", "thief")));
    };
}
```

https://stackoverflow.com/questions/17193365/what-in-the-world-are-spring-beans

### Inversion-of-Control (IoC) and Dependency Injection (DI):
The Dependency-Injection (DI) pattern is a more specific version of IoC pattern.

The term Inversion of Control (IoC) originally meant any sort of programming style where an overall framework or run-time controlled the program flow (lifecycle of everything inside).

Inversion of Control (IoC) means that objects do not create other objects on which they rely to do their work. Instead, they get the objects that they need, from an outside source (for example, an xml configuration file or DI).

Dependency Injection (DI) means that this is done without the object intervention, usually by a framework component that passes constructor parameters and set properties

https://stackoverflow.com/questions/6550700/inversion-of-control-vs-dependency-injection

### Spring Boot Architecture Brif and @SpringBootApplication:
Spring boot use a lot of annotation and Dependency Injection behind the scene.

`@SpringBootApplication` (Define app's starting point) a meta-annotation that pulls in `component scanning`, `autoconfiguration`, and `property support`. it will fire up a servlet container and serve up our REST service.

### Spring Data JPA (DAO) | Hibernate (ORM) :
Using Spring Data JPA, `DAOs` (Data Access Objects) are created automatically behind the scene at compile time. It uses an ORM (like Hibernate) in these DAOs. 

To create DAO, we need a `POJO` with @Entity annotation (and some inner annotations) and a repository that extends `JpaRepository<POJO, IDType>`.

```java
@Entity
public class Foo {
  @Id
  private Long id;
  private String name;
  //....
}

// FooRepository.java
public interface FooRepository extends CrudRepository<Foo, Long> {
  //that's it, nothing else. no code
}
```

JPA (`Java Persistence API`) is a specification, Spring Data JPA is built on top of it. It's a higher-level framework that builds on top of `Hibernate` and provides a simpler, more streamlined API

Base Custom Repository can also be defined and later extend into the final repository. In this case, base should be annotated with `@NoRepositoryBean`

https://stackoverflow.com/questions/35150218/difference-between-spring-data-jpa-and-orm
https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa
Guide: https://www.javaguides.net/p/spring-data-jpa-tutorial.html
https://docs.spring.io/spring-data/jpa/reference/repositories/definition.html

### REST Controller Sample:
```java
package com.mainuldip.Payroll;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/employees")
    List<Employee> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]
    // curl -v localhost:8080/employees

    // add a new employee
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }
    // curl -X POST localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }
    // curl -v localhost:8080/employees/99

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody  Employee newEmployee,@PathVariable Long id) {
        return repository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return repository.save(employee);
        }).orElseGet(() -> {
            newEmployee.setId(id);
            return repository.save(newEmployee);
        });
    }
    // curl -X PUT localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'

    // Delete Employee
    @DeleteMapping("/employees/{id}")
    void deleteEmploy(@PathVariable Long id) {
        repository.deleteById(id);
    }
    // curl -X DELETE localhost:8080/employees/4
}


class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}

// this will actually connect the custom exception handler (EmployeeNotFoundException) with the Spring REST Controller Application
@ControllerAdvice
class EmployeeNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage() + " Done!! \n\n\n";
    }
}
```



### @Autowired:
It allows Spring to resolve and inject collaborating beans into our bean.
https://www.baeldung.com/spring-autowire
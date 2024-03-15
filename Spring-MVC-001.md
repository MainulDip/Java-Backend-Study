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

### Spring Boot Architecture Brif and @SpringBootApplication
Spring boot use a lot of annotation and Dependency Injection behind the scene.

`@SpringBootApplication` (Define app's starting point) a meta-annotation that pulls in `component scanning`, `autoconfiguration`, and `property support`. it will fire up a servlet container and serve up our REST service.

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayrollApplication {
	public static void main(String[] args) {
		System.out.print("Bismillah");
		SpringApplication.run(PayrollApplication.class, args);
	}
}
```

### Spring Data JPA (DAO) | Hibernate (ORM) :
Using Spring Data JPA (Java Persistance API), `DAOs` (Data Access Objects) are created automatically behind the scene at compile time. It uses an ORM (like Hibernate) in these DAOs. 

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

JPA (`Java Persistence API`) is a specification, Spring Data JPA is built on top of it. It's a higher-level framework that builds on top of `Hibernate` ORM and provides a simpler, more streamlined API

Base Custom Repository can also be defined and later extend into the final repository. In this case, base should be annotated with `@NoRepositoryBean`.

Spring Data JPA will automatically look for a database driver to connect with a DB.

https://stackoverflow.com/questions/35150218/difference-between-spring-data-jpa-and-orm
https://www.baeldung.com/the-persistence-layer-with-spring-data-jpa
Guide: https://www.javaguides.net/p/spring-data-jpa-tutorial.html
https://docs.spring.io/spring-data/jpa/reference/repositories/definition.html

### JPA, Database Connections (h2,mysql,postgres,etc) and application.properties entry:
JAP will by default look for a database connection driver or an in-memory database in the artifacts/dependencies. H2 is an in-memory database, which can be used for fast prototyping and later can be migrated into MySQL or PostgreSQL. Spring boot initializer lists all the available db drivers, like `MySQL Driver`, `PostgreSQL Driver`

To configure jpa with full featured database like mysql or postgres, `application.properties` or `application.yml` file is used to store database configs.

```txt
spring.datasource.url=jdbc:mysql://localhost:3306/myDb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.datasource.username=root
spring.datasource.password=root
server.port=7000
```

### REST Controller Sample (RPC):
```java
package com.mainuldip.payroll;
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


### RESTful vs RPC and Spring HATEOAS:
REST stands for `Representational State Transfer` and RPC for `Remote procedural call`. Calling an API end point and getting back response are considered `RPC`. When RPC includes `Representation` of the overall API as link (Link to other resources), it become a `RESTful` service. In other words, a RESTful API will always provide a way to further interact with the service (through link/hypertext to other resources).

Roy T. Fielding first coined the term REST in 2000, in his PhD dissertation Architectural Styles and the Design of Network-based Software Architectures. 

* Example: A RESTful response form a server that includes link to self and all employee collection

```json
{
  "id": 1,
  "name": "Bilbo Baggins",
  "role": "burglar",
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees/1"
    },
    "employees": {
      "href": "http://localhost:8080/employees"
    }
  }
}
```


* Spring HATEOAS is a helper module to write hypermedia-driven API or RESTful API. Controller method and response docked

```java
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> all() {
      return repository.findAll();
    }
    // curl -v localhost:8080/employees

    // EntityModel<T> is form Hateoas, linkTo and methodOn are form hateoas' utility WebMvcLinkBuilder package
    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable @NonNull Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return EntityModel.of(employee, 
        linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
        linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
        );
    }
}

// curl -v localhost:8080/employees/1 | json_pp
/* response
{
  "id": 1,
  "name": "Bilbo Baggins",
  "role": "burglar",
  "_links": {
    "self": {
      "href": "http://localhost:8080/employees/1"
    },
    "employees": {
      "href": "http://localhost:8080/employees"
    }
  }
}
*/
```

### Hateoas Collection Model:
CollectionModel<> is another Spring HATEOAS container; it’s aimed at encapsulating collections of resources—instead of a single resource entity.
```java
@GetMapping("/employees")
// will return something like List<Employee> bust will all the self and other resources link
CollectionModel<EntityModel<Employee>> all() {
    List<EntityModel<Employee>> employees = repository.findAll().stream()
            .map( employee -> EntityModel.of (
                    employee,
                    linkTo( methodOn(EmployeeController.class).one(employee.getId()) ).withSelfRel(),
                    linkTo( methodOn(EmployeeController.class).all() ).withRel("employees") 
                    ) 
            )
            .collect(Collectors.toList());
    return CollectionModel.of (
            employees, linkTo( methodOn(EmployeeController.class).all() ).withSelfRel()
    );
}
```

Lets observe the difference between output form `List<Employees>` vs `CollectionModel<EntityModel<Employee>>`

```json
/* Output of the CollectionModel

{
   "_embedded" : {
      "employeeList" : [
         {
            "_links" : {
               "employees" : {
                  "href" : "http://localhost:8080/employees"
               },
               "self" : {
                  "href" : "http://localhost:8080/employees/1"
               }
            },
            "id" : 1,
            "name" : "Bilbo Baggins",
            "role" : "burglar"
         },
         {
            "_links" : {
               "employees" : {
                  "href" : "http://localhost:8080/employees"
               },
               "self" : {
                  "href" : "http://localhost:8080/employees/2"
               }
            },
            "id" : 2,
            "name" : "Frodo Baggins",
            "role" : "thief"
         }
      ]
   },

   "_links" : {
      "self" : {
         "href" : "http://localhost:8080/employees"
      }
   }
}

     */

//    @GetMapping("/employees")
//    List<Employee> all() {
//        return repository.findAll();
//    }

    /* Output of List<Employees>
    
[
   {
      "id" : 1,
      "name" : "Bilbo Baggins",
      "role" : "burglar"
   },
   {
      "id" : 2,
      "name" : "Frodo Baggins",
      "role" : "thief"
   }
]


     */
```

### `Hateoas`, `WebMvcLinkBuilder` and `RepresentationModelAssembler`:
WebMvcLinkBuilder is for working with SpringMVC. It contains `linkTo`, `methodOn` helper static methods.

`RepresentationModelAssembler<T,EntityModel<T>>` interface is to convert  a POJO into Hateoas's data type, with will include all the hyper links.

```java
// adding spring's @Component annotation, so-that the assembler will be automatically created when the app starts.
@Component
class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

    // toModel is the method of RepresentationalModelAssembler that is marked `abstract`, which must be implemented
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees")
                );
    }
}

// inject EmployeeModelAssembler in the controller's constructor, and use it from the class prop
@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler; // injecting EmployeeModelAssembler
    }

    // Aggregate root
    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of (
                employees, linkTo( methodOn(EmployeeController.class).all() ).withSelfRel()
        );
    }
    // curl -v localhost:8080/employees | json_pp

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable @NonNull Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return assembler.toModel(employee);
    }
    // curl -v localhost:8080/employees/99

    // Other code
}

```

### ResponseEntity<?>:
```java
@PostMapping("/employees")
ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {

      EntityModel<Employee> entityModel = assembler.toModel(repository.save(newEmployee));

      return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
}
```

### JPA vs JDBC
Spring JDBC allows to write SQL queries explicitly, giving the complete control over the database interactions. But adds a lot of boilerplate code. JDBC is database-dependent, which means that different scripts must be written for different databases

Spring JPA acts as abstraction layer on top of Hibernate and JDBC based on the JPA specification (java persistance api specification). Spring provide pre-configured database query on repository. So most of the boilerplate codes are hidden. JPA is database-agnostic, meaning that the same code can be used in a variety of databases with few (or no) modifications.

Spring JPA provides `CrudRepository` for simple CRUD operations and provide built-in methods to hook into controllers

* JPA-based applications still use JDBC under the hood. JPA serves as a layer of abstraction that hides the low-level JDBC calls from the developer, making database programming considerably easier.

### Port Configs
https://www.baeldung.com/spring-boot-change-port
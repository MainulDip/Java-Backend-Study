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

### @SpringBootApplication
It's a meta-annotation that pulls in `component scanning`, `autoconfiguration`, and `property support`. it will fire up a servlet container and serve up our service.
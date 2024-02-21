### Overviews:
It's a personalized monorepo regarding Spring MVC, Qurakus, gRPC, GraalVM, JVM, etc using Java
### Next
=> https://www.baeldung.com/maven-wrapper

### Terms:
`CDATA` => character data, is used for distinct, but related, purposes in the markup languages SGML and XML (also HTML). The term indicates that a certain portion of the document is general character data (for other purpose), rather than non-character data or character data with a more specific

`mvnw`: it's an executable Unix shell script used in place of a fully installed Maven. 

`mvnw.cmd`: it's the windows os version of `mvnw`. 

`.mvn`: the hidden folder that holds the Maven Wrapper Java library and its properties file
### javaX to Jakarta (Java EE to Jakarta EE):
After Java EE was open sourced by Oracle and gave the rights to the Eclipse Foundation they were legally required to change the name from Java as Oracle has the rights over the Java brand. The name Jakarta was chosen by the community.

Spring Boot 3/Spring Framework 6 have upgraded to Jakarta EE 9+, hence the corresponding version of Spring Data JPA is now on the "jakarta" versions of things. Spring Boot 2.7 and earlier are still on "javax" versions of things


`javax.persistence.*` becomes `jakarta.persistence`
`javax.faces:*` becomes `jakarta.faces:*` And so on

https://blogs.oracle.com/javamagazine/post/transition-from-java-ee-to-jakarta-ee
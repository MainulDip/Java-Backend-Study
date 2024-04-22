### JPA Streamer:
It's JAP/Hibernate with java Stream + some syntactic sugar. Its a extension of Hibernate. Upon execution, JPAStreamer will be automatically translated into `SQL` query for the Database (not in the JVM, as its native).

```java
final JPAStreamer jpaStreamer = JPAStreamer.of("sakila");

List<Film> films = jpaStreamer.stream(Film.class)
                    .filter(Film$.title.startsWith("A").and(Film$.length.greaterThan(60)))
                    .limit(10)
                    .collect(Collectors.toList());
```


### Installation:

### JPAStreamer MetaModel:
We need some info (Meta info) to translate Stream pipeline into SQL quires. This will be build automatically by IDE upon Build and will be housed inside `target/generated_source` directory with `$` suffixed.
For Eclipse, it will be on the `target/generated_source`
For Intellij, `target/generated_source` folder need to be marked as `generated-source-root`

### Hibernate and SQL Data Type matching:
For `smallint`
    - annotate with `@JdbcTypeCode(Types.SMALLINT)` where first form Hibernate and Types from SQL
    - another way is using type `Short`

https://stackoverflow.com/questions/74601781/upgrading-hibernate-to-6-1-how-to-specify-the-equivalent-of-typetype-text


https://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm

### Swagger UI:
For testing API endpoint, after starting the app, try `/q/swagger-ui/`
https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api#swaggerUi


```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>3.0.0</version>
</dependency>
```
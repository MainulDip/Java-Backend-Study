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
### Spring Graphql Server:
To start, add `Spring Web` and `Spring for GraphQL` dependencies Using the spring initializer `https://start.spring.io`.

### 1st Define Graphql Schema:
Inside resource/graphql directory, add all graphql schema and then Implement the logic to fetch the actual data for a query using java file.

* sample graphql schema
```graphql
type Query {
    bookById(id: ID): Book
}

type Book {
    id: ID
    name: String
    pageCount: Int
    author: Author
}

type Author {
    id: ID
    firstName: String
    lastName: String
}
```

* Every GraphQL schema has a top-level `Query` type, and the fields under it are the query operations exposed by the application. Here the schema defines one query called `bookById` that returns the details of a specific book.

When querying, the `bookById` is fetched by

```graphql
query bookDetails {
  bookById(id: "book-1") {
    id
    name
    pageCount
    author {
      firstName
      lastName
    }
  }
}
```


### Define Data Types:


### Define the controller to handle query:
```java
public class BookController {

    /**
     * this needs to be matched schema's query signature */
    @QueryMapping
    public Book bookById(@Argument String id) {
        return Book.getById(id);
    }

    /**
     * if a query ask for related author as its field, this needs to be matched with that */
    @SchemaMapping
    public Author author(Book book) {
        return Author.getById(book.authorId());
    }
}
```

https://docs.spring.io/spring-graphql/reference/controllers.html

### Multiple Graphql Endpoints:
https://stackoverflow.com/questions/62202051

### Graphql Configs:
https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.graphql
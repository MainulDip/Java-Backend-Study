package com.mainul.springgraphql001;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
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

package com.basaki.hateoas.data.entity;

import com.basaki.hateoas.controller.BookController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@code BookAssembler} converts a book to book resource with self link.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
public class BookAssembler extends ResourceAssemblerSupport<Book, BookResource> {

    public BookAssembler() {
        super(BookController.class, BookResource.class);
    }

    @Override
    public BookResource toResource(Book book) {
        BookResource resource = new BookResource(book);

        Link selfLink = linkTo(
                methodOn(BookController.class).read(book.getId()))
                .withSelfRel();
        resource.add(selfLink);

        return resource;
    }
}

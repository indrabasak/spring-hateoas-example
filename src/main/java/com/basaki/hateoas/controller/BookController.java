package com.basaki.hateoas.controller;

import com.basaki.hateoas.data.entity.Book;
import com.basaki.hateoas.data.entity.BookAssembler;
import com.basaki.hateoas.data.entity.BookResource;
import com.basaki.hateoas.model.BookRequest;
import com.basaki.hateoas.service.BookService;
import com.basaki.hateoas.swagger.CustomApiParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * {@code BookController} exposes book service.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
@RestController
@Slf4j
@Api(value = "Book Service", produces = "application/json", tags = {"1"})
public class BookController {

    private final BookService service;

    private BookAssembler bookAssembler;

    @Autowired
    public BookController(BookService service) {
        this.service = service;
        this.bookAssembler = new BookAssembler();
    }

    @ApiOperation(value = "Creates a book.", response = Book.class)
    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookResource> create(@CustomApiParam(
            value = "A book request.",
            example = "{title: \"My Life\", author: \"john doe\"}")
    @RequestBody BookRequest request) {
        Book book = service.create(request);
        BookResource resource = bookAssembler.toResource(book);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves a book.", notes = "Requires book identifier",
            response = Book.class)
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/books/{id}")
    public ResponseEntity<BookResource> read(@CustomApiParam(
            value = "A book id.",
            example = "{1234") @PathVariable("id") UUID id) {
        Book book = service.read(id);
        BookResource resource = bookAssembler.toResource(book);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves all books.", response = Book.class)
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/books")
    public HttpEntity<PagedResources<Book>> readAll(Pageable pageable,
            PagedResourcesAssembler assembler) {
        Page<Book> books = service.readAll(pageable);
        return new ResponseEntity<>(assembler.toResource(books), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves all books by manually creating self link.",
            response = Book.class)
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, value = "/booksx")
    public HttpEntity<PagedResources<Book>> readAllNew(Pageable pageable,
            PagedResourcesAssembler assembler) throws NoSuchMethodException {
        Page<Book> page = service.readAll(pageable);

        ControllerLinkBuilder ctrlBldr =
                linkTo(methodOn(BookController.class).readAllNew(pageable,
                        assembler));
        UriComponentsBuilder builder = ctrlBldr.toUriComponentsBuilder();

        int pageNumber = page.getPageable().getPageNumber();
        int pageSize = page.getPageable().getPageSize();
        int maxPageSize = 2000;

        builder.replaceQueryParam("page", pageNumber);
        builder.replaceQueryParam("size",
                pageSize <= maxPageSize ? page.getPageable().getPageSize() : maxPageSize);

        Link selfLink =
                new Link(new UriTemplate(builder.build().toString()), "self");
        PagedResources<Book> resources = assembler.toResource(page);
        resources.removeLinks();
        resources.add(selfLink);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}

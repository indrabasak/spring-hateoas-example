package com.basaki.hateoas.controller;

import com.basaki.hateoas.data.entity.Book;
import com.basaki.hateoas.data.entity.BookResource;
import com.basaki.hateoas.error.exception.DataNotFoundException;
import com.basaki.hateoas.model.BookRequest;
import com.basaki.hateoas.service.BookService;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * {@code BookControllerTest} represents unit test for {@code
 * BookController}.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
public class BookControllerTest {

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Indra's Chronicle");
        book.setAuthor("Indra");
        when(service.create(any(BookRequest.class))).thenReturn(book);

        BookRequest request = new BookRequest("Indra's Chronicle", "Indra");
        ResponseEntity<BookResource> result = controller.create(request);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(book, result.getBody().getBook());
    }

    @Test
    public void testRead() {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Indra's Chronicle");
        book.setAuthor("Indra");
        when(service.read(any(UUID.class))).thenReturn(book);

        ResponseEntity<BookResource> result = controller.read(UUID.randomUUID());
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(book, result.getBody().getBook());
    }

    @Test(expected = DataNotFoundException.class)
    public void testDataNotFoundRead() {
        when(service.read(any(UUID.class))).thenThrow(
                new DataNotFoundException("Not Found!"));

        controller.read(UUID.randomUUID());
    }
}

package com.basaki.hateoas.data.repository;

import com.basaki.hateoas.data.entity.Book;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * {@code BookRepository} is a JPA book repository. It servers as an example
 * for springfox-data-rest.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
public interface BookRepository
        extends PagingAndSortingRepository<Book, UUID> {
}

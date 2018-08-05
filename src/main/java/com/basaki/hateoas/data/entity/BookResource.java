package com.basaki.hateoas.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 * {@code BookResource} wraps a book entity as resource.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"id"})
public class BookResource extends ResourceSupport {

    @JsonUnwrapped
    private Book book;

    // for testing
    public BookResource() {
    }

    public BookResource(Book book) {
        this.book = book;
    }
}

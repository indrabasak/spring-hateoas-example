package com.basaki.hateoas.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


/**
 * {@code Book} represents a book entity.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
@Entity
@Table(name = "book")
@Getter
@Setter
@ApiModel(value = "Book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book implements Serializable {

    @ApiModelProperty(value = "identity of a book")
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ApiModelProperty(value = "book title")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty(value = "book author")
    @Column(name = "author", nullable = false)
    private String author;
}
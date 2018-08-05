package com.basaki.hateoas.swagger;

import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomApiParam {

    String name() default "";

    String value() default "";

    String defaultValue() default "";

    String allowableValues() default "";

    boolean required() default false;

    String access() default "";

    boolean allowMultiple() default false;

    boolean hidden() default false;

    String example() default "";

    Example examples() default @Example(value = @ExampleProperty(mediaType = "", value = ""));

    String type() default "";

    String format() default "";

    boolean allowEmptyValue() default false;

    boolean readOnly() default false;

    String collectionFormat() default "";
}

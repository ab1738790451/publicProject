package com.example.demo.annotation.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSetVal {
    String val() default "";
}

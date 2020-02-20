package de.cmoeller.persistence_enum.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PersistenceEnumField {

    String entityField() default "DEFAULT_NULL";
    Strategy mergeStrategy() default Strategy.CREATE_ONLY;

    enum Strategy {
        CREATE_ONLY, UPDATE
    }

}

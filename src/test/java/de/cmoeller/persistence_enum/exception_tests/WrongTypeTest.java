package de.cmoeller.persistence_enum.exception_tests;

import de.cmoeller.persistence_enum.exception_tests.utils.NoEnumClass;
import de.cmoeller.persistence_enum.exceptions.PersistenceEnumException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WrongTypeTest {

    @Test
    public void test_class_is_no_enum_on_get_model(){

        Exception exception = assertThrows(PersistenceEnumException.class, () -> {
            new NoEnumClass().getModel();
        });

        String expectedMessage = "is no assignable form of enum";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_class_is_no_enum_on_persist(){

        Exception exception = assertThrows(PersistenceEnumException.class, () -> {
            new NoEnumClass().persist();
        });

        String expectedMessage = "is no assignable form of enum";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}

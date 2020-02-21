package de.cmoeller.persistence_enum.exception_tests.utils;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnum;

public class NoEnumClass implements PersistenceEnum<ValidPersistenceEnumModel> {

    @Override
    public String name() {
        return null;
    }
}

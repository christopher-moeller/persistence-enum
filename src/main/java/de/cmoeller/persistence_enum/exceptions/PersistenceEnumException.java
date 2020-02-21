package de.cmoeller.persistence_enum.exceptions;

public class PersistenceEnumException extends RuntimeException {

    public static PersistenceEnumException of(Throwable cause){
        return new PersistenceEnumException(cause);
    }

    public PersistenceEnumException(Throwable cause){
        super(cause);
    }

}

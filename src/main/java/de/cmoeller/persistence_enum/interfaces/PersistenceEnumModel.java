package de.cmoeller.persistence_enum.interfaces;

public interface PersistenceEnumModel {

    public String getName();
    public void setName(String name);
    public default boolean isNew() {
        return getName() == null;
    }

}

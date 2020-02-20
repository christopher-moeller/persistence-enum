package de.cmoeller.persistence_enum.interfaces;

import java.util.Optional;

public interface PersistenceEnumRepo {

    public void save(PersistenceEnumModel model);
    public Optional<PersistenceEnumModel> getByTypeAndName(Class<? extends PersistenceEnumModel> type, String name);
    public void delete(PersistenceEnumModel model);

}

package de.cmoeller.persistence_enum.interfaces;

import de.cmoeller.persistence_enum.PersistenceEnumContext;
import de.cmoeller.persistence_enum.exceptions.PersistenceEnumException;
import de.cmoeller.persistence_enum.mapper.EnumModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

public interface PersistenceEnum<MODEL extends PersistenceEnumModel> {

    public String name();

    public default MODEL getModel(){
        Class<MODEL> modelClass = getModelClass();
        EnumModelMapper mapper = new EnumModelMapper();
        Optional<MODEL> persistedModel = PersistenceEnumContext.getInstance().findPersistenceModelByName(modelClass, name());

        if(!(this instanceof Enum))
            throw PersistenceEnumException.of(new IllegalStateException(String.format("Class %s is no assignable form of enum", this.getClass().getSimpleName())));

        return persistedModel.orElse(mapper.map((Enum) this, modelClass));
    }

    public default Class<MODEL> getModelClass(){

        ParameterizedType parameterizedTypes = Arrays.asList(this.getClass().getGenericInterfaces())
                .stream()
                .map(ParameterizedType.class::cast)
                .filter(type -> type.getRawType().equals(PersistenceEnum.class))
                .findAny()
                .get();

        return (Class<MODEL>) parameterizedTypes.getActualTypeArguments()[0];
    }

    public default void persist(){
        PersistenceEnumContext context = PersistenceEnumContext.getInstance();
        context.persistEnum(this);
    }

}

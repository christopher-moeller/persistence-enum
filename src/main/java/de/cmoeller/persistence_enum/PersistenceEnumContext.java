package de.cmoeller.persistence_enum;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnum;
import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;
import de.cmoeller.persistence_enum.interfaces.PersistenceEnumRepo;
import de.cmoeller.persistence_enum.mapper.EnumModelMapper;
import de.cmoeller.persistence_enum.util.DefaultInMemoryPersistenceEnumRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PersistenceEnumContext {

    private static PersistenceEnumContext instance = null;

    private PersistenceEnumRepo persistenceEnumRepo;

    private List<Class<? extends Enum>> enumsInContext = new ArrayList<>();

    private PersistenceEnumContext(){
        this.persistenceEnumRepo = new DefaultInMemoryPersistenceEnumRepo();
    }

    public static PersistenceEnumContext getInstance(){
        if(instance == null)
            instance = new PersistenceEnumContext();

        return instance;
    }

    public <MODEL extends PersistenceEnumModel> Optional<MODEL> findPersistenceModelByName(Class<MODEL> modelType, String name){
        return this.persistenceEnumRepo.getByTypeAndName(modelType, name)
                .map(model -> (MODEL) model);
    }

    public void persistEnum(PersistenceEnum<?> persistenceEnum){

        if(!(persistenceEnum instanceof Enum))
            throw new RuntimeException(String.format("Instance of class %s is not of type %s", persistenceEnum.getClass().getSimpleName(), Enum.class.getSimpleName()));

        Enum<?> enumEntry = (Enum<?>) persistenceEnum;

        EnumModelMapper mapper = new EnumModelMapper();
        this.persistenceEnumRepo.getByTypeAndName(persistenceEnum.getModelClass(), persistenceEnum.name());
        Optional<PersistenceEnumModel> foundModel = this.persistenceEnumRepo.getByTypeAndName(persistenceEnum.getModelClass(), persistenceEnum.name());
        PersistenceEnumModel model = foundModel.isPresent() ? mapper.mapInExistingModel(enumEntry, foundModel.get()) : mapper.map(enumEntry, persistenceEnum.getModelClass());

        this.persistenceEnumRepo.save(model);
    }

    public void addEnumsToContext(Class<? extends Enum> ...enums){
        addEnumsToContext(Arrays.asList(enums));
    }

    public void addEnumsToContext(List<Class<? extends Enum>> enums){
        enumsInContext.addAll(enums);
    }

    public void persistAllEnums(){
        for(Class<? extends Enum> enumType : enumsInContext){
            Enum[] enums = enumType.getEnumConstants();
            Arrays.asList(enums)
                    .stream()
                    .map(PersistenceEnum.class::cast)
                    .forEach(PersistenceEnum::persist);
        }
    }

    public PersistenceEnumRepo getPersistenceEnumRepo() {
        return persistenceEnumRepo;
    }

    public void setPersistenceEnumRepo(PersistenceEnumRepo persistenceEnumRepo) {
        this.persistenceEnumRepo = persistenceEnumRepo;
    }
}

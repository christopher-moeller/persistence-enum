package de.cmoeller.persistence_enum.util;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;
import de.cmoeller.persistence_enum.interfaces.PersistenceEnumRepo;

import java.util.*;

public class DefaultInMemoryPersistenceEnumRepo implements PersistenceEnumRepo {

    private Map<Class<? extends PersistenceEnumModel>, List<PersistenceEnumModel>> memory;

    public DefaultInMemoryPersistenceEnumRepo(){
        this.memory = new HashMap<>();
    }

    @Override
    public void save(PersistenceEnumModel model) {

        if(!memory.containsKey(model.getClass())){
            memory.put(model.getClass(), new ArrayList<>());
        }

        List<PersistenceEnumModel> modelList = memory.get(model.getClass());
        getModelInList(modelList, model).ifPresent(modelList::remove);

        modelList.add(model);
    }

    @Override
    public Optional<PersistenceEnumModel> getByTypeAndName(Class<? extends PersistenceEnumModel> type, String name) {

        if(!memory.containsKey(type))
            return Optional.empty();

        return memory.get(type).stream()
                .filter(model -> model.getName().equals(name))
                .findAny();
    }

    @Override
    public void delete(PersistenceEnumModel model) {
        Optional<PersistenceEnumModel> foundModel = getByTypeAndName(model.getClass(), model.getName());
        foundModel.ifPresent(item -> memory.get(item.getClass()).remove(item));
    }

    private Optional<PersistenceEnumModel> getModelInList(List<PersistenceEnumModel> modelList, PersistenceEnumModel modelA){
        return modelList.stream()
                .filter(modelB -> modelAEqualsB(modelA, modelB))
                .findAny();
    }

    private boolean modelAEqualsB(PersistenceEnumModel modelA, PersistenceEnumModel modelB){
        return modelA.getClass().equals(modelB.getClass()) && modelA.getName().equals(modelB.getName());
    }

    @Override
    public String toString() {
        return "DefaultInMemoryPersistenceEnumRepo{" +
                "memory=" + memory +
                '}';
    }
}

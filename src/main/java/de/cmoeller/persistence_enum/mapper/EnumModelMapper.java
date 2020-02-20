package de.cmoeller.persistence_enum.mapper;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;
import de.cmoeller.persistence_enum.annotations.PersistenceEnumField;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class EnumModelMapper {

    public <MODEL extends PersistenceEnumModel> MODEL mapInExistingModel(Enum enumEntry, MODEL model){
        Map<String, FieldValueWrapper> result = createValueMapForEntity(enumEntry, getEnumFields(enumEntry.getClass()));
        return mapToExistingEntity(model, result, enumEntry.name());
    }

    public <MODEL extends PersistenceEnumModel> MODEL map(Enum enumEntry, Class<MODEL> modelClass){
        Map<String, FieldValueWrapper> result = createValueMapForEntity(enumEntry, getEnumFields(enumEntry.getClass()));
        return mapToEntity(modelClass, result, enumEntry.name());
    }

    public <MODEL extends PersistenceEnumModel> List<MODEL> mapAll(Class<? extends Enum> enumClass, Class<MODEL> modelClass){

        return Arrays.asList(enumClass.getEnumConstants())
                .stream()
                .map(entry -> map(entry, modelClass))
                .collect(Collectors.toList());
    }

    private List<String> getEnumFields(Class<? extends Enum> enumClass){

        return Arrays.asList(enumClass.getDeclaredFields())
                .stream()
                .filter(field -> !field.isSynthetic() && !field.isEnumConstant())
                .map(field -> field.getName())
                .collect(Collectors.toList());


    }

    private Map<String, FieldValueWrapper> createValueMapForEntity(Enum enumEntry, List<String> fieldNames){
        return fieldNames
                .stream()
                .map(fieldName -> getDeclaringEnumField(enumEntry, fieldName))
                .collect(Collectors.toMap(this::getEnumEntityFieldName, field -> new FieldValueWrapper(getEnumFieldValue(field, enumEntry), getEnumEntityMergeStrategy(field))));
    }

    private Field getDeclaringEnumField(Enum enumEntry, String fieldName){
        try{
            return enumEntry.getDeclaringClass().getDeclaredField(fieldName);
        }catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Enum '%s' has no field '%s'", enumEntry.getDeclaringClass().getSimpleName(), fieldName));
        }
    }

    private String getEnumEntityFieldName(Field keyField){
        PersistenceEnumField persistenceEnumAnnotation = keyField.getAnnotation(PersistenceEnumField.class);
        return persistenceEnumAnnotation != null && !persistenceEnumAnnotation.entityField().equals("DEFAULT_NULL") ? persistenceEnumAnnotation.entityField() : keyField.getName();
    }

    private PersistenceEnumField.Strategy getEnumEntityMergeStrategy(Field keyField){
        PersistenceEnumField persistenceEnumAnnotation = keyField.getAnnotation(PersistenceEnumField.class);
        return persistenceEnumAnnotation != null ? persistenceEnumAnnotation.mergeStrategy() : PersistenceEnumField.Strategy.CREATE_ONLY;
    }

    private Object getEnumFieldValue(Field field, Enum enumEntry)  {
        boolean isAccessible = field.isAccessible();
        field.setAccessible(true);
        try{
            return field.get(enumEntry);
        }catch (IllegalAccessException e){
            throw new RuntimeException(e.getMessage());
        }finally {
            field.setAccessible(isAccessible);
        }
    }

    private <ENTITY extends PersistenceEnumModel> ENTITY mapToExistingEntity(ENTITY modelInstance, Map<String, FieldValueWrapper> fieldValueMap, String enumName){

        try{

            Class<? extends PersistenceEnumModel> type = modelInstance.getClass();
            modelInstance.setName(enumName);

            for(String key : fieldValueMap.keySet()){
                Field field = type.getDeclaredField(key);

                FieldValueWrapper fieldValueWrapper = fieldValueMap.get(key);
                if(!modelInstance.isNew() && fieldValueWrapper.mergeStrategy.equals(PersistenceEnumField.Strategy.CREATE_ONLY))
                    continue;

                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                field.set(modelInstance, fieldValueWrapper.fieldValue);
                field.setAccessible(isAccessible);
            }

            return modelInstance;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private <ENTITY extends PersistenceEnumModel> ENTITY mapToEntity(Class<ENTITY> type,  Map<String, FieldValueWrapper> fieldValueMap, String enumName){

        try{

            ENTITY modelInstance = type.newInstance();
            return mapToExistingEntity(modelInstance, fieldValueMap, enumName);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private class FieldValueWrapper {

        private Object fieldValue;
        private PersistenceEnumField.Strategy mergeStrategy;

        public FieldValueWrapper(Object fieldValue, PersistenceEnumField.Strategy mergeStrategy) {
            this.fieldValue = fieldValue;
            this.mergeStrategy = mergeStrategy;
        }
    }

}

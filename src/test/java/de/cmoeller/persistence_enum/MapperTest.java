package de.cmoeller.persistence_enum;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;
import de.cmoeller.persistence_enum.mapper.EnumModelMapper;
import de.cmoeller.persistence_enum.util.singleMapping.MyEntity;
import de.cmoeller.persistence_enum.util.singleMapping.MyEntityEnum;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    @Test
    public void singleMappingTest1(){

        EnumModelMapper mapper = new EnumModelMapper();
        MyEntity myEntity = mapper.map(MyEntityEnum.VALUE_1, MyEntity.class);

        assertEquals(MyEntityEnum.VALUE_1.name(), myEntity.getName());

        assertEquals("label_of_value1", myEntity.getLabel());
    }

    @Test
    public void allMappingTest1(){

        EnumModelMapper mapper = new EnumModelMapper();

        List<MyEntity> entityList = mapper.mapAll(MyEntityEnum.class, MyEntity.class);
        assertEquals(entityList.size(), MyEntityEnum.values().length);

        Optional<MyEntity> value1Entity = findModelByNameInList(entityList, MyEntityEnum.VALUE_1.name());
        assertTrue(value1Entity.isPresent());

        assertEquals("label_of_value1", value1Entity.get().getLabel());

        Optional<MyEntity> value2Entity = findModelByNameInList(entityList, MyEntityEnum.VALUE_2.name());
        assertTrue(value1Entity.isPresent());

        assertEquals("label_of_value2", value2Entity.get().getLabel());

    }

    private <MODEL extends PersistenceEnumModel> Optional<MODEL> findModelByNameInList(List<MODEL> models, String name){
        return models.stream()
                .filter(item -> item.getName().equals(name))
                .findAny();
    }

}

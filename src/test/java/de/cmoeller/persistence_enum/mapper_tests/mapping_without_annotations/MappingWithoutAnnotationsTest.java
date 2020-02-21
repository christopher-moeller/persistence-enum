package de.cmoeller.persistence_enum.mapper_tests.mapping_without_annotations;

import de.cmoeller.persistence_enum.mapper.EnumModelMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MappingWithoutAnnotationsTest {

    private static final String CHANGED_LABEL_1 = "changed_label_1";
    private static final String CHANGED_LABEL_2 = "changed_label_2";

    @Test
    public void test_single_mapping(){
        EnumModelMapper mapper = new EnumModelMapper();
        ModelWithoutAnnotations model = mapper.map(EnumWithoutAnnotation.VALUE_1, ModelWithoutAnnotations.class);

        assertNotNull(model);
        assertNull(model.getId());
        assertEquals(EnumWithoutAnnotation.VALUE_1.name(), model.getName());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel1(), model.getLabel1());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel2(), model.getLabel2());

    }

    @Test
    public void test_multi_mapping(){
        EnumModelMapper mapper = new EnumModelMapper();

        List<ModelWithoutAnnotations> results = mapper.mapAll(EnumWithoutAnnotation.class, ModelWithoutAnnotations.class);
        assertEquals(EnumWithoutAnnotation.values().length, results.size());

        for(int i=0; i<EnumWithoutAnnotation.values().length; i++){
            EnumWithoutAnnotation enumEntry = EnumWithoutAnnotation.values()[i];
            ModelWithoutAnnotations modelEntry = results.get(i);

            assertNull(modelEntry.getId());
            assertEquals(enumEntry.name(), modelEntry.getName());
            assertEquals(enumEntry.getLabel1(), modelEntry.getLabel1());
            assertEquals(enumEntry.getLabel2(), modelEntry.getLabel2());
        }
    }

    @Test
    public void test_single_mapping_into_existing_model(){
        EnumModelMapper mapper = new EnumModelMapper();

        ModelWithoutAnnotations model = createNotEmptyModel();
        ModelWithoutAnnotations mappedModel = mapper.mapInExistingModel(EnumWithoutAnnotation.VALUE_1, model.clone());

        assertEquals(model.getId(), mappedModel.getId());
        assertEquals(model.getLabel1(), mappedModel.getLabel1());
        assertEquals(model.getLabel2(), mappedModel.getLabel2());

        assertEquals(EnumWithoutAnnotation.VALUE_1.name(), mappedModel.getName());
    }

    @Test
    public void test_own_mapping() {

        ModelWithoutAnnotations model = EnumWithoutAnnotation.VALUE_1.getModel();

        assertNotNull(model);
        assertNull(model.getId());
        assertEquals(EnumWithoutAnnotation.VALUE_1.name(), model.getName());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel1(), model.getLabel1());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel2(), model.getLabel2());
    }

    private ModelWithoutAnnotations createNotEmptyModel(){
        ModelWithoutAnnotations model = new ModelWithoutAnnotations();
        model.setName(EnumWithoutAnnotation.VALUE_1.name());
        model.setId(1L);
        model.setLabel1(CHANGED_LABEL_1);
        model.setLabel2(CHANGED_LABEL_2);
        return model;
    }


}

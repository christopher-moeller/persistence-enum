package de.cmoeller.persistence_enum.mapper_tests.mapping_with_annotations;

import de.cmoeller.persistence_enum.mapper.EnumModelMapper;
import de.cmoeller.persistence_enum.mapper_tests.mapping_without_annotations.EnumWithoutAnnotation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MappingWithAnnotationsTest {

    private static final String CHANGED_LABEL_1 = "changed_label_1";
    private static final String CHANGED_LABEL_2 = "changed_label_2";

    @Test
    public void test_single_mapping(){
        EnumModelMapper mapper = new EnumModelMapper();
        ModelWithAnnotations model = mapper.map(EnumWithAnnotation.VALUE_1, ModelWithAnnotations.class);

        assertNotNull(model);
        assertNull(model.getId());
        assertEquals(EnumWithoutAnnotation.VALUE_1.name(), model.getName());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel1(), model.getLabelOne());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel2(), model.getLabelTwo());

    }

    @Test
    public void test_multi_mapping(){
        EnumModelMapper mapper = new EnumModelMapper();

        List<ModelWithAnnotations> results = mapper.mapAll(EnumWithAnnotation.class, ModelWithAnnotations.class);
        assertEquals(EnumWithAnnotation.values().length, results.size());

        for(int i=0; i<EnumWithAnnotation.values().length; i++){
            EnumWithAnnotation enumEntry = EnumWithAnnotation.values()[i];
            ModelWithAnnotations modelEntry = results.get(i);

            assertNull(modelEntry.getId());
            assertEquals(enumEntry.name(), modelEntry.getName());
            assertEquals(enumEntry.getLabel1(), modelEntry.getLabelOne());
            assertEquals(enumEntry.getLabel2(), modelEntry.getLabelTwo());
        }
    }

    @Test
    public void test_single_mapping_into_existing_model(){
        EnumModelMapper mapper = new EnumModelMapper();

        ModelWithAnnotations model = createNotEmptyModel();
        ModelWithAnnotations mappedModel = mapper.mapInExistingModel(EnumWithAnnotation.VALUE_1, model.clone());

        assertEquals(model.getId(), mappedModel.getId());
        assertEquals(model.getLabelOne(), mappedModel.getLabelOne());
        assertNotEquals(model.getLabelTwo(), mappedModel.getLabelTwo());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel2(), mappedModel.getLabelTwo());

        assertEquals(EnumWithAnnotation.VALUE_1.name(), mappedModel.getName());
    }

    @Test
    public void test_own_mapping() {

        ModelWithAnnotations model = EnumWithAnnotation.VALUE_1.getModel();

        assertNotNull(model);
        assertNull(model.getId());
        assertEquals(EnumWithAnnotation.VALUE_1.name(), model.getName());
        assertEquals(EnumWithAnnotation.VALUE_1.getLabel1(), model.getLabelOne());
        assertEquals(EnumWithAnnotation.VALUE_1.getLabel2(), model.getLabelTwo());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel2(), model.getLabelTwo());
    }

    private ModelWithAnnotations createNotEmptyModel(){
        ModelWithAnnotations model = new ModelWithAnnotations();
        model.setName(EnumWithAnnotation.VALUE_1.name());
        model.setId(1L);
        model.setLabelOne(CHANGED_LABEL_1);
        model.setLabelTwo(CHANGED_LABEL_2);
        return model;
    }


}

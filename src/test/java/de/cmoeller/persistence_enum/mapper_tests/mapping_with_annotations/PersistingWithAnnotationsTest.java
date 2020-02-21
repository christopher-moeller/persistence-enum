package de.cmoeller.persistence_enum.mapper_tests.mapping_with_annotations;

import de.cmoeller.persistence_enum.PersistenceEnumContext;
import de.cmoeller.persistence_enum.util.DefaultInMemoryPersistenceEnumRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersistingWithAnnotationsTest {

    private static final String CHANGED_LABEL_1 = "changed_label_1";
    private static final String CHANGED_LABEL_2 = "changed_label_2";

    @BeforeEach
    public void initContext(){
        PersistenceEnumContext.getInstance().setPersistenceEnumRepo(new DefaultInMemoryPersistenceEnumRepo());
    }

    @Test
    public void single_persist_test(){

        EnumWithAnnotation.VALUE_1.persist();

        Optional<ModelWithAnnotations> model = PersistenceEnumContext
                .getInstance()
                .findPersistenceModelByName(ModelWithAnnotations.class, EnumWithAnnotation.VALUE_1.name());

        assertTrue(model.isPresent());
        assertEquals(EnumWithAnnotation.VALUE_1.name(), model.get().getName());
        assertEquals(EnumWithAnnotation.VALUE_1.getLabel1(), model.get().getLabelOne());
        assertEquals(EnumWithAnnotation.VALUE_1.getLabel2(), model.get().getLabelTwo());

    }

    @Test
    public void multi_persist_test(){

        PersistenceEnumContext.getInstance().addEnumsToContext(EnumWithAnnotation.class);
        PersistenceEnumContext.getInstance().persistAllEnums();

        for(EnumWithAnnotation enumWithAnnotation : EnumWithAnnotation.values()){

            Optional<ModelWithAnnotations> model = PersistenceEnumContext.getInstance()
                    .findPersistenceModelByName(ModelWithAnnotations.class, enumWithAnnotation.name());

            assertTrue(model.isPresent());
            assertEquals(enumWithAnnotation.name(), model.get().getName());
            assertEquals(enumWithAnnotation.getLabel1(), model.get().getLabelOne());
            assertEquals(enumWithAnnotation.getLabel2(), model.get().getLabelTwo());
        }
    }

    @Test
    public void persist_and_get_model_by_own_test(){

        EnumWithAnnotation.VALUE_1.persist();

        Optional<ModelWithAnnotations> opModel = PersistenceEnumContext
                .getInstance()
                .findPersistenceModelByName(ModelWithAnnotations.class, EnumWithAnnotation.VALUE_1.name());

        assertTrue(opModel.isPresent());

        ModelWithAnnotations persistedModel = opModel.get();
        persistedModel.setLabelOne(CHANGED_LABEL_1);
        persistedModel.setLabelTwo(CHANGED_LABEL_2);

        PersistenceEnumContext.getInstance().getPersistenceEnumRepo().save(persistedModel);

        EnumWithAnnotation.VALUE_1.persist();

        ModelWithAnnotations model = EnumWithAnnotation.VALUE_1.getModel();
        assertEquals(EnumWithAnnotation.VALUE_1.name(), model.getName());
        assertEquals(CHANGED_LABEL_1, model.getLabelOne());
        assertEquals(EnumWithAnnotation.VALUE_1.getLabel2(), model.getLabelTwo());


    }

    @Test
    public void multi_persist_and_get_model_by_own_test(){

        PersistenceEnumContext.getInstance().addEnumsToContext(EnumWithAnnotation.class);
        PersistenceEnumContext.getInstance().persistAllEnums();

        for(EnumWithAnnotation enumWithAnnotation : EnumWithAnnotation.values()){

            Optional<ModelWithAnnotations> opModel = PersistenceEnumContext.getInstance()
                    .findPersistenceModelByName(ModelWithAnnotations.class, enumWithAnnotation.name());

            assertTrue(opModel.isPresent());

            ModelWithAnnotations persistedModel = opModel.get();
            persistedModel.setLabelOne(CHANGED_LABEL_1);
            persistedModel.setLabelTwo(CHANGED_LABEL_2);

            PersistenceEnumContext.getInstance().getPersistenceEnumRepo().save(persistedModel);

            enumWithAnnotation.persist();

            ModelWithAnnotations model = enumWithAnnotation.getModel();
            assertEquals(enumWithAnnotation.name(), model.getName());
            assertEquals(CHANGED_LABEL_1, model.getLabelOne());
            assertEquals(enumWithAnnotation.getLabel2(), model.getLabelTwo());

        }

    }

}

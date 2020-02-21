package de.cmoeller.persistence_enum.mapper_tests.mapping_without_annotations;

import de.cmoeller.persistence_enum.PersistenceEnumContext;
import de.cmoeller.persistence_enum.util.DefaultInMemoryPersistenceEnumRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PersistingWithoutAnnotationsTest {

    private static final String CHANGED_LABEL_1 = "changed_label_1";
    private static final String CHANGED_LABEL_2 = "changed_label_2";

    @BeforeEach
    public void initContext(){
        PersistenceEnumContext.getInstance().setPersistenceEnumRepo(new DefaultInMemoryPersistenceEnumRepo());
    }

    @Test
    public void single_persist_test(){

        EnumWithoutAnnotation.VALUE_1.persist();

        Optional<ModelWithoutAnnotations> model = PersistenceEnumContext
                .getInstance()
                .findPersistenceModelByName(ModelWithoutAnnotations.class, EnumWithoutAnnotation.VALUE_1.name());

        assertTrue(model.isPresent());
        assertEquals(EnumWithoutAnnotation.VALUE_1.name(), model.get().getName());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel1(), model.get().getLabel1());
        assertEquals(EnumWithoutAnnotation.VALUE_1.getLabel2(), model.get().getLabel2());

    }

    @Test
    public void multi_persist_test(){

        PersistenceEnumContext.getInstance().addEnumsToContext(EnumWithoutAnnotation.class);
        PersistenceEnumContext.getInstance().persistAllEnums();

        for(EnumWithoutAnnotation enumWithoutAnnotation : EnumWithoutAnnotation.values()){

            Optional<ModelWithoutAnnotations> model = PersistenceEnumContext.getInstance()
                    .findPersistenceModelByName(ModelWithoutAnnotations.class, enumWithoutAnnotation.name());

            assertTrue(model.isPresent());
            assertEquals(enumWithoutAnnotation.name(), model.get().getName());
            assertEquals(enumWithoutAnnotation.getLabel1(), model.get().getLabel1());
            assertEquals(enumWithoutAnnotation.getLabel2(), model.get().getLabel2());
        }
    }

    @Test
    public void persist_and_get_model_by_own_test(){

        EnumWithoutAnnotation.VALUE_1.persist();

        Optional<ModelWithoutAnnotations> opModel = PersistenceEnumContext
                .getInstance()
                .findPersistenceModelByName(ModelWithoutAnnotations.class, EnumWithoutAnnotation.VALUE_1.name());

        assertTrue(opModel.isPresent());

        ModelWithoutAnnotations persistedModel = opModel.get().clone();
        persistedModel.setLabel1(CHANGED_LABEL_1);
        persistedModel.setLabel2(CHANGED_LABEL_2);

        PersistenceEnumContext.getInstance().getPersistenceEnumRepo().save(persistedModel);

        EnumWithoutAnnotation.VALUE_1.persist();

        ModelWithoutAnnotations model = EnumWithoutAnnotation.VALUE_1.getModel();
        assertEquals(EnumWithoutAnnotation.VALUE_1.name(), model.getName());
        assertEquals(CHANGED_LABEL_1, model.getLabel1());
        assertEquals(CHANGED_LABEL_2, model.getLabel2());


    }

    @Test
    public void multi_persist_and_get_model_by_own_test(){

        PersistenceEnumContext.getInstance().addEnumsToContext(EnumWithoutAnnotation.class);
        PersistenceEnumContext.getInstance().persistAllEnums();

        for(EnumWithoutAnnotation enumWithoutAnnotation : EnumWithoutAnnotation.values()){

            Optional<ModelWithoutAnnotations> opModel = PersistenceEnumContext.getInstance()
                    .findPersistenceModelByName(ModelWithoutAnnotations.class, enumWithoutAnnotation.name());

            assertTrue(opModel.isPresent());

            ModelWithoutAnnotations persistedModel = opModel.get();
            persistedModel.setLabel1(CHANGED_LABEL_1);
            persistedModel.setLabel2(CHANGED_LABEL_2);

            PersistenceEnumContext.getInstance().getPersistenceEnumRepo().save(persistedModel);

            ModelWithoutAnnotations model = enumWithoutAnnotation.getModel();
            assertEquals(enumWithoutAnnotation.name(), model.getName());
            assertEquals(CHANGED_LABEL_1, model.getLabel1());
            assertEquals(CHANGED_LABEL_2, model.getLabel2());

        }

    }

}

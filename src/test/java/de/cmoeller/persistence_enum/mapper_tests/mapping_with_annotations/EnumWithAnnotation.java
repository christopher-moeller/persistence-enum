package de.cmoeller.persistence_enum.mapper_tests.mapping_with_annotations;

import de.cmoeller.persistence_enum.annotations.PersistenceEnumField;
import de.cmoeller.persistence_enum.interfaces.PersistenceEnum;

public enum EnumWithAnnotation implements PersistenceEnum<ModelWithAnnotations> {

    VALUE_1("V1L1", "V1L2"), VALUE_2("V2L1", "V2L2");

    @PersistenceEnumField(entityField = "labelOne")
    private String label1;

    @PersistenceEnumField(entityField = "labelTwo", mergeStrategy = PersistenceEnumField.Strategy.UPDATE)
    private String label2;

    EnumWithAnnotation(String label1, String label2){
        this.label1 = label1;
        this.label2 = label2;
    }

    public String getLabel1() {
        return label1;
    }

    public String getLabel2() {
        return label2;
    }
}

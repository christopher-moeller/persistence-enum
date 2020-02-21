package de.cmoeller.persistence_enum.mapper_tests.mapping_without_annotations;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnum;

public enum EnumWithoutAnnotation implements PersistenceEnum<ModelWithoutAnnotations> {

    VALUE_1("V1L1", "V1L2"), VALUE_2("V2L1", "V2L2");

    private String label1;
    private String label2;

    EnumWithoutAnnotation(String label1, String label2){
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

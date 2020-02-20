package de.cmoeller.persistence_enum.util.singleMapping;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnum;
import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;
import de.cmoeller.persistence_enum.annotations.PersistenceEnumField;

public enum MyEntityEnum implements PersistenceEnum<PersistenceEnumModel> {

    VALUE_1("label_of_value1"),
    VALUE_2("label_of_value2");

    MyEntityEnum(String label){
        this.bla = label;
    }

    @PersistenceEnumField(entityField = "label")
    private String bla;

}

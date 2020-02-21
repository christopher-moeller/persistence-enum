package de.cmoeller.persistence_enum.mapper_tests.mapping_with_annotations;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;

public class ModelWithAnnotations implements PersistenceEnumModel, Cloneable {

    private Long id;
    private String name;
    private String labelOne;
    private String labelTwo;

    public String getLabelOne() {
        return labelOne;
    }

    public void setLabelOne(String labelOne) {
        this.labelOne = labelOne;
    }

    public String getLabelTwo() {
        return labelTwo;
    }

    public void setLabelTwo(String labelTwo) {
        this.labelTwo = labelTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ModelWithAnnotations clone() {

        try {
            return (ModelWithAnnotations) super.clone();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

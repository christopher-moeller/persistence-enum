package de.cmoeller.persistence_enum.mapper_tests.mapping_without_annotations;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;

public class ModelWithoutAnnotations implements PersistenceEnumModel, Cloneable {

    private Long id;
    private String name;
    private String label1;
    private String label2;

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
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

    protected ModelWithoutAnnotations clone() {
        try {
            return (ModelWithoutAnnotations) super.clone();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}

package de.cmoeller.persistence_enum.util.singleMapping;

import de.cmoeller.persistence_enum.interfaces.PersistenceEnumModel;

public class MyEntity implements PersistenceEnumModel {

    private Long id;

    private String name;

    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }
}

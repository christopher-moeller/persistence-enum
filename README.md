# persistence-enum

This library helps developers access entities, wich are linked with enum values. 

### Only two interfaces are important for this:

- PersistenceEnumModel: used to sign an entity as an model for a PersistenceEnum
- PersistenceEnum<? extends PersistenceEnumModel>: used to sign an enum as PersistenceEnum for a specific PersistenceEnumModel

### Example of an PersistenceEnumModel:

```java

public class RightEntity implements PersistenceEnumModel {

  private String name;
  
  private String label;
  
  private String description;
  
  @Override
  private String getName(){
    return name;
  }
  
  @Override
  private void setName(String name) {
    this.name = name;
  }
}

```

### Example of an PersistenceEnum linked with RightEntity:


```java

public enum RightEnum implements PersistenceEnum<RightEntit> {

  FIRST_RIGHT("label_1", "helptext_1"),
  SECCOND_RIGHT("label_2", "helptext_2");

  private String label;
  
  @PersistenceEnumField(entityField = "description", mergeStrategy = Strategy.UPDATE)
  private String helpText;

  RightEnum(String label, String helpText) {
    this.label = label;
    this.helpText = helpText;
  }

}

```
### Usage:

```java

//set a repository for your database
PersistenceEnumContext.getInstance().setPersistenceEnumRepo(new PersistenceEnumRepo{ ... } );

//add your enum to persistence context and persist all
PersistenceEnumContext.getInstance().addEnumsToContext(RightEnum.class);
PersistenceEnumContext.getInstance().persistAllEnums();

//or persist a single model to your database
RightEnum.FIRST_RIGHT.persist();

// ... do something with the RightEntity in your database

// fetch the current database entity by enum
RightEntity entity = RightEnum.FIRST_RIGHT.getModel()

```

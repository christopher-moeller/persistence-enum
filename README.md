# persistence-enum

This library helps developers access entities, wich are linked with enum values. 

Only two interfaces are important for this:

- PersistenceEnumModel: used to sign an entity as an model for a PersistenceEnum
- PersistenceEnum<? extends PersistenceEnumModel>: used to sign an enum as PersistenceEnum for a specific PersistenceEnumModel

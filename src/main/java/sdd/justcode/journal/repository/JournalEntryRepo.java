package sdd.justcode.journal.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sdd.justcode.journal.entity.JournalEntity;

public interface JournalEntryRepo extends MongoRepository<JournalEntity, ObjectId>{


}

//MongoRepository is the interface which is used to apply crud operations directly
//provided by mongo db dependency

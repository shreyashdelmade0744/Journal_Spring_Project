package sdd.justcode.journal.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import sdd.justcode.journal.entity.UserEntity;

public interface UserEntryRepo extends MongoRepository<UserEntity, ObjectId>{

     UserEntity findByUsername(String username);

}

//MongoRepository is the interface which is used to apply crud operations directly
//provided by mongo db dependency

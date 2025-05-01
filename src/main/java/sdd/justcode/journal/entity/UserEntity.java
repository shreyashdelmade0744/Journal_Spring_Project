package sdd.justcode.journal.entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data // contains getter and setter
public class UserEntity {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntity> journalEntries = new ArrayList<>();

    //for authorization
    private List<String> roles; // what the user is authorized to do , if the user is authenticated
    // roles means what he can do like he is normal user , admin or etc


}

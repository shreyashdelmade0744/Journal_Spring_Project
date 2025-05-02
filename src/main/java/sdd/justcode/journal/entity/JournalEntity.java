package sdd.justcode.journal.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection  = "journal_entries")
@Getter // this comes under lombok
@Data
@NoArgsConstructor
@Setter//this comes under lombok
public class JournalEntity {
    @Id
    private ObjectId id;

    @NonNull
    private String title;
    private String content;
    private LocalDateTime data;

}


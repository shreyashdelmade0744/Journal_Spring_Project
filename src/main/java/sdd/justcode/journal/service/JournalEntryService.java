package sdd.justcode.journal.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sdd.justcode.journal.entity.JournalEntity;
import sdd.justcode.journal.repository.JournalEntryRepo;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo JournalEntryRepo ;

    public void saveJournalEntry(JournalEntity journalEntity){
        JournalEntryRepo.save(journalEntity);
    }

    public List<JournalEntity> getAll(){
        return JournalEntryRepo.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id){
        return JournalEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        JournalEntryRepo.deleteById(id);
    }


}

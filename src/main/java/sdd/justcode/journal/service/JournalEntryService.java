package sdd.justcode.journal.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sdd.justcode.journal.entity.JournalEntity;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.repository.JournalEntryRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo JournalEntryRepo ;
    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveJournalEntry(JournalEntity journalEntry, String username){
        try {
        UserEntity user = userEntryService.findByUsername(username);
        journalEntry.setData(LocalDateTime.now());
        JournalEntity saved = JournalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userEntryService.saveUserEntry(user);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void saveJournalEntry(JournalEntity oldEntry) {
        JournalEntryRepo.save(oldEntry);
    }

    public List<JournalEntity> getAll(){
        return JournalEntryRepo.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id){
        return JournalEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id, String username){
        UserEntity user = userEntryService.findByUsername(username);
        user.getJournalEntries().removeIf(x-> x.getId().equals(id));
        userEntryService.saveUserEntry(user);
        JournalEntryRepo.deleteById(id);
    }

}

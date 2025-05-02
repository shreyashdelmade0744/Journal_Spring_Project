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
        userEntryService.saveUser(user);
        }
        catch(Exception e){
            System.out.println("service error: " + e.getMessage()); // for debug
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

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        boolean removed = false;
       try {
            UserEntity user = userEntryService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userEntryService.saveUser(user);
                JournalEntryRepo.deleteById(id);
            }

        } catch (Exception e) {
           System.out.println(e);
           throw new RuntimeException("error while deleting entry",e);
       }
       return  removed;
    }

}

package sdd.justcode.journal.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdd.justcode.journal.entity.JournalEntity;
import sdd.justcode.journal.repository.JournalEntryRepo;
import sdd.justcode.journal.service.JournalEntryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//controller ----> service -----> repository

@RestController
@RequestMapping("/journal-v2")
public class JournalEntryControllerV2 {

    @Autowired//this is dependency injection

    private JournalEntryService JournalEntryService;

    @GetMapping("/getall")
    public ResponseEntity<?> getAll() {
        List<JournalEntity> all = JournalEntryService.getAll();
        if (all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all); // 200 OK with data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found"); // or empty list/message
        }
    }

    @PostMapping("/adduser")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity je){
        try {
            je.setData(LocalDateTime.now());
            JournalEntryService.saveJournalEntry(je);
            return new ResponseEntity<>(je, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }//working

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntity> findbyId(@PathVariable ObjectId myid){
        Optional<JournalEntity> journalEntry = JournalEntryService.findById(myid);
        if(journalEntry.isPresent()){
         return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }//working

    @DeleteMapping("/id/{myid}")
    public boolean deletebyId(@PathVariable ObjectId myid){
         JournalEntryService.deleteById(myid);
         return true;
    }//working

    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myid,@RequestBody JournalEntity newEntry){
        JournalEntity oldEntry = JournalEntryService.findById(myid).orElse(null);
        if(oldEntry!=null){
            oldEntry.setTitle((newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty()) ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((newEntry.getContent()!=null && !newEntry.getContent().isEmpty()) ? newEntry.getContent() : oldEntry.getContent());
            JournalEntryService.saveJournalEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } //working

    }

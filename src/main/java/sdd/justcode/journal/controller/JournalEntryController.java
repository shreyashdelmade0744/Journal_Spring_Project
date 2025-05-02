package sdd.justcode.journal.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sdd.justcode.journal.entity.JournalEntity;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.service.JournalEntryService;
import sdd.justcode.journal.service.UserEntryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
//controller ----> service -----> repository

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired//this is dependency injection
    private JournalEntryService JournalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/check")
    public String check(){
        return "journla working";
    }

    @GetMapping
    public ResponseEntity<?> getAllEntriesofUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userEntryService.findByUsername(username);
        List<JournalEntity> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all); // 200 OK with data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found"); // or empty list/message
        }
    }//working

    @PostMapping("/addjournal")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity je){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            je.setData(LocalDateTime.now());
            JournalEntryService.saveJournalEntry(je,username);
            return new ResponseEntity<>(je, HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println("Controller error: " + e.getMessage()); // for debug
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }//working

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntity> findbyId(@PathVariable ObjectId myid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity user = userEntryService.findByUsername(username);
        List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntity> journalEntry = JournalEntryService.findById(myid);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }//working

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deletebyId(@PathVariable ObjectId myid ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = JournalEntryService.deleteById(myid, username);
        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }//working


    @PutMapping("/id/{myid}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myid,@RequestBody JournalEntity newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //this two lines to ensure shyam cannot delete or update entry of ram ,even if shyam knows id
        UserEntity user = userEntryService.findByUsername(username);
        List<JournalEntity> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).toList();

        if(!collect.isEmpty()){
            Optional<JournalEntity> journalEntry = JournalEntryService.findById(myid);

            if(journalEntry.isPresent()){
                JournalEntity oldEntry = journalEntry.get();
                    oldEntry.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
                    oldEntry.setContent((newEntry.getContent()!=null && !newEntry.getContent().isEmpty()) ? newEntry.getContent() : oldEntry.getContent());
                    JournalEntryService.saveJournalEntry(oldEntry);
                    return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }//working

    }

package sdd.justcode.journal.controller;

import org.springframework.web.bind.annotation.*;
import sdd.justcode.journal.entity.JournalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntry {

    private Map<Long, JournalEntity> journalentries = new HashMap<>();
    @GetMapping("/getall")
    public List<JournalEntity> getall(){
        return new ArrayList<>(journalentries.values());
    }

    @PostMapping("/adduser")
    public boolean createEntry(@RequestBody JournalEntity je){
        return true;
    }

    @GetMapping("/id/{myid}")
    public JournalEntity getbyId(@PathVariable Long myid){
        return journalentries.get(myid);
    }

    @DeleteMapping("/id/{myid}")
    public JournalEntity deletebyId(@PathVariable Long myid){
        return journalentries.remove(myid);
    }

    @PutMapping("/id/{myid}")
    public JournalEntity updateJournalById(@PathVariable Long myid,@RequestBody JournalEntity je){

        return journalentries.put(myid,je);

    }

}

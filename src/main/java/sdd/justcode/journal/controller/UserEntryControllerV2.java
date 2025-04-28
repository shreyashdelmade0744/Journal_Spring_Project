package sdd.justcode.journal.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdd.justcode.journal.entity.JournalEntity;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.service.JournalEntryService;
import sdd.justcode.journal.service.UserEntryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//controller ----> service -----> repository

@RestController
@RequestMapping("/user")
public class UserEntryControllerV2 {

    @Autowired
    private UserEntryService userEntryService ;

    @GetMapping
    public List<UserEntity> getall(){
        return userEntryService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody UserEntity user){
        userEntryService.saveUserEntry(user);
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> findByUsername(@RequestBody UserEntity user,@PathVariable String username ){
        UserEntity userInDB = userEntryService.findByUsername(username);
        if(userInDB!=null){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userEntryService.saveUserEntry(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

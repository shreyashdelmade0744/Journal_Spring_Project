package sdd.justcode.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.repository.UserEntryRepo;
import sdd.justcode.journal.service.UserEntryService;
//controller ----> service -----> repository

@RestController
@RequestMapping("/user")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService ;

    @Autowired
    private UserEntryRepo userEntryRepo;

    //just for testing purpose
    @GetMapping("/test")
    public String test(){
        return "user endpoint is accessible";
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserEntity userInDB = userEntryService.findByUsername(username);
        if(userInDB!=null){
            userInDB.setUsername(user.getUsername());
            userInDB.setPassword(user.getPassword());
            userEntryService.saveNewUser(userInDB);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        userEntryRepo.deleteByUsername(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }







}

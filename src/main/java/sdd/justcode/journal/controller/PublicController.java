package sdd.justcode.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.service.UserEntryService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/healthcheck")
    public String healthcheck() {
        return "health check is working fine";
    }


    @PostMapping("/create-user")
    public void createUser(@RequestBody UserEntity user){
        userEntryService.saveNewUser(user);
    }

}

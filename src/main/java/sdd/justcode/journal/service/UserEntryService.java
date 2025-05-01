package sdd.justcode.journal.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sdd.justcode.journal.entity.JournalEntity;
import sdd.justcode.journal.entity.UserEntity;
import sdd.justcode.journal.repository.UserEntryRepo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepo userEntryRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUserEntry(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Arrays.asList("USER"));
        userEntryRepo.save(userEntity);
    }

//    public void saveNewUser(UserEntity userEntity){
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//        userEntity.setRoles(Arrays.asList("USER"));
//        userEntryRepo.save(userEntity);
//    }



    public List<UserEntity> getAll(){
        return userEntryRepo.findAll();
    }

    public UserEntity findByUsername(String username){
        return userEntryRepo.findByUsername(username);
    }

    public void deleteById(ObjectId id){
        userEntryRepo.deleteById(id);
    }


}

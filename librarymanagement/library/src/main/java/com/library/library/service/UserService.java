package com.library.library.service;

import com.library.library.entity.User;
import com.library.library.model.Login;
import com.library.library.model.LoginResult;
import com.library.library.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    // saving the user
    public ResponseEntity<String> saveUser(User user){

        Optional <User> existingUser = userRepository.findUserByEmail(user.getEmail());
        if(existingUser.isPresent()){
            return ResponseEntity.badRequest().body( user.getEmail() + " email already exists");
        }
        else {
            String plainPassword = user.getPassword();
            String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("user saved successfully. ");}
    }
    public  ResponseEntity<LoginResult> loginUser(Login login){

        // finding the user is present or not
        Optional <User> existingUser = userRepository.findUserByEmail(login.getEmail());
        LoginResult loginResult = new LoginResult();
        if(existingUser.isPresent()){
            if(BCrypt.checkpw(login.getPassword(), existingUser.get().getPassword())){
                existingUser.get().setPassword(null);
                loginResult.setUser(existingUser.get());
                return ResponseEntity.ok(loginResult);
            } else { loginResult.setErrorMessage("password did not matched");}
        } else {
            loginResult.setErrorMessage("user does not exist.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResult);

    }

}

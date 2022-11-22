package com.example.mezzo_rec.Services;


import com.example.mezzo_rec.Entites.User;
import com.example.mezzo_rec.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64enocder = Base64.getUrlEncoder();
    private Object UserRipository;


    public User register(User user) {

        if(checkUserExist(user)== true)
            return null;

        user.setToken(generateToken());

        return userRepository.save(user);

    }

    private String generateToken() {

        byte[] token = new byte[24];
        secureRandom.nextBytes(token);
        return base64enocder.encodeToString(token);

    }

    private boolean checkUserExist(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);

        if(existingUser == null)
            return false;
        return true;
    }

    public User login(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if(existingUser.getUsername().equals(user.getUsername()) &&
                existingUser.getPassword().equals(user.getPassword()) &&
                existingUser.getRole().equals(user.getRole())) {
            existingUser.setPassword("");
            return existingUser;
        }

        return null;

    }

    public List<User> getAllAdmins() {
        return userRepository.findAll();
    }


    public User updateUser(User user) {
        Optional<User> u = userRepository.findById((Long) user.getId());

        if(u.isPresent()) {
            return userRepository.save(user);}
        else {
            return null ;
        }
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


    public User findUserById(Long id) {


            Optional<User> u = userRepository.findById(id);
            if (u.isPresent()) {
                return u.get();
            } else {
                return null;
            }

        }
    }



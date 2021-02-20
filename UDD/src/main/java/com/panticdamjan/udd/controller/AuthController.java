package com.panticdamjan.udd.controller;

import com.panticdamjan.udd.dto.UserDTO;
import com.panticdamjan.udd.model.Writer;
import com.panticdamjan.udd.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class AuthController {

    @Autowired
    WriterRepository writerRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO request){
        Writer user = writerRepository.findById(request.getUsername()).orElse(null);

        if (user == null){
            return new ResponseEntity<>("Bad credentials", HttpStatus.BAD_REQUEST);
        }

        if (!request.getPassword().equals(user.getPassword())){
            return new ResponseEntity<>("Bad credentials", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

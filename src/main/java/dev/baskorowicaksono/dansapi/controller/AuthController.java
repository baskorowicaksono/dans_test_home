package dev.baskorowicaksono.dansapi.controller;

import dev.baskorowicaksono.dansapi.entity.Role;
import dev.baskorowicaksono.dansapi.entity.User;
import dev.baskorowicaksono.dansapi.payload.LoginDto;
import dev.baskorowicaksono.dansapi.payload.RoleDto;
import dev.baskorowicaksono.dansapi.payload.SignUpDto;
import dev.baskorowicaksono.dansapi.repository.RoleRepository;
import dev.baskorowicaksono.dansapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        if (!authentication.isAuthenticated()){
            return new ResponseEntity<>("Failed to login with the given credentials.", HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User successfully signed-in!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto){

//        Validation on username
//        Case 1: Duplicate username
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
        }

//        Case 2: Username not defined
        if(signUpDto.getUsername() == null){
            return new ResponseEntity<>("Username not defined", HttpStatus.BAD_REQUEST);
        }

//        Validation on email
//        Case 1: Duplicate email
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);
        }

//        Case 2: Email not defined
        if(signUpDto.getEmail() == null){
            return new ResponseEntity<>("Email not defined", HttpStatus.BAD_REQUEST);
        }

//        Validation on name
        if(signUpDto.getName() == null){
            return new ResponseEntity<>("Name not defined", HttpStatus.BAD_REQUEST);
        }

//        Validation on role
        if(signUpDto.getRole() == null){
            return new ResponseEntity<>("Role not defined", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setHashed_password(passwordEncoder.encode(signUpDto.getPassword()));

//        Wrong role is being defined in the request
        Boolean emptyRole = roleRepository.findByName(signUpDto.getRole().toUpperCase()).isEmpty();
        if(emptyRole){
            return new ResponseEntity<>("There is no such role.", HttpStatus.BAD_REQUEST);
        }

        Role roles = roleRepository.findByName(signUpDto.getRole().toUpperCase()).get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/role")
    public ResponseEntity<String> addRole(@RequestBody RoleDto roleDto){

//        Validation on name
//        Case 1: Duplicate name
        if(roleRepository.existsByName(roleDto.getName())){
            return new ResponseEntity<>("Rolename already defined.", HttpStatus.BAD_REQUEST);
        }

//        Case 2: Name not defined
        if(roleDto.getName() == null){
            return new ResponseEntity<>("Rolename not defined", HttpStatus.BAD_REQUEST);
        }

        Role role = new Role();
        role.setName(roleDto.getName().toUpperCase());

        roleRepository.save(role);

        return new ResponseEntity<>("Role registered successfully", HttpStatus.OK);
    }
}

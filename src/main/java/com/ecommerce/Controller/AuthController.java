package com.ecommerce.Controller;

import com.ecommerce.Entity.User;
import com.ecommerce.Repository.UserRepository;
import com.ecommerce.Request.LoginRequest;
import com.ecommerce.Service.Impl.CustomUserDetailsService;
import com.ecommerce.Service.Impl.JwtService;
import com.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
    return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword())
                );

        if(authenticate.isAuthenticated()){
            String jwt = jwtService.generateToken(customUserDetailsService.loadUserByUsername(loginRequest.getEmail()));
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Unauthorized user",HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentlyLoggedInUser(Authentication authentication){
        if(authentication == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        String email = authentication.getName();
        User user =userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Invalid email, user not found"));
        return ResponseEntity.ok(user);

    }

}

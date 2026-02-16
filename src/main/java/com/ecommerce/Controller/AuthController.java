package com.ecommerce.Controller;

import com.ecommerce.Entity.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

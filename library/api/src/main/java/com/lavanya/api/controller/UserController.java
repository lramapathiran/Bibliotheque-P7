package com.lavanya.api.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lavanya.api.configs.JwtTokenProvider;
import com.lavanya.api.model.AuthBody;
import com.lavanya.api.repository.UserRepository;
import com.lavanya.api.service.UserService;


/**
 * Rest Controller to control all the requests related to User object.
 * @author lavanya
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;
    
    /**
     * POST requests for /saveUser endpoint.
     * This controller-method updates an user and encode its password in database.
     * 
     */	
    @PostMapping("/saveUser")
    public void saveUser() {
    	userService.updateUser();
    }
	
    /**
     * POST requests for /login endpoint.
     * This controller-method send data required for user authentication.
     * 
     * @param data is the bean where the password and username of the user are stored to authenticate the user.
     * @return token generated durig the login step.
     */	
    @PostMapping("/login")
    public String login(@RequestBody AuthBody data) {
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String role = this.users.findByUsername(username).getRoles();
            String fullName = this.users.findByUsername(username).getFirstName() + " " + this.users.findByUsername(username).getLastName();
            String token = jwtTokenProvider.createToken(username, fullName, role);
 
            return token;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("L'identifiant et/ou le mot de passe sont invalides!");
        }
    }
	
}

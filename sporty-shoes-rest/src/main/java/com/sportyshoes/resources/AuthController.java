package com.sportyshoes.resources;

import com.sportyshoes.dto.LoginUserDTO;
import com.sportyshoes.dto.RegisterUserDTO;
import com.sportyshoes.model.LoginResponse;
import com.sportyshoes.model.User;
import com.sportyshoes.service.AuthenticationService;
import com.sportyshoes.service.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
	private final JwtService jwtService;
	private final AuthenticationService authenticationService;

	public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
	    this.jwtService = jwtService;
	    this.authenticationService = authenticationService;
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
	    User registeredUser = authenticationService.signup(registerUserDto);
	
	    return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
	    User authenticatedUser = authenticationService.authenticate(loginUserDto);
	
	    String jwtToken = jwtService.generateToken(authenticatedUser);
	
	    LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());
	
	    return ResponseEntity.ok(loginResponse);
	}
}

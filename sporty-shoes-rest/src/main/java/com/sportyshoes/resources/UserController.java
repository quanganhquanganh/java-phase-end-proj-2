package com.sportyshoes.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sportyshoes.dto.PasswordChangeDTO;
import com.sportyshoes.model.User;
import com.sportyshoes.service.AuthenticationService;

@Controller
@RequestMapping("/api/user")
public class UserController {
	private final AuthenticationService authenticationService;
	
	public UserController(AuthenticationService authenticationService) {
	    this.authenticationService = authenticationService;
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        User user = (User) authentication.getPrincipal();
			authenticationService.changePassword(user.getUsername(), passwordChangeDto);

			return ResponseEntity.ok(user);
	}
}

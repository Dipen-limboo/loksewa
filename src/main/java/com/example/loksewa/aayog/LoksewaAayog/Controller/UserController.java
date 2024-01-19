package com.example.loksewa.aayog.LoksewaAayog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.ChangeRoleDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.LoginRequest;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.SignupRequest;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.UserDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.VerifiedTokenDto;
import com.example.loksewa.aayog.LoksewaAayog.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;
		
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return userService.signIn(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.signup(signUpRequest);
	}
	
	@PostMapping("/verifiedToken")
	public ResponseEntity<?> verifyingSignUpRequest(@RequestBody VerifiedTokenDto verifiedDto){
		return userService.verified(verifiedDto);
	}
	
	@GetMapping("/list-users")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listOfUsers(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(name = "sort", required = false, defaultValue = "id") String id,
		@RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
	) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
		Pageable pageable = PageRequest.of(page -1, size, sort);
		return userService.userlist(pageable);
	}
	
	@GetMapping("/userById/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getUserById(@PathVariable Long id){
		return userService.getUser(id);
	}
	
	@PutMapping("/userById/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<?> updateUserDetails(@PathVariable Long id,@Valid @RequestBody UserDto userDto, Authentication auth){
		return userService.updateUser(id, userDto, auth);
	}
	
	@DeleteMapping("/deleteUserByid/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		return userService.delete(id);
	}

	@PutMapping("/changingRole/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> changingUserRoleById(@PathVariable Long id, @RequestBody ChangeRoleDto changeRoleDto) {
	   return userService.changeRole(id, changeRoleDto);
	}
}

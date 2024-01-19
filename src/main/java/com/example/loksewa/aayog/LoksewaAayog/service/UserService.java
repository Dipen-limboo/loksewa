package com.example.loksewa.aayog.LoksewaAayog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.loksewa.aayog.LoksewaAayog.Entity.ERole;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Role;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Status;
import com.example.loksewa.aayog.LoksewaAayog.Entity.User;
import com.example.loksewa.aayog.LoksewaAayog.Repository.RoleRepository;
import com.example.loksewa.aayog.LoksewaAayog.Repository.UserRepository;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.ChangeRoleDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.LoginRequest;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.SignupRequest;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.UserDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.VerifiedTokenDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.JwtResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.RoleResponseDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.UserResponseDto;
import com.example.loksewa.aayog.LoksewaAayog.security.jwt.JwtUtils;
import com.example.loksewa.aayog.LoksewaAayog.security.service.UserDetailsImpl;

import jakarta.validation.Valid;

@Service
public class UserService {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	JavaMailSender mailSender;

	public ResponseEntity<?> signIn(@Valid LoginRequest loginRequest) {
		Authentication authentication = authenticationManager
			       .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
					if(!userDetails.isVerified()) {
						return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("ERROR: Verified your email to singup first"));
					}
					if (userDetails.getStatus().equals(Status.DEACTIVE)) {
					    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error: You are deactivated by admin"));
					}
					String jwt = jwtUtils.generateJwtToken(authentication);
					List<String> roles = userDetails.getAuthorities().stream()
							.map(item -> item.getAuthority())
							.collect(Collectors.toList());
					return ResponseEntity.ok(new JwtResponse(jwt, 
							userDetails.getId(),
							userDetails.getFirstname(),
							userDetails.getMiddlename(),
							userDetails.getLastname(),
							userDetails.getDateOfbirth(),
							userDetails.getPhone(),
							userDetails.getUsername(),
							userDetails.getEmail(),
							roles)
							);
	}

	
	
	
	public ResponseEntity<?> signup(@Valid SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User();
		user.setFirstName(signUpRequest.getFirstName());
		user.setMiddleName(signUpRequest.getMiddleName());
		user.setLastName(signUpRequest.getLastName());
		user.setUsername(signUpRequest.getUsername());
		user.setBirthDate(signUpRequest.getDateOfBirth());
		user.setPhone(signUpRequest.getPhone());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setVerifiedDate(new Date());
		//verifying the singup by generating token 
		String token = UUID.randomUUID().toString();
		
		String email = signUpRequest.getEmail();
		sendTokenToEmail(token, email);
		user.setVerifiedToken(token);
		
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Check you mail and verified to register!"));
	}
	
	
	
	
	private void sendTokenToEmail(String token, String email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("dipenlimboo564@gmail.com");
		mailMessage.setTo(email);
		String subject = "User Verification Request";
		String text = "To verified your register request, click the following link: "
                + "http://localhost:8081/api/user/verifiedToken?token=" + token;
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		mailSender.send(mailMessage);
	}




	public ResponseEntity<?> verified(VerifiedTokenDto verifiedDto) {
		Optional<User> optionalUser = userRepository.findByVerifiedToken(verifiedDto.getToken());
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setVerifiedToken(null);
			user.setVerified(true);
			userRepository.save(user);
			return ResponseEntity.ok().body(new MessageResponse(user.getEmail() + " is verified!!"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Invalid token"));
		}
	}




	public ResponseEntity<?> userlist(Pageable pageable) {
		Page<User> listOfUsers=  userRepository.findAll(pageable); 
		
		List<UserResponseDto> userResponseDtoList = new ArrayList<>();
		for(User user: listOfUsers) {
			UserResponseDto userResponseDto = new UserResponseDto();
			userResponseDto.setId(user.getId());
			userResponseDto.setFirstName(user.getFirstName());
			userResponseDto.setMiddleName(user.getMiddleName());
			userResponseDto.setLastName(user.getLastName());
			userResponseDto.setDateOfBirth(user.getBirthDate());
			userResponseDto.setUsername(user.getUsername());
			userResponseDto.setPhone(user.getPhone());
			userResponseDto.setEmail(user.getEmail());
			
			Set<RoleResponseDto> roleDto = new HashSet<>();
			
			for(Role role: user.getRoles()) {
				RoleResponseDto roleResponseDto = new RoleResponseDto();
				roleResponseDto.setId(role.getId());
				ERole erole = role.getName();
				String userRole = erole.name();
				roleResponseDto.setName(userRole);
				roleDto.add(roleResponseDto);
			}
			userResponseDto.setRole(roleDto);
			userResponseDtoList.add(userResponseDto);
		}
		return ResponseEntity.ok().body(userResponseDtoList);
	}




	public ResponseEntity<?> getUser(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			UserResponseDto userResponseDto = new UserResponseDto();
			userResponseDto.setId(id);
			userResponseDto.setFirstName(user.getFirstName());
			userResponseDto.setMiddleName(user.getMiddleName());
			userResponseDto.setLastName(user.getLastName());
			userResponseDto.setDateOfBirth(user.getBirthDate());
			userResponseDto.setUsername(user.getUsername());
			userResponseDto.setPhone(user.getPhone());
			userResponseDto.setEmail(user.getEmail());
			
			Set<RoleResponseDto> setRoledto = new HashSet<>();
			for(Role role: user.getRoles()) {
				RoleResponseDto roledto = new RoleResponseDto();
				roledto.setId(role.getId());
				
				ERole erole = role.getName();
				String userRole = erole.name();
				roledto.setName(userRole);
				setRoledto.add(roledto);
				
			}
			userResponseDto.setRole(setRoledto);
			return ResponseEntity.ok().body(userResponseDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id "+ id));
		}
	}




	public ResponseEntity<?> updateUser(Long id, @Valid UserDto userDto, Authentication auth) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
			if(userDetailsImpl.getId().equals(user.getId())) {
				user.setFirstName(userDto.getFirstName());
				user.setMiddleName(userDto.getMiddleName());
				user.setLastName(userDto.getLastName());
				user.setBirthDate(userDto.getDateOfBirth());
				user.setUsername(userDto.getUsername());
				user.setPhone(userDto.getPhone());
				user.setUsername(userDto.getUsername());
				user.setPassword(encoder.encode(userDto.getPassword()));
				userRepository.save(user);
				return ResponseEntity.ok().body(userDto);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Error: You are not authorized to change this user Details"));
			}
		} else {
			return ResponseEntity.notFound().build();	
			}
	}




	public ResponseEntity<?> changeRole(Long id, ChangeRoleDto changeRoleDto) {
		 Optional<User> optionalUser = userRepository.findById(id);

		    if (optionalUser.isPresent()) {
		        User user = optionalUser.get();
		        Set<String> roleSet = changeRoleDto.getRole();
		        if (roleSet == null) {
		            roleSet = new HashSet<>(Collections.singletonList("user"));
		        }
		        Set<Role> roles = roleSet.stream()
		                .map(roleName -> {
		                    switch (roleName) {
		                        case "admin":
		                            return roleRepository.findByName(ERole.ROLE_ADMIN)
		                                    .orElseThrow(() -> new RuntimeException("Error: Role 'ADMIN' not found."));
		                        case "mod":
		                            return roleRepository.findByName(ERole.ROLE_MODERATOR)
		                                    .orElseThrow(() -> new RuntimeException("Error: Role 'MODERATOR' not found."));
		                        default:
		                            return roleRepository.findByName(ERole.ROLE_USER)
		                                    .orElseThrow(() -> new RuntimeException("Error: Role 'USER' not found."));
		                    }
		                })
		                .collect(Collectors.toSet());

		        user.setRoles(roles);
		        
		        Set<String> setStatus = changeRoleDto.getStatus();
		        if(setStatus == null) {
		        	user.setStatus(Status.ACTIVE);
		        } else {
		        	setStatus.forEach(status -> {
		        		switch(status) {
		        		case "deactivated":
		        			user.setStatus(Status.DEACTIVE);
		        			break;
		        		default: 
		        			user.setStatus(Status.ACTIVE);
		        		}
		        	});
		        }
		        userRepository.save(user);

		        return ResponseEntity.ok().body(changeRoleDto);
		    } else {
		        return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found!! "));
		    }
	}




	public ResponseEntity<?> delete(Long id) {
		userRepository.deleteById(id);
		return ResponseEntity.ok().body(new MessageResponse("User id " +id + " deleted successfully!!"));
	}

}

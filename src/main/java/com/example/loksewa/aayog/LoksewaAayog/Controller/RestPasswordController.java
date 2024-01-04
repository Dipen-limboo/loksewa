package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.ResetPassword;
import com.example.loksewa.aayog.LoksewaAayog.Entity.User;
import com.example.loksewa.aayog.LoksewaAayog.Repository.ResetpasswordRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.UserRepository;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.NewPasswordDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.RestPasswordDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/forgot-password")
public class RestPasswordController {
	
	@Autowired
	UserRepository userRepo;


	@Autowired
	ResetpasswordRepo resetRepo; 
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JavaMailSender mailSender;
	
	
	private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;
	
	@PostMapping("/generate-token")
	@Transactional
	public ResponseEntity<?> generateTokenByMail(@Valid @RequestBody RestPasswordDto resetPasswordDto){
		String email = resetPasswordDto.getEmail();
		try {
			Optional<User> optionalUser = userRepo.findByEmail(email);
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				String token = generateToken(user);
				sendPasswordResetEmail(user, token);
				return ResponseEntity.ok().body(new MessageResponse("Reset token generated and sent successfully!! Check the email"));
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by email address: "+email));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body(new MessageResponse("failed to genereated token"));

	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody NewPasswordDto passwordDto){
		String token = passwordDto.getToken();
		String newPassword = passwordDto.getPassword();
		
		ResetPassword resetPassword = resetRepo.findByToken(token);
		if(resetPassword == null || resetPassword.isExpired()) {
			return ResponseEntity.ok().body(new MessageResponse("Error: Either token is not valid or the token is expired"));
		}
		User user = resetPassword.getUser();
		updatePassword(user, newPassword);
		expireToken(resetPassword);
		return ResponseEntity.ok().body(new MessageResponse("Reset Password succesfully!!"));
	}
	

	public String generateToken(User user) {
		String token = UUID.randomUUID().toString();
		Date expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS);
		
		ResetPassword passwordReset = new ResetPassword();
		passwordReset.setToken(token);
		passwordReset.setExpireDate(expiryDate);
		passwordReset.setUser(user);
		
		resetRepo.save(passwordReset); 
		return token;
	}
	
	public void sendPasswordResetEmail(User user, String token) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("dipenlimboo564@gmail.com");
		mailMessage.setTo(user.getEmail());
		String subject = "Password Reset Request";
		String text = "To reset your password, click the following link: "
                + "http://localhost:8081/api/forgot-password/reset-password?token=" + token;
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		mailSender.send(mailMessage);
	}

	public void updatePassword(User user, String newPassword) {
		String hashedPassword = encoder.encode(newPassword);
		user.setPassword(hashedPassword);
		userRepo.save(user);
	}
	
	public void expireToken(ResetPassword resetPassword) {
		resetPassword.setExpireDate(new Date());
		resetRepo.save(resetPassword);
	}

}

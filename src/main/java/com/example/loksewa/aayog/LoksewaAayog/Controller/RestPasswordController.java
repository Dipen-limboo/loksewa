package com.example.loksewa.aayog.LoksewaAayog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.NewPasswordDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.RestPasswordDto;
import com.example.loksewa.aayog.LoksewaAayog.service.ResetPasswordService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/forgot-password")
public class RestPasswordController {
	
	@Autowired
	ResetPasswordService passwordService;
	
	private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;
	
	@PostMapping("/generate-token")
	@Transactional
	public ResponseEntity<?> generateTokenByMail(@Valid @RequestBody RestPasswordDto resetPasswordDto){
		return passwordService.generateToken(resetPasswordDto);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody NewPasswordDto passwordDto){
		return passwordService.reset(passwordDto);
	}
}

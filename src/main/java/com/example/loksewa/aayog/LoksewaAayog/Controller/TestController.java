package com.example.loksewa.aayog.LoksewaAayog.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public ResponseEntity<?> userAccess() {
    return ResponseEntity.ok(new MessageResponse("User access successfullly!! "));
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public ResponseEntity<?> moderatorAccess() {
    return ResponseEntity.ok(new MessageResponse("Moderator access successfully!!"));
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> adminAccess() {
    return ResponseEntity.ok(new MessageResponse("Admin access successfully!!")) ;
  }
}

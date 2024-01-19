package com.example.loksewa.aayog.LoksewaAayog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.EditQuestionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionDTO;
import com.example.loksewa.aayog.LoksewaAayog.service.QuestionService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	
	@PostMapping("/addingQuestion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionDTO questionDto){
		return questionService.saveQuestion(questionDto);
	}
	
	@GetMapping("/listOfQuestions")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> viewListOfQuestions(
		    @RequestParam(defaultValue = "1") int page,
		    @RequestParam(defaultValue = "10") int size,
		    @RequestParam(name = "sort", required = false, defaultValue = "id") String id,
		    @RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
			) {
	    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
	    Pageable pageable = PageRequest.of(page -1, size, sort);
	
		return questionService.viewListQuestion(pageable);
	}
	
	@GetMapping("/editQuestions/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> editQuestion(@PathVariable Long id){
		return questionService.editingQuestion(id);
	}
	
	@PutMapping("/editQuestions/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody EditQuestionDto editQuestionDto){
		return questionService.updateQuestions(id, editQuestionDto);
	}
	
	
	@DeleteMapping("/deleteQuestion/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delteQuestionById(@PathVariable Long id){
		return questionService.deleteQuesiton(id);
	}

}
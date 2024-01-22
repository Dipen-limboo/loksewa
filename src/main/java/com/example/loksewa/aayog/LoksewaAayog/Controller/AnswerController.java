package com.example.loksewa.aayog.LoksewaAayog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.AnswerSetDto;
import com.example.loksewa.aayog.LoksewaAayog.service.AnswerService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

	@Autowired
	AnswerService ansService;
	
	@GetMapping("/getQuestionSet/{category_id}/{position_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> displayingQuestionSet(@PathVariable Long category_id, @PathVariable Long position_id ){
		return ansService.geQuestionSet(category_id, position_id);
	}

	@PostMapping("/answerQuestionSet/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@Transactional
	public ResponseEntity<?> answeringQuestionSet(@PathVariable Long id, @RequestBody AnswerSetDto answerSetDto, Authentication auth){
		return ansService.answeringQuestion(id, answerSetDto, auth);
	}
	
	@GetMapping("/getanswerList")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listofAnswers(@RequestParam(required=false) Long categoryId, 
			@RequestParam(required=false) Long positionId,
			@RequestParam(required=false) Integer year,
			@RequestParam(required = false) Long userId){
		return ansService.answersList(categoryId, positionId, year, userId);
	}
	
	@GetMapping("/getanswer/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> listofAnswerByUser(@PathVariable Long id){
		return ansService.answerListOfUser(id);
	}
	
	@GetMapping("/getanswer/exam={id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAnswersByExamsId(@PathVariable Long id){
		return ansService.getAnswersByExams(id);
	}
	
}
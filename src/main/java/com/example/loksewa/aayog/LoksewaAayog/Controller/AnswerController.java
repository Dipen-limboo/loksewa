package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Option;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;
import com.example.loksewa.aayog.LoksewaAayog.Entity.ScoreBoard;
import com.example.loksewa.aayog.LoksewaAayog.Entity.User;
import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;
import com.example.loksewa.aayog.LoksewaAayog.Repository.CategoryRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.OptionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.PositionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionsetRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.ScoreBoardRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.UserRepository;
import com.example.loksewa.aayog.LoksewaAayog.Repository.UserScoreRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.AnswerDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.AnswerSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.GetListOfquestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ListOfAnswerAttemptDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.QuestionResponseDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ScoreResponseDto;
import com.example.loksewa.aayog.LoksewaAayog.security.service.UserDetailsImpl;
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
	public ResponseEntity<?> listofAnswers(){
		return ansService.answersList();
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
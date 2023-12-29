package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Entity.UserScore;
import com.example.loksewa.aayog.LoksewaAayog.Repository.PositionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.ScoreRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.Answer;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.Score;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	private QuestionRepo questionRepo;
	
	@Autowired
	private PositionRepo positionRepo;
	
	@Autowired
	private ScoreRepo scoreRepo;
	
	@PostMapping("/addingQuestion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionResponse question){
		Question q = new Question();
		q.setQuestionText(question.getQuestionText());
		q.setOptionA(question.getOptionA());
		q.setOptionB(question.getOptionB());
		q.setOptionC(question.getOptionC());
		q.setOptionD(question.getOptionD());
		q.setAnswer(question.getAnswer());
		q.setYear(question.getYear());
		Set<String> strPositions = question.getPosition();
		Set<Position> positions = new HashSet<>();
		
		if(strPositions != null) {
			strPositions.forEach(position -> {
				switch (position) {
				case "Chief Secretary":
					Position cheif = positionRepo.findByName("Chief Secretary").orElseThrow(() -> new RuntimeException("Error: position is not found"));
					positions.add(cheif);
				break;
				case "Secretary":
					Position sec = positionRepo.findByName("Secretary").orElseThrow(() -> new RuntimeException("Error: position is not found"));
					positions.add(sec);
				break;
				case "Joint Secretary":
					Position joint = positionRepo.findByName("Joint Secretary").orElseThrow(() -> new RuntimeException("Error: position is not found"));
					positions.add(joint);
				break;
				case "Deputy Secretary":
					Position deputy = positionRepo.findByName("Deputy Secretary").orElseThrow(() -> new RuntimeException("Error: position is not found"));
					positions.add(deputy);
				break;
				case "Section Officer":
					Position sect = positionRepo.findByName("Section Officer").orElseThrow(() -> new RuntimeException("Error: position is not found"));
					positions.add(sect);
				break;
				case "Nayab Subba":
					Position subba = positionRepo.findByName("Nayab Subba").orElseThrow(() -> new RuntimeException("Error: position is not found"));
					positions.add(subba);
				break;
				default:
					Position kharidar = positionRepo.findByName("Kharidar").orElseThrow(() -> new RuntimeException("Error: Position not found"));
					positions.add(kharidar);
				}
			});
		} else {
			Position kharidar = positionRepo.findByName("Kharidar").orElseThrow(() -> new RuntimeException("Error: Position not found"));
			positions.add(kharidar);
		}
		q.setPosition(positions);
		questionRepo.save(q);
		return ResponseEntity.status(HttpStatus.CREATED).body(question);
	}
	
	@PostMapping("/answer")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> evaluate(@RequestBody List<Answer> answers) {
		UserScore userScore = new UserScore(); 
		int rights = 0;
		for (Answer answer : answers) 
			rights +=  questionRepo.countByIdAndAnswer(answer.getId(), answer.getOption());
		userScore.setRight(rights);
		userScore.setTotal(answers.size());
		scoreRepo.save(userScore);
		return ResponseEntity.status(HttpStatus.CREATED).body(userScore);
	}
	 
}

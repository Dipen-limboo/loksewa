package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;
import com.example.loksewa.aayog.LoksewaAayog.Repository.PositionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionsetRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionIdDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionSetDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questionset")
public class QuestionSetController {
	
	@Autowired
	private QuestionsetRepo questionsetRepo;
	
	@Autowired 
	private QuestionRepo questionRepo; 
	
	@Autowired
	private PositionRepo positionRepo;
	
	@PostMapping("/addingQuestionSet")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> creatingQuestionSet(@Valid @RequestBody QuestionSetDto questionSetDto) {
	    QuestionSet questionSet = new QuestionSet();

	    int positionIds = questionSetDto.getPosition();
	    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + positionIds);
	    
	    Optional<Position> positionOptional = positionRepo.findById(positionIds);

	    if (positionOptional.isPresent()) {
	        Position positionId = positionOptional.get();
	        questionSet.setPosition(positionId);

	        questionSet.setYear(questionSetDto.getYear());

	        List<QuestionIdDto> questionIdDto = questionSetDto.getQuestionId();
	        List<Question> questions = new ArrayList<>();
	        for(QuestionIdDto questiondto: questionIdDto) {
	        	Long id = questiondto.getId();
	    		Optional<Question> optionalQuestion = questionRepo.findById(id);
	    		if(optionalQuestion.isPresent()) {
	    			Question question = optionalQuestion.get(); 
	    			question.setId(id);
	    			questions.add(question);
	    		}
	        }

	        questionSet.setQuestion(questions);
	        questionsetRepo.save(questionSet);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(questionSet);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Position with ID " + positionIds + " not found");
	    }
	}

}

package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Option;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;
import com.example.loksewa.aayog.LoksewaAayog.Repository.CategoryRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.PositionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionsetRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.ScoreBoardRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.UserScoreRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.DisplayOptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.DisplayQuestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	
	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired
	QuestionsetRepo questionSetRepo;
	
	@Autowired
	ScoreBoardRepo boardRepo;
	
	@Autowired
	PositionRepo positionRepo;
	
	@Autowired
	CategoryRepo cateRepo;
	
	@Autowired
	UserScoreRepo userScoreRepo; 
	
	
	@GetMapping("/getQuestionSet/{category_id}/{position_id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> displayingQuestionSet(@PathVariable Long category_id, @PathVariable Long position_id ){
		Optional<Category> optionalCategory = cateRepo.findById(category_id);
		if(optionalCategory.isPresent()) {
			Category cate = optionalCategory.get();
			Optional<Position> positionOptional = positionRepo.findById(position_id);
			if(positionOptional.isPresent()) {
				Position position = positionOptional.get();
				List<QuestionSet> questionSetList = questionSetRepo.findByCategoryAndPosition(cate, position);
				Random random = new Random();
				if(!questionSetList.isEmpty()) {
					int randomIndex= random.nextInt(questionSetList.size());
					QuestionSet randomQuestion = questionSetList.get(randomIndex);
					List<Question> questionList = randomQuestion.getQuestion();
					List<DisplayQuestionSetDto> listQuestionSetDto = new ArrayList<>(); 
					if(!questionList.isEmpty()) {
						for(Question question: questionList) {
							DisplayQuestionSetDto displayDto = new DisplayQuestionSetDto();
							displayDto.setId(question.getId());
							displayDto.setQuestion(question.getQuestionText());
							displayDto.setOptionType(question.getOptionT().getId());
							List<Option> optionList = question.getOptions();
							List<DisplayOptionDto> displayOptionDtoList = new ArrayList<>();
							for(Option option: optionList) {
								DisplayOptionDto displayOptionDto = new DisplayOptionDto();
								displayOptionDto.setId(option.getId());
								displayOptionDto.setText(option.getText());
								displayOptionDtoList.add(displayOptionDto);
							}
							displayDto.setOption(displayOptionDtoList);
							listQuestionSetDto.add(displayDto); 
						}
						return ResponseEntity.ok().body(listQuestionSetDto);
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Question List is empty"));
					}
					
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: Question Set List is empty"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Position not found by position id: "+ position_id));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Category not found by category id: "+ category_id));
		}
	}
	
	
//	@PostMapping("/answerQuestionSet/")
}

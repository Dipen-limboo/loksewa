package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Option;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
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
import com.example.loksewa.aayog.LoksewaAayog.payload.response.DisplayOptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.DisplayQuestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.QuestionSetDisplayDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ScoreResponseDto;
import com.example.loksewa.aayog.LoksewaAayog.security.service.UserDetailsImpl;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
	
	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired 
	OptionRepo optionRepo;
	
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
	
	@Autowired
	UserRepository userRepo;
	
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
					QuestionSetDisplayDto questionSetDisplayDto = new QuestionSetDisplayDto();
					questionSetDisplayDto.setId(randomQuestion.getId());
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
						questionSetDisplayDto.setQuesitons(listQuestionSetDto);
						return ResponseEntity.ok().body(questionSetDisplayDto);
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
	

	@PostMapping("/answerQuestionSet/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@Transactional
	public ResponseEntity<?> answeringQuestionSet(@PathVariable Long id, @RequestBody AnswerSetDto answerSetDto, Authentication auth){
		Optional<QuestionSet> optionalQuestionSet = questionSetRepo.findById(id);
		boolean check = true;
		ScoreResponseDto score = new ScoreResponseDto(); 
		if(optionalQuestionSet.isPresent()) {
			QuestionSet questionSet = optionalQuestionSet.get();
			List<AnswerDto> answerSetDtoList = answerSetDto.getAnswer();
			List<UserScore> userScoreList = new ArrayList<>();
			int rights = 0; 
			for(AnswerDto answer: answerSetDtoList) {
				UserScore userScore = new UserScore();
				
				UserDetailsImpl userDetailsImpl = (UserDetailsImpl) auth.getPrincipal();
				
				
				long questionId = answer.getQuestionId();
				Optional<Question> question = questionRepo.findById(questionId);
				if(question.isPresent()) {
					userScore.setQuestion(question.get());
					
					
					long optionId = answer.getOptionId();
					Optional<Option> option = optionRepo.findById(optionId);
					if(option.isPresent()) {
						userScore.setOption(option.get());
						
						Long userId = userDetailsImpl.getId();
						Optional<User> user = userRepo.findById(userId);
						if(user.isPresent()) {
							userScore.setUser(user.get());
							
						} else {
							return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found "));
						}
						rights += optionRepo.countByIdAndIsCorrect(optionId, check);
						
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: Option is not found by option id " + optionId));
					}
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: question is not found by question id " + questionId));
				}
				userScoreList.add(userScore);
			}
			
			ScoreBoard board = new ScoreBoard();
			board.setRight(rights);
			board.setTotal(answerSetDtoList.size());
			boardRepo.save(board);
			for (UserScore userScore : userScoreList) {
			    userScore.setBoard(board);
			}

			userScoreRepo.saveAll(userScoreList);
			score  = new ScoreResponseDto(answerSetDtoList.size(), rights);
		}
		
		return ResponseEntity.ok().body(score); 
	}
}

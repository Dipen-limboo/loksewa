package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
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
import com.example.loksewa.aayog.LoksewaAayog.payload.response.QuestionResponseDto;
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
	
	private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
	
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
		if(optionalQuestionSet.isPresent()) {
			QuestionSet questionSet = optionalQuestionSet.get();
			UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
			Long userId = userDetails.getId();
			Optional<User> userOptional = userRepo.findById(userId);
			int rights = 0;
			boolean check = true;
			if(userOptional.isPresent()) {
				User user = userOptional.get();
				UserScore userScore = new UserScore();
				userScore.setUser(user);
				userScore.setDate(new Date());
				userScoreRepo.save(userScore);
				List<Question> listQuestion = questionSet.getQuestion();
				List<ScoreBoard> scoreBoardList = new ArrayList<>();
				
				for(Question question: listQuestion) {
					ScoreBoard score = new ScoreBoard();
					score.setQuestion(question);
					List<AnswerDto> answerList = answerSetDto.getAnswer();
					for(AnswerDto answer: answerList) {
						if(question.getId().equals(answer.getQuestionId())) {
							Optional<Option> optionalOption = optionRepo.findByQuestionAndId(question, answer.getOptionId());
							if(optionalOption.isPresent()) {
								score.setOption(optionalOption.get());
								
								rights += optionRepo.countByIdAndIsCorrect(answer.getOptionId(), check);
							} else {
								score.setOption(null);
							}
						}
						score.setBoard(userScore);
					}
					scoreBoardList.add(score);
				}
				
				
				
				
//				List<AnswerDto> answerDtoList = answerSetDto.getAnswer();
//				List<ScoreBoard> scoreBoardList = new ArrayList<>();
//				
//				for(AnswerDto answerDto: answerDtoList ) {
//					ScoreBoard score = new ScoreBoard();
//					Long questionId = answerDto.getQuestionId();
//					Optional<Question> optionalQuestion = questionRepo.findById(questionId);
//					if(optionalQuestion.isPresent()) {
//						Question question = optionalQuestion.get();
//						score.setQuestion(question);
//						
//						Long optionId = answerDto.getOptionId();
//						Optional<Option> optionalOption = optionRepo.findById(optionId);
//						if(optionalOption.isPresent()) {
//							Option option = optionalOption.get();
//							score.setOption(option);
//							score.setBoard(userScore);
//							rights += optionRepo.countByIdAndIsCorrect(optionId, check);
//						} else {
//							return ResponseEntity.badRequest().body(new MessageResponse("Error: Option not found by option id: "+ optionId));
//						}
//					} else {
//						return ResponseEntity.badRequest().body(new MessageResponse("Error: Question not found by question id: "+ questionId));
//					}
//					scoreBoardList.add(score);
//				}
				boardRepo.saveAll(scoreBoardList);
				taskScheduler.initialize();
				
				
				ScoreResponseDto score = new ScoreResponseDto();
				List<QuestionResponseDto> responseDtoList = new ArrayList<>();
				for(Question question: listQuestion) {
					QuestionResponseDto questionResponse = new QuestionResponseDto();
					questionResponse.setQuestion(question.getQuestionText());
					List<AnswerDto> answerList = answerSetDto.getAnswer();
					for(AnswerDto answer: answerList) {
						if(question.getId().equals(answer.getQuestionId())) {
							Optional<Option> optionalOption = optionRepo.findByQuestionAndId(question, answer.getOptionId());
							if(optionalOption.isPresent()) {
								questionResponse.setOption(optionalOption.get().getText());
							} else {
								questionResponse.setOption(null);
							}
						}
						List<Option> optionsList= optionRepo.findByQuestion(question);
						boolean optionCheck = true;
						for (Option option: optionsList) {
							if(option.isCorrect() == optionCheck) {
								questionResponse.setCorrectOption(option.getText());
							}
						}
					}
					responseDtoList.add(questionResponse); 
				}
				score.setListResponseDto(responseDtoList);
				score.setRight(rights);
				score.setTotal(questionSet.getQuestion().size());
				return ResponseEntity.ok().body(score); 
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by userId: "+ userId));
			} 
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Question Set not found by set id : "+ id));
		}
		
//		taskScheduler.initialize();
//		return ResponseEntity.ok().body(score); 
	}
	
	
	@Scheduled(fixedDelay=300000)
	public void timeoutTask() {
		taskScheduler.shutdown();
	}
	
//	@GetMapping("/getanswer/{id}")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//	public ResponseEntity<?> listofAnswerByUser(@PathVariable Long id){
//		Optional<User> optionalUser = userRepo.findById(id);
//		if(optionalUser.isPresent()) {
//			User user = optionalUser.get();
//			List<UserScore> userScoreList = userScoreRepo.findByUser(user);
//			List<ListOfAnswerAttemptDto> listOfAnswerAttemptDto = new ArrayList<>();
//			
//			if(!userScoreList.isEmpty()) {
//				for(UserScore score: userScoreList ) {
//					ListOfAnswerAttemptDto attemptDto = new ListOfAnswerAttemptDto();
//					attemptDto.setId(score.getBoard().getId());
//					attemptDto.setDate(score.getDate());
//					
//				}
//			}
//			
//			return null;
//
//		} else {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not found by user id:" + id));
//		}
//	}
}

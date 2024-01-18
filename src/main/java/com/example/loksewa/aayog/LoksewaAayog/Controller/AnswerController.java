package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.DashBoardDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.AdminDashboardDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.DisplayOptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.DisplayQuestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.GetListOfquestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ListOfAnswerAttemptDto;
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
	private static final long EXPIRATION_TIME_MS = 1 * 60 * 1000;
	
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
				userScore.setQuestionSet(questionSet);
				Date newDate = new Date();
				userScore.setDate(newDate);
				Date expiryDate = new Date(newDate.getTime()+EXPIRATION_TIME_MS);
				userScore.setExpiry(expiryDate);
				userScoreRepo.save(userScore);
				List<Question> listQuestion = questionSet.getQuestion();
				String message = "Your answers";
				for(Question question: listQuestion) {
					ScoreBoard scoreboard = new ScoreBoard();
					scoreboard.setBoard(userScore);
					scoreboard.setQuestion(question);
					List<AnswerDto> answerList = answerSetDto.getAnswer();
					for(AnswerDto answer: answerList ) {
						if(question.getId().equals(answer.getQuestionId())) {
							Optional<Option> optionalOption = optionRepo.findByQuestionAndId(question, answer.getOptionId());
							if(optionalOption.isPresent() && (new Date().before(expiryDate))) {
								Optional<ScoreBoard> optionalScoreboard = boardRepo.findByTestAndQuestion(userScore, question);
								if(optionalScoreboard.isPresent()) {
									scoreboard.setOption(optionalOption.get()); 
								} else {
									scoreboard.setOption(optionalOption.get());
								}
								rights += optionRepo.countByIdAndIsCorrect(answer.getOptionId(), check);

							} else if(optionalOption.isPresent() && (new Date().after(expiryDate))){
								message="Your time is up";
								scoreboard.setOption(null);
							} else {
								scoreboard.setOption(null);
							}
						}
						boardRepo.save(scoreboard);
						}
				}
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
				score.setMessage(message);
				return ResponseEntity.ok().body(score); 
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: user not found by userId: "+ userId));
			} 
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Question Set not found by set id : "+ id));
		}
	}
	
	@GetMapping("/getanswerList")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listofAnswers(){
		List<UserScore> userscorelist = userScoreRepo.findAll();
		List<ListOfAnswerAttemptDto> listOfAnswerAttemptDto = new ArrayList<>();
		for (UserScore score: userscorelist) {
			ListOfAnswerAttemptDto attemptDto = new ListOfAnswerAttemptDto();
			attemptDto.setId(score.getId());
			attemptDto.setDate(score.getDate());
		
			List<ScoreBoard> scoreBoardList = boardRepo.findByTest(score);
			List<GetListOfquestionSetDto> list = new ArrayList<>();
				if(!scoreBoardList.isEmpty()) {
					for (ScoreBoard board: scoreBoardList) {
						GetListOfquestionSetDto answers = new GetListOfquestionSetDto();
						answers.setQuestion(board.getQuestion().getQuestionText());
						if(board.getOption() == null) {
							answers.setOption(null);
						} else {
							answers.setOption(board.getOption().getText());
						}
						List<Option> optionsList= optionRepo.findByQuestion(board.getQuestion());
						boolean optionCheck = true;
						for (Option option: optionsList) {
							if(option.isCorrect() == optionCheck) {
								answers.setCorrectOption(option.getText());
							}
						}
						list.add(answers);
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: exam is not found by id:"+ score.getId()));
		}
		attemptDto.setListOfAnswer(list);
		listOfAnswerAttemptDto.add(attemptDto);
	}
		return ResponseEntity.ok().body(listOfAnswerAttemptDto);
	}
	
	@GetMapping("/getanswer/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> listofAnswerByUser(@PathVariable Long id){
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<UserScore> userScoreList = userScoreRepo.findByUser(user);
			List<ListOfAnswerAttemptDto> listOfAnswerAttemptDto = new ArrayList<>();
			
			if(!userScoreList.isEmpty()) {
				for(UserScore score: userScoreList ) {
					ListOfAnswerAttemptDto attemptDto = new ListOfAnswerAttemptDto();
					attemptDto.setId(score.getId());
					attemptDto.setDate(score.getDate());
					
					List<ScoreBoard> scoreBoardList = boardRepo.findByTest(score);
					List<GetListOfquestionSetDto> list = new ArrayList<>();
					if(!scoreBoardList.isEmpty()) {
						for (ScoreBoard board: scoreBoardList) {
							GetListOfquestionSetDto answers = new GetListOfquestionSetDto();
							answers.setQuestion(board.getQuestion().getQuestionText());
							if(board.getOption() == null) {
								answers.setOption(null);
							} else {
								answers.setOption(board.getOption().getText());
							}
							List<Option> optionsList= optionRepo.findByQuestion(board.getQuestion());
							boolean optionCheck = true;
							for (Option option: optionsList) {
								if(option.isCorrect() == optionCheck) {
									answers.setCorrectOption(option.getText());
								}
							}
							list.add(answers);
						}
					} else {
						return ResponseEntity.badRequest().body(new MessageResponse("Error: exam is not found by id:"+ score.getId()));
					}
					attemptDto.setListOfAnswer(list);
					listOfAnswerAttemptDto.add(attemptDto);
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id: " + user.getId()));
			}
			return ResponseEntity.ok().body(listOfAnswerAttemptDto);

		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not found by user id:" + id));
		}
	}
	
	@GetMapping("/getanswer/exam={id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAnswersByExamsId(@PathVariable Long id){
		Optional<UserScore> optionalUserScore = userScoreRepo.findById(id);
		if(optionalUserScore.isPresent()) {
			UserScore score = optionalUserScore.get();
			ListOfAnswerAttemptDto attemptDto = new ListOfAnswerAttemptDto();
			attemptDto.setId(score.getId());
			attemptDto.setDate(score.getDate());
			
			List<ScoreBoard> scoreBoardList = boardRepo.findByTest(score);
			List<GetListOfquestionSetDto> list = new ArrayList<>();
			if(!scoreBoardList.isEmpty()) {
				for (ScoreBoard board: scoreBoardList) {
					GetListOfquestionSetDto answers = new GetListOfquestionSetDto();
					answers.setQuestion(board.getQuestion().getQuestionText());
					if(board.getOption() == null) {
						answers.setOption(null);
					} else {
						answers.setOption(board.getOption().getText());
					}
					List<Option> optionsList= optionRepo.findByQuestion(board.getQuestion());
					boolean optionCheck = true;
					for (Option option: optionsList) {
						if(option.isCorrect() == optionCheck) {
							answers.setCorrectOption(option.getText());
						}
					}
					list.add(answers);
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: exam is not found by id:"+ score.getId()));
			}
			attemptDto.setListOfAnswer(list);
			return ResponseEntity.ok().body(attemptDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Exam is not found by exam id:" + id));

		}
		
		
	}
	
	
	
}
package com.example.loksewa.aayog.LoksewaAayog.Controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Option;
import com.example.loksewa.aayog.LoksewaAayog.Entity.OptionType;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Repository.OptionTypeRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.EditOptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.EditQuestionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.OptionsDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionDTO;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.OptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ViewResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	private QuestionRepo questionRepo;

	@Autowired
	private OptionTypeRepo optRepo; 

	@PostMapping("/addingQuestion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionDTO questionDto){
		Question question = new Question();
		question.setQuestionText(questionDto.getQuestionText());
		
		int optionType = questionDto.getOptionType();
		OptionType opt = optRepo.findById(optionType);
		question.setOptionT(opt);
		
		List<OptionsDto> optionDto = questionDto.getOptions();
		List<Option> toSaveOptions = new ArrayList();
		for(OptionsDto dtoOption : optionDto )	{
			Option option = new Option();
			option.setText(dtoOption.getOptionName());
			option.setCorrect(dtoOption.isCorrect());
			option.setQuestion(question);
			toSaveOptions.add(option);
		}		
		
		question.setOptions(toSaveOptions);
		questionRepo.save(question);
		return ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
	}
	
	@GetMapping("/listOfQuestions")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> viewListOfQuestions(){
//		return ResponseEntity.ok(questionRepo.findAll());
		List<ViewResponseDto> listDto = new ArrayList<>();
		List<Question> questionList = questionRepo.findAll();
		
		for (Question question: questionList) {
			ViewResponseDto responseDto = new ViewResponseDto();
			responseDto.setId(question.getId());
			responseDto.setQuestion(question.getQuestionText());
			responseDto.setOptionType(question.getOptionT().getId());
			
			List<OptionDto> optionDto = new ArrayList<>();
			for(Option option: question.getOptions()) {
				OptionDto optDto = new OptionDto();
				optDto.setOption(option.getText());
				optDto.setCheck(option.isCorrect());
				optionDto.add(optDto);
			}
			responseDto.setOptionResponse(optionDto);
			
			listDto.add(responseDto);
			
		}
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping("/editQuestions/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> editQuestion(@PathVariable Long id){
		Optional<Question> question = questionRepo.findById(id);
		EditQuestionDto editQuestionDto = new EditQuestionDto();
		if(question.isPresent()) {
			
			editQuestionDto.setQuestion(question.get().getQuestionText());
			
			Long optionTypeIds = question.get().getOptionT().getId(); 
			int optionTypeId = optionTypeIds.intValue();
			
			editQuestionDto.setOptionType(optionTypeId);
			
			List<EditOptionDto> listForEdit = new ArrayList<>();
			for(Option option: question.get().getOptions()) {
				EditOptionDto editOptionDto = new EditOptionDto();
				editOptionDto.setOption(option.getText());
				editOptionDto.setCheck(option.isCorrect());
				listForEdit.add(editOptionDto);
			}
			editQuestionDto.setOption(listForEdit);
		}
		
		return ResponseEntity.ok().body(editQuestionDto);
	}
	
	@PutMapping("/editQuestions/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody EditQuestionDto editQuestionDto){
	Optional<Question> OptionalQuestion = questionRepo.findById(id);
	
	if(OptionalQuestion.isPresent()) {
		Question question = OptionalQuestion.get(); 
		question.setQuestionText(editQuestionDto.getQuestion());
		
		int optionType = editQuestionDto.getOptionType();
		OptionType opt = optRepo.findById(optionType);
		question.setOptionT(opt);

		List<Option> listOption = new ArrayList<>();
		List<EditOptionDto> listOptiondto = editQuestionDto.getOption();
		for(EditOptionDto optiondto: listOptiondto) {
			Option option = new Option();
			option.setText(optiondto.getOption());
			option.setCorrect(optiondto.isCheck());
			listOption.add(option);
		}
		
		question.setOptions(listOption);
		questionRepo.save(question);
		return ResponseEntity.ok().body(editQuestionDto);
	} 
	else {
		return ResponseEntity.notFound().build();
	}
	}
	
	
	@DeleteMapping("/deleteQuestion/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delteQuestionById(@PathVariable Long id){
		questionRepo.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Succesfully deleted the question id: " + id));
	}
}
//	@PostMapping("/answer")
//	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//	public ResponseEntity<?> evaluate(@RequestBody List<Answer> answers) {
//		UserScore userScore = new UserScore(); 
//		int rights = 0;
//		for (Answer answer : answers) 
//			rights +=  questionRepo.countByIdAndAnswer(answer.getId(), answer.getOption());
//		Score score = new Score(answers.size(), rights);
//		
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		
//		UserDetailsImpl userDetails =(UserDetailsImpl) auth.getPrincipal();
//		
//		String username = userDetails.getUsername();
//		User currentUser = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found by " + username));
//		
//		userScore.setRight(rights);
//		userScore.setTotal(answers.size());
//		
//		currentUser.getScores().add(userScore);
//		scoreRepo.save(userScore);
//		return ResponseEntity.status(HttpStatus.CREATED).body(score);
//	}
//	 
//}

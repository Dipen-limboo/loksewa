package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Option;
import com.example.loksewa.aayog.LoksewaAayog.Entity.OptionType;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Repository.CategoryRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.OptionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.OptionTypeRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.PositionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.EditOptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.EditQuestionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.OptionsDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionDTO;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.OptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ViewResponseDto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	private QuestionRepo questionRepo;

	@Autowired
	private OptionTypeRepo optRepo; 
	
	@Autowired
	private OptionRepo optionRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private PositionRepo positionRepo;

	@PostMapping("/addingQuestion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionDTO questionDto){
		Question question = new Question();
		question.setQuestionText(questionDto.getQuestionText());
		
		int categoryType = questionDto.getCategory();
		Long categoryId = (long) categoryType;
		Optional<Category> cate =  categoryRepo.findById(categoryId);
		if (cate.isPresent()) {
			question.setCategory(cate.get());
			
			Optional<Position> position = positionRepo.findById(questionDto.getPosition());
			if(position.isPresent()) {
				question.setPosition(position.get());
				
				question.setYear(questionDto.getYear());
				
				int optionType = questionDto.getOptionType();
				OptionType opt = optRepo.findById(optionType);
				question.setOptionT(opt);
				
				if(opt.getId() == 3) {
					List<Option> saveOptionsNull = new ArrayList<>();
					Option option = new Option();
					option.setText(null);
					option.setQuestion(question);
					option.setCorrect(false);
					saveOptionsNull.add(option);
				} else {
					List<OptionsDto> optionDto = questionDto.getOptions();
					List<Option> toSaveOptions = new ArrayList<>();
					for(OptionsDto dtoOption : optionDto )	{
						Option option = new Option();
						option.setText(dtoOption.getOptionName());
						option.setCorrect(dtoOption.isCorrect());
						option.setQuestion(question);
						toSaveOptions.add(option);
					}		
					question.setOptions(toSaveOptions);
				}
				questionRepo.save(question);
				return ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Category not found by Position id " + questionDto.getPosition()));
			}
			
			
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Category not found by category id " + categoryType));
		}
		
		
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
	
		Page<Question> questionPage = questionRepo.findAll(pageable);
		
		List<ViewResponseDto> listDto = new ArrayList<>();
		for (Question question: questionPage.getContent()) {
			ViewResponseDto responseDto = new ViewResponseDto();
			responseDto.setId(question.getId());
			int cateId= question.getCategory().getId().intValue();
			responseDto.setCategory(cateId);
			responseDto.setPosition(question.getPosition().getId());
			responseDto.setYear(question.getYear());			
			responseDto.setQuestion(question.getQuestionText());
			responseDto.setOptionType(question.getOptionT().getId());
			List<OptionDto> optionDto = new ArrayList<>();
			if(question.getOptionT().getId()==3) {
				
				OptionDto optDto = new OptionDto();
				optDto.setOption(null);
				optDto.setCheck(false);
				optionDto.add(optDto);
			}
			else {
				for(Option option: question.getOptions()) {
					OptionDto optDto = new OptionDto();
					optDto.setOption(option.getText());
					optDto.setCheck(option.isCorrect());
					optionDto.add(optDto);
				}
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
			int categoryId = question.get().getCategory().getId().intValue();
			editQuestionDto.setCategory(categoryId);
			
			editQuestionDto.setPosition(question.get().getPosition().getId());
			
			editQuestionDto.setYear(question.get().getYear());
			
			editQuestionDto.setQuestion(question.get().getQuestionText());
			
			Long optionTypeIds = question.get().getOptionT().getId(); 
			int optionTypeId = optionTypeIds.intValue();
			
			editQuestionDto.setOptionType(optionTypeId);
			
			List<EditOptionDto> listForEdit = new ArrayList<>();
			if(question.get().getOptionT().getId()==3) {
				
				EditOptionDto optDto = new EditOptionDto();
				optDto.setOption(null);
				optDto.setCheck(false);
				listForEdit.add(optDto);
			} else {
				for(Option option: question.get().getOptions()) {
					EditOptionDto editOptionDto = new EditOptionDto();
					editOptionDto.setOption(option.getText());
					editOptionDto.setCheck(option.isCorrect());
					listForEdit.add(editOptionDto);
				}
			}
			editQuestionDto.setOption(listForEdit);
			return ResponseEntity.ok().body(editQuestionDto);
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Question not found by question id" + id));
		}
	
	}
	
	@PutMapping("/editQuestions/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public ResponseEntity<?> updateQuestion(@PathVariable Long id, @RequestBody EditQuestionDto editQuestionDto){
	Optional<Question> OptionalQuestion = questionRepo.findById(id);
	
	if(OptionalQuestion.isPresent()) {
		Question question = OptionalQuestion.get(); 
		List<Option> optionList = optionRepo.findByQuestion(question);
		
		int categoryId = editQuestionDto.getCategory();
		Long cateId = (long) categoryId;
		Optional<Category> category = categoryRepo.findById(cateId);
		
		if(category.isPresent()) {
			question.setCategory(category.get());
			
			Optional<Position> optionalPosition = positionRepo.findById(editQuestionDto.getPosition());
			
			if(optionalPosition.isPresent()) {
				Position position = optionalPosition.get();
				question.setPosition(position);
				question.setYear(editQuestionDto.getYear());
				question.setQuestionText(editQuestionDto.getQuestion());
				
				int optionType = editQuestionDto.getOptionType();
				OptionType opt = optRepo.findById(optionType);
				question.setOptionT(opt);
				
				List<EditOptionDto> editOptionDtoList = editQuestionDto.getOption();
				
				if(opt.getId() == 3) {
					question.getOptions().clear();
					Option option = new Option();
					option.setText(null);
					option.setQuestion(question);
					option.setCorrect(false);
				} else {
					for (int i= 0; i < Math.min(optionList.size(), editOptionDtoList.size()); i++ ) {
						Option existingOption = optionList.get(i);
						EditOptionDto editOptionDto = editOptionDtoList.get(i);
						
						existingOption.setText(editOptionDto.getOption());
						existingOption.setCorrect(editOptionDto.isCheck());
						optionRepo.save(existingOption);
						
						
					}
					}
				questionRepo.save(question);
				return ResponseEntity.ok().body(editQuestionDto);
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Position not found by id" + editQuestionDto.getPosition()));
			}
			
			
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Category not found by id" +categoryId));
		}
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
package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Question;
import com.example.loksewa.aayog.LoksewaAayog.Entity.QuestionSet;
import com.example.loksewa.aayog.LoksewaAayog.Entity.SetOption;
import com.example.loksewa.aayog.LoksewaAayog.Repository.CategoryRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.PositionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionRepo;
import com.example.loksewa.aayog.LoksewaAayog.Repository.QuestionsetRepo;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionIdDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.OptionDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.ViewResponseDto;

import jakarta.transaction.Transactional;
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
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@PostMapping("/addingQuestionSet")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> creatingQuestionSet(@Valid @RequestBody QuestionSetDto questionSetDto) {
	    QuestionSet questionSet = new QuestionSet();

	    Optional<Category> optionalCategory = categoryRepo.findById(questionSetDto.getCategory());
	    if (optionalCategory.isPresent()) {
	        Category cate = optionalCategory.get();
	        questionSet.setCategory(cate);

	        int positionIds = questionSetDto.getPosition();

	        Optional<Position> positionOptional = positionRepo.findById(positionIds);

	        if (positionOptional.isPresent()) {
	            Position position = positionOptional.get();
	            questionSet.setPosition(position);

	            Set<String> strOptions = questionSetDto.getSetOption();
	            System.out.println(">>>>>>>>>>>>>>>>"+strOptions);
	            if (strOptions == null) {
	                questionSet.setOptions(SetOption.SELECT);
	            } else {
	                strOptions.forEach(option -> {
	                    switch (option) {
	                        case "random":
	                            questionSet.setOptions(SetOption.RANDOM);
	                            break;
	                        default:
	                            questionSet.setOptions(SetOption.SELECT);
	                    }
	                });
	            }

	           
	            	
	            if (strOptions != null && strOptions.contains("random")) {
	                List<Question> listQuestion = questionRepo.findByPositionAndCategory(position, cate);
	                List<Question> questionlistSet = new ArrayList<>();

	                if (!listQuestion.isEmpty()) {
	                    Random random = new Random();
	                    Set<Integer> selectedIndices = new HashSet<>();

	                    int maxQuestions = Math.min(2, listQuestion.size());

	                    for (int i = 0; i < maxQuestions; i++) {
	                        int randomIndex;

	                        do {
	                            randomIndex = random.nextInt(listQuestion.size());
	                        } while (selectedIndices.contains(randomIndex));

	                        selectedIndices.add(randomIndex);

	                        Question randomQuestion = listQuestion.get(randomIndex);
	                        questionlistSet.add(randomQuestion);
	                    
	                }

	                questionSet.setQuestion(questionlistSet);
	                questionsetRepo.save(questionSet);
	            }

	                } else {
	                    List<QuestionIdDto> questionIdDto = questionSetDto.getQuestionId();
	                    List<Question> questions = new ArrayList<>();
	                    for (QuestionIdDto questiondto : questionIdDto) {
	                        Long id = questiondto.getId();
	                        Optional<Question> optionalQuestion = questionRepo.findById(id);
	                        if (optionalQuestion.isPresent()) {
	                            Question question = optionalQuestion.get();
	                            question.setId(id);
	                            questions.add(question);
	                        }
	                    }
	                    questionSet.setQuestion(questions);
	                    questionsetRepo.save(questionSet);

	                }


	            return ResponseEntity.status(HttpStatus.CREATED).body(questionSet);

	        } else {
	            return ResponseEntity.badRequest().body(new MessageResponse("ERROR: Position with Id " + positionIds + " not Found."));
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with ID " + questionSetDto.getCategory() + " not found");
	    }
	}

	
	@GetMapping("/listOfSetQuestions")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> listOfQuestionSets( @RequestParam(defaultValue = "1") int page,
		    @RequestParam(defaultValue = "1") int size,
		    @RequestParam(name = "sort", required = false, defaultValue = "id") String id,
		    @RequestParam(name = "order", required = false, defaultValue = "desc") String sortDir
			) {
	    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(id).ascending() : Sort.by(id).descending();
	    Pageable pageable = PageRequest.of(page -1, size, sort);
	    Page<QuestionSet> questionsetPage = questionsetRepo.findAll(pageable);
	    List<QuestionSetDto> listSetDto = new ArrayList<>();
	  
	    
	    for(QuestionSet questionSet: questionsetPage.getContent()) {
	    	QuestionSetDto questionSetDto = new QuestionSetDto();
	    	
	    	Long setid = questionSet.getId();
	    	int questionSetid = setid.intValue();
	    	questionSetDto.setId(questionSetid);

	    	questionSetDto.setCategory(questionSet.getCategory().getId());
	    	Position position = questionSet.getPosition();
	    	int positionId = position.getId().intValue();
	    	questionSetDto.setPosition(positionId);
	    	
	    	SetOption options = questionSet.getOptions();
	    	Set<String> optionSet = new HashSet<>();
	    	optionSet.add(options.name());
	    	questionSetDto.setSetOption(optionSet);
	    	
	    	List<QuestionIdDto> questionIds = new ArrayList<>();
	    	for(Question question: questionSet.getQuestion()) {
	    		QuestionIdDto idDto = new QuestionIdDto();
	    		idDto.setId(question.getId());
	    		questionIds.add(idDto);
	    	}
	    	questionSetDto.setQuestionId(questionIds);
	    	listSetDto.add(questionSetDto);
	    	
	    }
	return ResponseEntity.ok().body(listSetDto);
	}
	
	@GetMapping("/editQuestionSet/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getQuestionSetById(@PathVariable Long id){
		Optional<QuestionSet> questionSet = questionsetRepo.findById(id);
		if(questionSet.isPresent()) {
			QuestionSet questions  = questionSet.get(); 
			QuestionSetDto questionSetDto = new QuestionSetDto();
			
			int questionSetId = questions.getId().intValue();
			questionSetDto.setId(questionSetId);
			
			questionSetDto.setCategory(questions.getCategory().getId());
			Position position = questions.getPosition();
			int positionId = position.getId().intValue();
			questionSetDto.setPosition(positionId);
			
			SetOption options = questionSet.get().getOptions();
	    	Set<String> optionSet = new HashSet<>();
	    	optionSet.add(options.name());
	    	questionSetDto.setSetOption(optionSet);
			
			List<QuestionIdDto> questionIds = new ArrayList<>();
			for(Question question: questions.getQuestion()) {
				QuestionIdDto questionIdDto = new QuestionIdDto();
				questionIdDto.setId(question.getId());
				questionIds.add(questionIdDto);
			}
			questionSetDto.setQuestionId(questionIds);
			return ResponseEntity.ok().body(questionSetDto);
		} else {
		return ResponseEntity.badRequest().body(new MessageResponse("questionSet id: "+id +"not found"));
		}
	}
	
	@PutMapping("/editQuestionSet/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> updateQuestionSetById(@PathVariable Long id, @RequestBody QuestionSetDto questionSetDto) {
        Optional<QuestionSet> optionalQuestionSet = questionsetRepo.findById(id);

        if (optionalQuestionSet.isPresent()) {
            QuestionSet questionSet = optionalQuestionSet.get();
            questionSet.setId(id);
            
            Optional<Category> optionalCategory = categoryRepo.findById(questionSetDto.getCategory());
            if(optionalCategory.isPresent()) {
            	Category category = optionalCategory.get();
            	questionSet.setCategory(category);
            	int positionId = questionSetDto.getPosition();
                Optional<Position> positionOptional = positionRepo.findById(positionId);

                if (positionOptional.isPresent()) {
                    Position position = positionOptional.get();
                    questionSet.setPosition(position);
                    
                    
                    questionsetRepo.save(questionSet);
                    return ResponseEntity.ok().body(questionSetDto);
                } else {
                    return ResponseEntity.badRequest().body("Invalid Position ID");
                }
            } else {
            	return ResponseEntity.badRequest().body(new MessageResponse("Error: category not found by category id " + questionSetDto.getCategory()));
            }
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/deleteByid/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteQuestionSetById(@PathVariable Long id){
		questionsetRepo.deleteById(id);
		return ResponseEntity.ok().body(new MessageResponse("Successfully Deleted the question set id: " +id));
	}
	

	@GetMapping("/getQuestionsByYear/{year}/{category_id}/{position_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getQuestionSetByyear(@PathVariable int year, @PathVariable Long category_id, @PathVariable Long position_id) {
		
		Optional<Category> cateOptional = categoryRepo.findById(category_id);
		if(cateOptional.isPresent()) {
			Category cate = cateOptional.get();
			Optional<Position> optionalPosition = positionRepo.findById(position_id);
			if(optionalPosition.isPresent()) {
				Position position = optionalPosition.get();
				List<Question> questionList = questionRepo.findByYearAndCategoryAndPosition(year, cate, position);
				List<ViewResponseDto> listDto = new ArrayList<>();
				if(!questionList.isEmpty()) {
					for(Question question: questionList) {
						ViewResponseDto responseDto = new ViewResponseDto();
						responseDto.setId(question.getId());
						int cateId= question.getCategory().getId().intValue();
						responseDto.setCategory(cateId);
						responseDto.setPosition(question.getPosition().getId());
						responseDto.setYear(question.getYear());			
						responseDto.setQuestion(question.getQuestionText());
						responseDto.setOptionType(question.getOptionT().getId());
						List<OptionDto> optionDto = new ArrayList<>();
						if(question.getOptionT().getId() == 3) {
							OptionDto optDto = new OptionDto();
							optDto.setOption(null);
							optDto.setCheck(false);
							optionDto.add(optDto);
							responseDto.setOptionResponse(optionDto);
						} else {
							for(Option option: question.getOptions()) {
								OptionDto optDto = new OptionDto();
								optDto.setOption(option.getText());
								optDto.setCheck(option.isCorrect());
								optionDto.add(optDto);
							}
							responseDto.setOptionResponse(optionDto);
						}
						listDto.add(responseDto);
					}
					return ResponseEntity.ok().body(listDto);
				}else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: question List is empty"));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Position not found by id " + position_id));
			}
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Category not found by id " + category_id ));
		}

	}
}

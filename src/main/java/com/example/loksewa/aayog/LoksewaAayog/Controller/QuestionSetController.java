package com.example.loksewa.aayog.LoksewaAayog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.QuestionSetDto;
import com.example.loksewa.aayog.LoksewaAayog.service.QuestionSetService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questionset")
public class QuestionSetController {

	@Autowired
	QuestionSetService setService;
	
	@PostMapping("/addingQuestionSet")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> creatingQuestionSet(@Valid @RequestBody QuestionSetDto questionSetDto) {
	    return setService.saveQuestionSet(questionSetDto);
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
	    return setService.listQuestionSet(pageable);
	}
	
	@GetMapping("/editQuestionSet/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getQuestionSetById(@PathVariable Long id){
		return setService.getById(id);
	}
	
	@PutMapping("/editQuestionSet/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<?> updateQuestionSetById(@PathVariable Long id, @RequestBody QuestionSetDto questionSetDto) {
        return setService.updateById(id, questionSetDto);
    }
	
	@DeleteMapping("/deleteByid/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteQuestionSetById(@PathVariable Long id){
		return setService.deleteById(id);
	}
	

	@GetMapping("/getQuestionsByYear/{year}/{category_id}/{position_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getQuestionSetByyear(@PathVariable int year, @PathVariable Long category_id, @PathVariable Long position_id) {
		return setService.getByYearAndCategoryAndPosition(year, category_id, position_id );
	}
}

package com.example.loksewa.aayog.LoksewaAayog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.DashBoardDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;

@Service
public class DashboardService {
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
	
	public ResponseEntity<?> getUserDashboard(Long userId) {
	       Optional<User> optionalUser = userRepo.findById(userId);
	       DashBoardDto dashboardDto = new DashBoardDto();
	       int totalExam = 0;
	       int totalQuestion = 0;
	       int rights = 0;
	       boolean check = true;
	
	       if (optionalUser.isPresent()) {
	           User user = optionalUser.get();
	           List<UserScore> testList = userScoreRepo.findByUser(user);
	
	           if (!testList.isEmpty()) {
	               for (UserScore test : testList) {
	                   totalExam += userScoreRepo.countById(test.getId());
	                   totalQuestion += boardRepo.countByTest(test);
	                   List<ScoreBoard> answersList = boardRepo.findByTest(test);
	
	                   for (ScoreBoard answer : answersList) {
	                       if (answer.getOption() != null) {
	                           rights += optionRepo.countByIdAndIsCorrect(answer.getOption().getId(), check);
	                       }
	                   }
	               }
		                double average = totalQuestion > 0 ? ((double) rights / totalQuestion) * 100.00 : 0.0;
	               dashboardDto.setTotal_exams(totalExam);
	               dashboardDto.setAverageMarks(average);
		            } else {
	               return ResponseEntity.badRequest().body(new MessageResponse("Error: Test not found done by user "
	               + user.getFirstName() + user.getLastName()));
		            }
		} else {
		    return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id " + userId));
		    }
		
		    return ResponseEntity.ok().body(dashboardDto);
	}
}

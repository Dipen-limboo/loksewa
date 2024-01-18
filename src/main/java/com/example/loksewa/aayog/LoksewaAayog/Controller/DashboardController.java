package com.example.loksewa.aayog.LoksewaAayog.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loksewa.aayog.LoksewaAayog.Entity.Category;
import com.example.loksewa.aayog.LoksewaAayog.Entity.Position;
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
import com.example.loksewa.aayog.LoksewaAayog.payload.reqeust.DashBoardDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.AdminDashboardDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.CategoryListDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.MessageResponse;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.PositionListDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.UserListDto;
import com.example.loksewa.aayog.LoksewaAayog.payload.response.YearDto;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
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
	
	
	//dashboard of user;
		@GetMapping("/userDashboard/{id}")
		@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
		public ResponseEntity<?> dashboardOfUser(@PathVariable Long id){
			Optional<User> optionalUser = userRepo.findById(id);
			DashBoardDto dashboardDto = new DashBoardDto();
			int totalExam = 0;
			int totalQuestion = 0;
			int rights = 0;
			boolean check = true;
			if(optionalUser.isPresent()) {
				User user = optionalUser.get();
				List<UserScore> testList = userScoreRepo.findByUser(user);
				if(!testList.isEmpty()) {
					for(UserScore test: testList) {
						totalExam += userScoreRepo.countById(test.getId());
						totalQuestion += boardRepo.countByTest(test);
						List<ScoreBoard> answersList = boardRepo.findByTest(test);
						for(ScoreBoard answer: answersList) {
							if(answer.getOption() != null) {
								rights += optionRepo.countByIdAndIsCorrect(answer.getOption().getId(), check);
							}
						}
					}
					double average = ((double)rights/totalQuestion)*100.00;
					dashboardDto.setTotal_exams(totalExam);
					dashboardDto.setAverageMarks(average);
					
					
				} else {
					return ResponseEntity.badRequest().body(new MessageResponse("Error: test not found done by user " +user.getFirstName() +user.getLastName()));
				}
			} else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found by id " +id));
			}
			return ResponseEntity.ok().body(dashboardDto);
		}
		
		
		//dashboard of admin
		@GetMapping("/dashBoardAdmin")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<?> dashBoardAdmin(){
			AdminDashboardDto adminDashboardDto = new AdminDashboardDto();
			List<Category> categoryList = cateRepo.findAll();
			if(!categoryList.isEmpty()) {
				List<CategoryListDto> categoryListDto = new ArrayList<>();
				for(Category cate: categoryList) {
					CategoryListDto categoryDto = new CategoryListDto();
					int totalExams =0;
					int total = 0;
					categoryDto.setCategory(cate.getName());
					
					List<QuestionSet> questionSetListCategory = questionSetRepo.findByCategory(cate);
					if(!questionSetListCategory.isEmpty()) {
						for(QuestionSet questionset: questionSetListCategory) {
							totalExams += userScoreRepo.countByQuestionSet(questionset);
							
						}
						Long totalcount = userScoreRepo.count();
						total = totalcount.intValue();
						double avg = ((double)totalExams/total)*100.00;
						categoryDto.setTotal_exams(totalExams);
						categoryDto.setAverage(avg);
						categoryListDto.add(categoryDto);
					}
				}
				adminDashboardDto.setCategoryList(categoryListDto);
				
			}else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: No any Category found"));
			}
			List<Position> positionList = positionRepo.findAll();
			if (!positionList.isEmpty()) {
			    List<PositionListDto> positionListDto = new ArrayList<>();
			    for (Position position : positionList) {
			        PositionListDto positiondto = new PositionListDto();
			        positiondto.setPosition(position.getName());
			        int totalExams = 0;
			        int total = 0;
			        List<QuestionSet> questionSetListPosition = questionSetRepo.findByPosition(position);
			        for (QuestionSet questionset : questionSetListPosition) {
			            totalExams += userScoreRepo.countByQuestionSet(questionset);
			        }
			        Long totalcount = userScoreRepo.count();
			        total = totalcount.intValue();
			        if (total != 0) {
			            double avg = ((double) totalExams / total) * 100.00;
			            positiondto.setTotal_exams(totalExams);
			            positiondto.setAverage_exams(avg);
			            positionListDto.add(positiondto);
			        }
			    }
			    adminDashboardDto.setPositionList(positionListDto);
			} else {
			    return ResponseEntity.badRequest().body(new MessageResponse("Error: No any Position found"));
			}			
			
			List<UserScore> userScoreList = userScoreRepo.findAll();
			Set<String> uniqueUsernames = new HashSet<>();
			List<UserListDto> userListDto = new ArrayList<>();

			for (UserScore test : userScoreList) {
			    String username = test.getUser().getUsername();
			    if (!uniqueUsernames.contains(username)) {
			        UserListDto userDto = new UserListDto();
			        userDto.setUsername(username);
			        uniqueUsernames.add(username);
			        int totalExams =0;
			        int total =0;
			        
		       		totalExams += userScoreRepo.countByUser(test.getUser());
		       		Long totalCount = userScoreRepo.count();
		       		total = totalCount.intValue();
		       		if(total!=0) {
		       			double avg = ((double) totalExams / total) * 100.00;
			       		userDto.setTotal_exams(totalExams);
			       		userDto.setAverage_marks(avg);
				        userListDto.add(userDto);
		       		}
			    }
			}
			adminDashboardDto.setUserList(userListDto);
			
			Set<Integer> uniqueYear = new HashSet<>();
			List<YearDto> yearlistDto = new ArrayList<>();
			for (UserScore test: userScoreList) {
				int year = test.getQuestionSet().getYear();				
				if(!uniqueYear.contains(year)) {
					YearDto yeardto  = new YearDto();
					yeardto.setYear(year);
					uniqueYear.add(year);
					int totalExams = 0;
					int total =0;
					List<QuestionSet> questionSetListYear = questionSetRepo.findByYear(year);
			        for (QuestionSet questionset : questionSetListYear) {
			            totalExams += userScoreRepo.countByQuestionSet(questionset);
			        }
			        Long totalcount = userScoreRepo.count();
			        total = totalcount.intValue();
			        if (total != 0) {
			            double avg = ((double) totalExams / total) * 100.00;
			            yeardto.setTotal_exams(totalExams);
			            yeardto.setAvg(avg);
			            yearlistDto.add(yeardto);
			        }
					
				}
			}
			adminDashboardDto.setYearList(yearlistDto);
			return ResponseEntity.ok().body(adminDashboardDto);
		}
}

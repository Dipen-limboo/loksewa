package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="questions")
public class Question {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    @NotEmpty(message="Question should be inserted")
    private String questionText;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="question_positions",
    		joinColumns = @JoinColumn(name="question_id"),
    		inverseJoinColumns = @JoinColumn(name="positions_id")
    		)
    private Set<Position> position = new HashSet<>();
    
    @Column(name="opt_a") 
    @NotEmpty(message="Option should be inserted")
    private String optionA;
    
    @Column(name="opt_b") 
    @NotEmpty(message="Option should be inserted")
    private String optionB;
    
    @Column(name="opt_c") 
    @NotEmpty(message="Option should be inserted")
    private String optionC;
    
    @Column(name="opt_d") 
    @NotEmpty(message="Option should be inserted")
    private String optionD;
    
    @Column(name="answer") 
    @NotNull(message="Anser must be enter")
    private int answer;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Question(@NotEmpty(message = "Question should be inserted") String questionText,
			@NotEmpty(message = "Option should be inserted") String optionA,
			@NotEmpty(message = "Option should be inserted") String optionB,
			@NotEmpty(message = "Option should be inserted") String optionC,
			@NotEmpty(message = "Option should be inserted") String optionD,
			@NotEmpty(message = "Anser must be enter") int answer) {
		super();
		this.questionText = questionText;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Set<Position> getPosition() {
		return position;
	}

	public void setPosition(Set<Position> position) {
		this.position = position;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}
    
    
}

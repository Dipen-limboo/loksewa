package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="questionSets")
public class QuestionSet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message="Select for which position")
    @JoinColumn(name = "position_id")
    private Position position;
	
	@Column(name="year_set")
	@Min(value = 2069, message = "Year must start with 20")
	@Max(value = 2081, message = "Year must not exceed 2099")
	private int year;  
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sets_question",
		joinColumns = @JoinColumn(name="set_id"),
		inverseJoinColumns = @JoinColumn(name="question_id")
		)
	@Size(max = 20, message = "The number of questions cannot exceed 20")
	private List<Question> question = new ArrayList<>();


	public QuestionSet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public QuestionSet(Long id, @NotNull(message = "Select for which position") Position position,
			@Min(value = 2069, message = "Year must start with 20") @Max(value = 2081, message = "Year must not exceed 2099") int year,
			@Size(max = 20, message = "The number of questions cannot exceed 20") List<Question> question) {
		super();
		this.id = id;
		this.position = position;
		this.year = year;
		this.question = question;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Position getPosition() {
		return position;
	}


	public void setPosition(Position position) {
		this.position = position;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public List<Question> getQuestion() {
		return question;
	}


	public void setQuestion(List<Question> question) {
		this.question = question;
	}
	
}
package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="questionSets")
public class QuestionSet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message="Select for which Category")
    @JoinColumn(name = "category_id")
    private Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message="Select for which position")
    @JoinColumn(name = "position_id")
    private Position position;
	
	@Enumerated(EnumType.STRING)
    @NotNull(message="Select Options")
    @Column(name = "options_set")
    private SetOption options;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="sets_question",
		joinColumns = @JoinColumn(name="quesitonset_id"),
		inverseJoinColumns = @JoinColumn(name="question_id")
		)
	@Size(max = 20, message = "The number of questions cannot exceed 20")
	private List<Question> question = new ArrayList<>();

	@Column(name="year")
	private int year;
	
	
	
	public QuestionSet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public QuestionSet(Long id, @NotNull(message = "Select for which Category") Category category, @NotNull(message = "Select for which position") Position position,
			@NotNull(message = "Select options") SetOption options, @Size(max = 20, message = "The number of questions cannot exceed 20") List<Question> question) {
		super();
		this.category = category;
		this.id = id;
		this.position = position;
		this.options = options;
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

	public List<Question> getQuestion() {
		return question;
	}


	public void setQuestion(List<Question> question) {
		this.question = question;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public SetOption getOptions() {
		return options;
	}


	public void setOptions(SetOption options) {
		this.options = options;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}
	
	
}

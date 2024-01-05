package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="questions", 
			uniqueConstraints = @UniqueConstraint(columnNames="question_text"))
public class Question {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text")
    @NotEmpty(message="Question should be inserted")
    private String questionText;
    
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message="Select for which position")
    @JoinColumn(name = "position_id")
    private Position position;
	
	@Column(name="year_question")
	@Min(value = 2069, message = "Year must start with 20")
	@Max(value = 2081, message = "Year must not exceed 2099")
	private int year;  
	
    @ManyToOne
    @JoinColumn(name="option_type_id")
    private OptionType optionT;
    
    @OneToMany(mappedBy="question", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Option> options;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(Long id, @NotEmpty(message = "Question should be inserted") String questionText, Category category,
			@NotNull(message = "Select for which position") Position position,
			@Min(value = 2069, message = "Year must start with 20") @Max(value = 2081, message = "Year must not exceed 2099") int year,
			OptionType optionT, List<Option> options) {
		super();
		this.id = id;
		this.questionText = questionText;
		this.category = category;
		this.position = position;
		this.year = year;
		this.optionT = optionT;
		this.options = options;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public OptionType getOptionT() {
		return optionT;
	}

	public void setOptionT(OptionType optionT) {
		this.optionT = optionT;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	
    
}

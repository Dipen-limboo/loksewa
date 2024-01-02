package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;

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
    @JoinColumn(name="option_type_id")
    private OptionType optionT;
    
    @OneToMany(mappedBy="question", cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Option> options;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(Long id, @NotEmpty(message = "Question should be inserted") String questionText, OptionType optionT,
			List<Option> options) {
		super();
		this.id = id;
		this.questionText = questionText;
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

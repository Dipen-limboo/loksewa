package com.example.loksewa.aayog.LoksewaAayog.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="score_boards")
public class ScoreBoard {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="total_attempt")
	private int total;
	
	@Column(name="correct_answers")
	private int right;
	
	@OneToMany(mappedBy="board", cascade = CascadeType.ALL)
	private List<UserScore> score;
	
	public ScoreBoard() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreBoard(Long id, int total, int right) {
		super();
		this.id = id;
		this.total = total;
		this.right = right;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

}

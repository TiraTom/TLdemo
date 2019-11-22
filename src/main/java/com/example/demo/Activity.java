package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import jp.tldemo.constants.Constants;


@Entity
@Table(name="activities")
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	@Max(value=999999999, message=Constants.VALIDATION_ERROR_COST)
	@Min(value=0, message=Constants.VALIDATION_ERROR_COST)
	private int cost;

	@Column
	@NotEmpty
	@Size(min=1, max=30, message=Constants.VALIDATION_ERROR_TITLE_LENGTH)
	private String title;

	public Activity() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

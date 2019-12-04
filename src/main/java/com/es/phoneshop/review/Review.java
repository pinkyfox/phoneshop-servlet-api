package com.es.phoneshop.review;

import java.io.Serializable;

public class Review implements Serializable {
	private String username;
	private int stars;
	private String comment;

	public Review() {
	}

	public Review(String username, int stars, String comment) {
		this.username = username;
		this.stars = stars;
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}

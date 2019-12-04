package com.es.phoneshop.review;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public class ReviewService {
	private static ReviewService instance;

	private ReviewService() {
	}

	public static ReviewService getInstance() {
		if (instance == null) {
			instance = new ReviewService();
		}
		return instance;
	}

	public void placeReview(Product product, Review review) {
		product.setReview(review);
	}

	public List<Review> getReviews(Product product) {
		return product.getReview();
	}
}

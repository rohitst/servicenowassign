package com.servicenow.repository

import com.servicenow.api.RetrofitBuilder
import com.servicenow.model.Review
import io.reactivex.Single

class ReviewsRepository {

    private val apiService = RetrofitBuilder.apiService //TODO inject

    fun getAllReviews() : Single<List<Review>> {
        return apiService.getReviews().map {
            it.map { apiReviewModel ->
                Review.fromApiModel(apiReviewModel)
            }
        }
    }

}
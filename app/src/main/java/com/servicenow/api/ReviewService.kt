package com.servicenow.api

import io.reactivex.Single
import retrofit2.http.GET

interface ReviewApiService {

    @GET("api/jsonBlob/891065824971079680")
    fun getReviews() : Single<List<ApiReview>>

}
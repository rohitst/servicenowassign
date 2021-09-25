package com.servicenow.api

import com.servicenow.exercise.R

data class ApiReview(
    var name: String,
    var review: String,
    var rating: Int,
    var location: String
)
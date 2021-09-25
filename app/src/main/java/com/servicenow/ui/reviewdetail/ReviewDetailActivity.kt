package com.servicenow.ui.reviewdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.servicenow.model.Review
import com.servicenow.exercise.databinding.ActivityReviewDetailBinding
import java.lang.IllegalArgumentException

class ReviewDetailActivity : AppCompatActivity() {

    companion object {
        const val REVIEW_NAME_KEY = "reviewNameKey"
        const val REVIEW_REVIEW_KEY = "reviewReviewKey"
        const val REVIEW_RATING_KEY = "reviewRatingKey"
        const val REVIEW_LOCATION_KEY = "reviewLocationKey"

        fun makeIntent(context: Context, review: Review): Intent {

            return Intent(context, ReviewDetailActivity::class.java).apply {
                this.putExtra(REVIEW_NAME_KEY, review.name)
                this.putExtra(REVIEW_REVIEW_KEY, review.review)
                this.putExtra(REVIEW_RATING_KEY, review.rating)
                this.putExtra(REVIEW_LOCATION_KEY, review.location)
            }
        }
    }

    private lateinit var binding: ActivityReviewDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityReviewDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val review = Review(
            name = intent.getStringExtra(REVIEW_NAME_KEY)
                ?: throw IllegalArgumentException("Review Name is required"),
            review = intent.getStringExtra(REVIEW_REVIEW_KEY) ?: throw IllegalArgumentException(
                "Review review is required"
            ),
            location = intent.getStringExtra(REVIEW_LOCATION_KEY)
                ?: throw IllegalArgumentException("Review location is required"),
            rating = intent.getIntExtra(
                REVIEW_RATING_KEY,
                Int.MIN_VALUE //Would rather an exception is thrown here, but there is no nullable getIntExtra method
            )
        )


        binding.image.setImageResource(Review.getIconResourceFromName(review.name))
        binding.name.text = review.name
        binding.reviewText.text = review.review
        binding.location.text = review.location
        binding.rating.text = "⭐".repeat(review.rating)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
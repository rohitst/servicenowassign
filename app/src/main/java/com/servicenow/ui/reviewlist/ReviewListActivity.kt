package com.servicenow.ui.reviewlist

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.servicenow.exercise.databinding.ActivityReviewListBinding
import com.servicenow.exercise.databinding.ActivityReviewListBinding.inflate
import com.servicenow.ui.reviewdetail.ReviewDetailActivity

class ReviewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewListBinding

    private val viewModel by viewModels<ReviewListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        val view = binding.root

        val adapter = ReviewListAdapter { review ->
            startActivity(ReviewDetailActivity.makeIntent(baseContext, review))
        }
        binding.activityReviewListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.activityReviewListRecyclerView.addItemDecoration(DividerItemDecoration(binding.activityReviewListRecyclerView.context, DividerItemDecoration.VERTICAL))
        binding.activityReviewListRecyclerView.adapter = adapter


        binding.activityReviewListErrorRefreshButton.setOnClickListener {
            viewModel.refresh()
        }

        viewModel.viewState.observe(this ) { viewState ->
            when (viewState.loadingState) {
                UILoadingState.initial -> {
                    binding.activityReviewListRecyclerView.visibility = View.GONE
                    binding.activityReviewListLoadingView.visibility = View.GONE
                    binding.activityReviewListErrorView.visibility = View.GONE
                }
                UILoadingState.refreshing -> {
                    binding.activityReviewListRecyclerView.visibility = View.GONE
                    binding.activityReviewListLoadingView.visibility = View.VISIBLE
                    binding.activityReviewListErrorView.visibility = View.GONE
                }
                UILoadingState.success -> {
                    binding.activityReviewListRecyclerView.visibility = View.VISIBLE
                    binding.activityReviewListLoadingView.visibility = View.GONE
                    binding.activityReviewListErrorView.visibility = View.GONE
                    adapter.submitList(viewState.data?.toMutableList())
                }
                UILoadingState.error -> {
                    binding.activityReviewListRecyclerView.visibility = View.GONE
                    binding.activityReviewListLoadingView.visibility = View.GONE
                    binding.activityReviewListErrorView.visibility = View.VISIBLE
                }
            }
        }


        setContentView(view)
    }

}

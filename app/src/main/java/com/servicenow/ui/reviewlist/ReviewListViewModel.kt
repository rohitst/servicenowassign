package com.servicenow.ui.reviewlist

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.servicenow.api.RetrofitBuilder
import com.servicenow.model.Review
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ReviewListViewModel : ViewModel() {

    var viewState : MutableLiveData<ReviewListViewState> = MutableLiveData()

    private var disposables: MutableList<Disposable> = mutableListOf()
    init {
        refresh()
    }

    fun refresh() {
        viewState.postValue(ReviewListViewState(UILoadingState.refreshing, null))
        disposables += RetrofitBuilder.apiService.getReviews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { reviews ->
                    viewState.postValue(ReviewListViewState(UILoadingState.success, reviews))

                }, { error ->
                    viewState.postValue(ReviewListViewState(UILoadingState.error, null))
                }

            )
    }


    override fun onCleared() {
        super.onCleared()
        disposables.forEach {
            it.dispose()
        }
        disposables.clear()
    }

}

data class ReviewListViewState(
    val loadingState: UILoadingState = UILoadingState.initial,
    val data: List<Review>? = null
)

//Move to a shared library
//Should idealy be a sealed class <T>
enum class UILoadingState {
    initial, refreshing, success, error
}
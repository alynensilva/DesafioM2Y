package br.com.alyne.moviescreen.ui.similarmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.alyne.moviescreen.data.rep.NetworkState
import br.com.alyne.moviescreen.data.value_object.Movie
import io.reactivex.disposables.CompositeDisposable

class SimilarMoviesViewModel(private val similarMoviesRepository : MoviePagedListRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    //To get similar movies with pagination:
    val moviePagedList : LiveData<PagedList<Movie>> by lazy {
        similarMoviesRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        similarMoviesRepository.getNetworkState()
    }

    fun emptyList(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    //To dispose the disposable composite when the activity is destroyed
    override fun onCleared(){
        super.onCleared()
        compositeDisposable.dispose()
    }
}
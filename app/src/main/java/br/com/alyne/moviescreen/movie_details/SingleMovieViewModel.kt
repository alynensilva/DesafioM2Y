package br.com.alyne.moviescreen.movie_details

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alyne.moviescreen.data.rep.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    //To get details only when needed:
    val movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }
    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    //To dispose the disposable composite when the activity is destroyed
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
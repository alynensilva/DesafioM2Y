package br.com.alyne.moviescreen.data.rep

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alyne.moviescreen.data.api.TheMovieDBInterface
import br.com.alyne.moviescreen.movie_details.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource (private val apiService : TheMovieDBInterface, private val compositeDisposable: ArrayCompositeDisposable : CompositeDisposable){
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                    apiService.getMovieDetails(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        _downloadedMovieDetailsResponse.postValue(it)
                                        _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        _networkState.postValue(NetworkState.ERROR)
                                        Log.e("MovieDetailsDataSource", it.message)

                                    }
                            )
                    )
            }
        catch (e: Exception){
            Log.e("MovieDetailsDataSource", e.message)
            }
        }

}


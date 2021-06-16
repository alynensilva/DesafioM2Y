package br.com.alyne.moviescreen.ui.single_movie_details

import androidx.lifecycle.LiveData
import br.com.alyne.moviescreen.data.api.TheMovieDBInterface
import br.com.alyne.moviescreen.data.rep.MovieDetailsNetworkDataSource
import br.com.alyne.moviescreen.data.rep.NetworkState
import br.com.alyne.moviescreen.data.value_object.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private  val apiService : TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }
    fun getMovieDetailsNetworkState(): LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}
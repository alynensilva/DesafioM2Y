package br.com.alyne.moviescreen.data.rep

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.alyne.moviescreen.data.api.TheMovieDBInterface
import br.com.alyne.moviescreen.data.value_object.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable)
    : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource =  MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}
package br.com.alyne.moviescreen.data.api

import br.com.alyne.moviescreen.data.value_object.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TheMovieDBInterface {
    //https://api.themoviedb.org/3/movie/372058/similar?api_key=165ad824f6875e3df1e29366b3faf0f2
    //https://api.themoviedb.org/3/movie/372058?api_key=165ad824f6875e3df1e29366b3faf0f2
    //https://api.themoviedb.org/3/

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path ("movie_id") id: Int): Single<MovieDetails> //This function gets the movie details from the API


}
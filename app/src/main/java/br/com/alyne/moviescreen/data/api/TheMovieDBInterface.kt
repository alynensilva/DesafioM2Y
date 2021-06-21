package br.com.alyne.moviescreen.data.api

import br.com.alyne.moviescreen.data.value_object.MovieDetails
import br.com.alyne.moviescreen.data.value_object.MovieSimilar
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
    /*
    URL for movie information:
    https://api.themoviedb.org/3/movie/372058?api_key=165ad824f6875e3df1e29366b3faf0f2

    URL for similar movies:
    https://api.themoviedb.org/3/movie/372058/similar?api_key=165ad824f6875e3df1e29366b3faf0f2
    */

    //This function gets movie details from the API
    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path ("movie_id") id: Int): Single<MovieDetails>

    //This function gets movies similar to the specific movie used in this project
    @GET("movie/372058/similar")
    fun getSimilarMovies(@Query("page") page: Int): Single<MovieSimilar>

}
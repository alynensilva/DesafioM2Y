package br.com.alyne.moviescreen.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//https://api.themoviedb.org/3/movie/372058/similar?api_key=165ad824f6875e3df1e29366b3faf0f2
//https://api.themoviedb.org/3/movie/372058?api_key=165ad824f6875e3df1e29366b3faf0f2
//https://image.tmdb.org/t/p/w342/q719jXXEzOoYaps6babgKnONONX.jpg

const val API_KEY = "165ad824f6875e3df1e29366b3faf0f2"
const val BASE_URL ="https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

object TheMovieDBClient {
    fun getClient(): TheMovieDBInterface {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
            val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
            return@Interceptor chain.proceed(request)
        }
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheMovieDBInterface::class.java)

    }
}
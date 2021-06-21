package br.com.alyne.moviescreen.data.value_object


import com.google.gson.annotations.SerializedName

data class MovieSimilar(
    val page: Int,
        @SerializedName("results")
    val movieList: List<Movie>,
        @SerializedName("total_pages")
    val totalPages: Int,
        @SerializedName("total_results")
    val totalResults: Int
)
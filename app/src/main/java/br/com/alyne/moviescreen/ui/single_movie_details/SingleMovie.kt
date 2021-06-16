package br.com.alyne.moviescreen.ui.single_movie_details
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.com.alyne.moviescreen.data.api.POSTER_BASE_URL
import br.com.alyne.moviescreen.data.api.TheMovieDBClient
import br.com.alyne.moviescreen.data.api.TheMovieDBInterface
import br.com.alyne.moviescreen.data.rep.NetworkState
import br.com.alyne.moviescreen.data.value_object.MovieDetails
import br.com.alyne.moviescreen.databinding.ActivitySingleMovieBinding
import com.bumptech.glide.Glide

class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var binding : ActivitySingleMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        //val view = binding.root
        setContentView(binding.root)



        //val progress_bar = findViewById<View>(R.id. progress_bar)
        //val text_error = findViewById<View>(R.id. text_error)

        val movieId: Int = 1
        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {


            binding.progressBar.visibility =
                    if (it == NetworkState.LOADING)
                        View.VISIBLE
                    else
                        View.GONE
            binding.textError.visibility =
                    if (it == NetworkState.ERROR)
                        View.VISIBLE
                    else
                        View.GONE
        })
    }
    //val movie_title = findViewById<View>(R.id. movie_title)
    //val movie_tagline = findViewById<View>(R.id. movie_tagline)
    //val release_date = findViewById<View>(R.id. release_date)
    //val movie_rating = findViewById<View>(R.id. movie_rating)
    //val movie_runtime = findViewById<View>(R.id. movie_runtime)
    //val movie_overview = findViewById<View>(R.id. movie_overview)
    //val movie_poster = findViewById<View>(R.id. movie_poster)

    fun bindUI(it: MovieDetails){
        binding.movieTitle.text = it.title
        binding.movieTagline.text = it.tagline
        binding.releaseDate.text = it.releaseDate
        binding.movieRating.text = it.popularity.toString()
        binding.movieRuntime.text = it.runtime.toString()
        binding.movieOverview.text = it.overview

        val moviePosterUL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
                .load(moviePosterUL)
                .into(binding.moviePoster)
    }

    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
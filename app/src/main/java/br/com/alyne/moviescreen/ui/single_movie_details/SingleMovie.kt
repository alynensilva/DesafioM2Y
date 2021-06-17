package br.com.alyne.moviescreen.ui.single_movie_details
import android.content.Intent
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
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var binding : ActivitySingleMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, SingleMovie::class.java)
        intent.putExtra("id",372058 )
        val movieId: Int = intent.getIntExtra("id",1)

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

    fun bindUI(it: MovieDetails){
        binding.movieTitle.text = it.title
        binding.movieCounts.text = it.vote_count.toString()
        binding.movieReleaseDate.text = it.releaseDate
        binding.moviePopularity.text = it.popularity.toString()
        binding.movieRuntime.text = it.runtime.toString()
        binding.movieOverview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        binding.movieBudget.text = formatCurrency.format(it.budget)
        binding.movieRevenue.text = formatCurrency.format(it.revenue)

        val moviePosterUL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
                .load(moviePosterUL)
                .into(binding.ivMoviePoster)
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
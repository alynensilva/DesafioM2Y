package br.com.alyne.moviescreen.ui.movie_details
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alyne.moviescreen.data.api.POSTER_BASE_URL
import br.com.alyne.moviescreen.data.api.TheMovieDBClient
import br.com.alyne.moviescreen.data.api.TheMovieDBInterface
import br.com.alyne.moviescreen.data.rep.NetworkState
import br.com.alyne.moviescreen.data.value_object.MovieDetails
import br.com.alyne.moviescreen.databinding.ActivityMovieDetailsBinding
import br.com.alyne.moviescreen.ui.similarmovie.MoviePagedListRepository
import br.com.alyne.moviescreen.ui.similarmovie.SimilarMoviePagedListAdapter
import br.com.alyne.moviescreen.ui.similarmovie.SimilarMoviesViewModel
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieRepository: MovieDetailsRepository
    private lateinit var similarMoviesRepository: MoviePagedListRepository
    private lateinit var binding : ActivityMovieDetailsBinding
    private lateinit var simMovieViewModel : SimilarMoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //This will be used to access the movie information such as name, likes, and popularity
        val movieId: Int = intent.getIntExtra("id",372058)

        //Binding the layout
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Linking to the API Client
        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        similarMoviesRepository = MoviePagedListRepository(apiService)


        //Setting observers for movie information
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

        //Setting observers for the list of similar movies
       val movieAdapter = SimilarMoviePagedListAdapter(this)
       val linearLayoutManager = LinearLayoutManager(this)

        binding.movieList.layoutManager = linearLayoutManager
        binding.movieList.adapter = movieAdapter

        simMovieViewModel = getSimMovieViewModel()
        simMovieViewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })
        simMovieViewModel.networkState.observe (this, Observer{
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

                if (!simMovieViewModel.emptyList()) {
                    movieAdapter.setNetworkState(it)
                }
        })
    }

    fun bindUI(it: MovieDetails){
        binding.movieTitle.text = it.title
        binding.moviePopularity.text = it.popularity.toString() + " views"
        binding.movieCounts.text = it.voteCount.toString() + " likes"

        val moviePosterUL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
                .load(moviePosterUL)
                .into(binding.ivMoviePoster)
    }

    private fun getViewModel(movieId:Int): MovieDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository, movieId) as T
            }
        })[MovieDetailsViewModel::class.java]
    }

    private fun getSimMovieViewModel(): SimilarMoviesViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SimilarMoviesViewModel(similarMoviesRepository) as T
            }
        })[SimilarMoviesViewModel::class.java]
    }
}
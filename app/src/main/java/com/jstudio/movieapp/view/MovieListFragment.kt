package com.jstudio.movieapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.Navigation

import  com.jstudio.movieapp.R
import com.jstudio.movieapp.viewmodel.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private val moviesListAdapter = MoviesListAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    // instantiate our interface.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //instantiate the view model  instance of our view model inside our fragment.
         viewModel=ViewModelProviders.of(this).get(MovieListViewModel::class.java)


        //this is going to call our refresh methods it's going to generate some movie objects.
        //A movie list and it's going to update the variables here.
        viewModel.refresh()


        //to retrieve that information here.
        //1- instantiate our dogs list.
        moviesList.apply {
            //it allows the system to order our elements sequentially in a linear fashion from top to bottom
            layoutManager = LinearLayoutManager(context)

            //2- we attach the adapter
            adapter=moviesListAdapter
        }


        refreshLayout.setOnRefreshListener {
            moviesList.visibility=View.GONE
            listError.visibility=View.GONE
            loadingView.visibility=View.VISIBLE
            viewModel.refreshByPassCache()
            refreshLayout.isRefreshing=false
        }

        observeViewModel()

    }

    // this method is going to use the variables that we've created
    //  in the ListViewModel to update the layout based on the values that we get
    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            movies?.let {
                moviesList.visibility = View.VISIBLE
                moviesListAdapter.updateMovieList(movies)
            }
        })

        viewModel.moviesLoadError.observe(viewLifecycleOwner, Observer {isError ->
            isError?.let {
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    listError.visibility = View.GONE
                    moviesList.visibility = View.GONE
                }
            }
        })
    }



}
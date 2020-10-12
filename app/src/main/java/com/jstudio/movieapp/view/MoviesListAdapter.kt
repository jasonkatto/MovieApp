package com.jstudio.movieapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.jstudio.movieapp.R
import com.jstudio.movieapp.databinding.ItemNoviesBinding
import com.jstudio.movieapp.model.MoviesInfo
import kotlinx.android.synthetic.main.item_novies.view.*

class MoviesListAdapter(val moviesList: ArrayList<MoviesInfo>) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>(), MoviesClickListener {

    fun updateMovieList(newMovieList: List<MoviesInfo>){
        moviesList.clear()
        moviesList.addAll(newMovieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
       // val view: View = inflater.inflate(R.layout.item_novies, parent, false)
        val view =DataBindingUtil.inflate<ItemNoviesBinding>(inflater,R.layout.item_novies, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.view.movie=moviesList[position]
        holder.view.listener= this
//        holder.view.name.text=moviesList[position].movieName
//        holder.view.detail.text=moviesList[position].movieId
//        //holder.view.name.text=moviesList[position].movieTitle
//       // holder.view.name.text=moviesList[position].movieCategory
//        //holder.view.name.text=moviesList[position].imageUrl
//
//        holder.view.setOnClickListener {
//
//        }
//
//        holder.view.imageView.loadImage(moviesList[position].imageUrl,
//        getProgressDrawable(holder.view.imageView.context))
    }

    override fun getItemCount() = moviesList.size

    override fun onMoviesClicked(v: View) {
        val uuid=v.movieId.text.toString().toInt()
        val action=MovieListFragmentDirections.actionDetailFragment()
        action.movieUuid= uuid
        Navigation.findNavController(v).navigate(action)
    }


    class MoviesViewHolder(var view:ItemNoviesBinding) : RecyclerView.ViewHolder(view.root)



}
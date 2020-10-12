package com.jstudio.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jstudio.movieapp.model.MoviesDatabase
import com.jstudio.movieapp.model.MoviesInfo
import kotlinx.coroutines.launch
import java.util.*

class DetailViewModel(application: Application):BaseViewModel(application){

    val movieLiveData=MutableLiveData<MoviesInfo>()

    fun fetch(uuid: Int){
        launch {
            val dog=MoviesDatabase(getApplication()).movieDao().getMovie(uuid)
            movieLiveData.value=dog
        }
    }
}
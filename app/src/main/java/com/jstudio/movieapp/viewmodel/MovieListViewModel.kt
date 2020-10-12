package com.jstudio.movieapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.jstudio.movieapp.model.MoviesApiService
import com.jstudio.movieapp.model.MoviesDatabase
import com.jstudio.movieapp.model.MoviesInfo
import com.jstudio.movieapp.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class MovieListViewModel(application: Application) :BaseViewModel(application) {
    private var refreshTime=10*1000*1000L

    private var prefHelper= SharedPreferencesHelper(getApplication())

    private val movieService=MoviesApiService()

    private val disposable=CompositeDisposable()

    //liveData variables
    //this will provide the information for the
    // actual list of dogs that we retrieve from all data source
    // which can be the backend API can.
    // or can be a local database or any other data source that we have
    val movies= MutableLiveData<List<MoviesInfo>>()

    //This Life data will notify whoever is
    //listening to this new model will notify that there's an error
    // It will just specify that there is a generic error with the retrieval of the data.
    val moviesLoadError=MutableLiveData<Boolean>()


    // this live data will tell whoever's listening that the system is
    //loading so the data has not yet arrived.
    val loading=MutableLiveData<Boolean>()



    fun refresh(){
        val updateTime=prefHelper.getUpdateTime()
        if (updateTime !=null && updateTime !=0L && System.nanoTime() - updateTime<refreshTime){
            fetchFromDataBase()
        }
        else{
            fetchFromRemote()
        }
    }
    fun refreshByPassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDataBase() {
        loading.value=true
        launch {
            val movies=MoviesDatabase(getApplication()).movieDao().getAlMovies()
            moviesRetrieve(movies)
            Toast.makeText(getApplication(), "Dogs Retrieved from database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote() {
         loading.value=true
        disposable.add(
            movieService.getMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<MoviesInfo>>(){
                    override fun onSuccess(moviesList: List<MoviesInfo>) {
                         storeMoviesLocally(moviesList)
                        Toast.makeText(getApplication(), "Dogs Retrieved from endpoint", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                         moviesLoadError.value=true
                        loading.value=false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun moviesRetrieve(moviesList: List<MoviesInfo>){
        //movies.value=moviesList
        movies.value=moviesList
        moviesLoadError.value=false
        loading.value=false
    }

    private fun storeMoviesLocally(list: List<MoviesInfo>){
        //to run the coroutine on the separate threat
        launch {
            val dao=MoviesDatabase(getApplication()).movieDao()
            dao.deleteAllMovies()
            val result=dao.insertAll(*list.toTypedArray())
            var i =0
            while (i<list.size){
                list[i].uuid=result[i].toInt()
            }
            moviesRetrieve(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())

    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
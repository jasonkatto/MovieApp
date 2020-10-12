package com.jstudio.movieapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    suspend fun  insertAll(vararg movies:MoviesInfo):List<Long>

    @Query("SELECT * FROM moviesinfo")
    suspend fun getAlMovies():List<MoviesInfo>


    @Query("SELECT * FROM  moviesinfo   WHERE uuid=:movieId")
    suspend fun getMovie(movieId:Int):MoviesInfo


    @Query("DELETE FROM  moviesinfo")
    suspend fun  deleteAllMovies()
}
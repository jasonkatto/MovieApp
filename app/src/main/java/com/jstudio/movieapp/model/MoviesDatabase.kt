package com.jstudio.movieapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(MoviesInfo::class),version = 1)
abstract class MoviesDatabase:RoomDatabase() {

    abstract fun movieDao():MovieDao


    companion object{
        @Volatile private var instance:MoviesDatabase?=null
        private val LOCK=Any()


        operator fun invoke(context: Context)= instance?: synchronized(LOCK){
            instance?:buildDatabase(context).also{
                instance=it
            }
        }
        private fun buildDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            MoviesDatabase::class.java,
            "movieDatabase"
        ).build()
    }
}
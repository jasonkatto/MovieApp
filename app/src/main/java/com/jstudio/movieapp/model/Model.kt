package com.jstudio.movieapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MoviesInfo(
     @ColumnInfo(name ="movie_name" )
     @SerializedName("name")
     val movieName:String?,

     @ColumnInfo(name = "movie_id")
     @SerializedName("id")
     val movieId:String?,

     @ColumnInfo(name = "movie_title")
     @SerializedName("title")
     val movieTitle:String?,

     @ColumnInfo(name = "move_category")
     @SerializedName("category")
     val movieCategory:String?,

     @ColumnInfo(name = "movie_url")
     @SerializedName("url")
     val imageUrl:String?



 ){
     @PrimaryKey(autoGenerate = true)
     var uuid:Int=0
}

data class MoviePalette(var color:Int)
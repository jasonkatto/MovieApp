package com.jstudio.movieapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jstudio.movieapp.R

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth=10f
        centerRadius=50f
        start()
    }
}

fun ImageView.loadImage(uri:String?, progressDrawable:CircularProgressDrawable){
    val option=RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_movie_icon)
    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadingImage(view: ImageView, url:String){
    view.loadImage(url, getProgressDrawable(view.context))
}
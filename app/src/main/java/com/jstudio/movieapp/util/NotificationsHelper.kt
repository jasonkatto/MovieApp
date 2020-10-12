package com.jstudio.movieapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jstudio.movieapp.R
import com.jstudio.movieapp.view.MainActivity

class NotificationsHelper(val context: Context) {
    private val CHANNEL_ID="Movies channel id"
    private val NOTIFICATION_ID=123


    fun createNotification(){
        createNotificationChannel()
        val intent= Intent(context, MainActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent= PendingIntent.getActivity(context, 0 , intent, 0)

        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_movie_icon_background)


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_icon_background)
            .setLargeIcon(icon)
            .setContentTitle("Dogs retrieved")
            .setContentText("This Notification has some content")
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigLargeIcon(icon)
                    .bigLargeIcon(null)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name=CHANNEL_ID
            val descriptionText="Channel description"
            val importance= NotificationManager.IMPORTANCE_DEFAULT
            val channel= NotificationChannel(CHANNEL_ID, name, importance).apply {
                description=descriptionText
            }
            //to register the channel
            val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
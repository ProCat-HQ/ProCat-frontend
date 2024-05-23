package com.example.procatfirst.ui.personal.notifications

import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.procatfirst.R


fun makeNotification(context: Context, title: String, content: String) {

    // Create an explicit intent for an Activity in your app.
    val intent = Intent(context, AlertDetails::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val CHANNEL_ID = "CHANNEL_ID_NOTIFICATION_999"
    val textTitle = title
    val textContent = content

    /*
    val ACTION_MARK_READ = "Пометить как прочитанное"

    val markReadIntent = Intent(context, MyBroadcastReceiver::class.java).apply {
        action = ACTION_MARK_READ
        putExtra(EXTRA_NOTIFICATION_ID, 0)
    }
    val markReadPendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, markReadIntent,
        PendingIntent.FLAG_MUTABLE) */

    var builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.cat_logo)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        // Set the intent that fires when the user taps the notification.
        .setContentIntent(pendingIntent)
        //.addAction(R.drawable.cat_logo, "Mark As Read", markReadPendingIntent)
        .setAutoCancel(true)

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is not in the Support Library.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = title
        val descriptionText = content
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(0, builder.build())

}




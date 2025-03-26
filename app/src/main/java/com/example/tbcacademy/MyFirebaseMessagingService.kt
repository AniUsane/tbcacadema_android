package com.example.tbcacademy

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log.d
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.tbcacademy.App.Companion.FIREBASE_MESSAGING_TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    //called when token is generated
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        d(FIREBASE_MESSAGING_TAG, "New FCM Token: $token")
    }

    //method is called when FCM message is received. handles foreground and background notifications
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        d(FIREBASE_MESSAGING_TAG, "".plus(remoteMessage.data))

        remoteMessage.notification?.let {
            val title = it.title ?: "New Notification"
            val message = it.body ?: "You have a new message"

            d(FIREBASE_MESSAGING_TAG, "Foreground Notification: Title: $title, Body: $message")
            showNotification(title, message)
        }

        if (remoteMessage.data.isNotEmpty()) {
            d(FIREBASE_MESSAGING_TAG, "Data Payload: ${remoteMessage.data}")
        }
    }

    //shows notification
    private fun showNotification(title: String, content: String){
        val channelId = "FCM_CHANNEL_ID"

        //creates channel for notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Firebase Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for Firebase Cloud Messaging"
                enableLights(true)
                enableVibration(true)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        //checks permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            d(FIREBASE_MESSAGING_TAG, "Notification permission not granted. Cannot show notification.")
            return
        }


        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        //notification builder
        var builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager?.notify(System.currentTimeMillis().toInt(), builder.build())
        d(FIREBASE_MESSAGING_TAG, "Foreground notification displayed successfully.")

    }
}
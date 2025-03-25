package com.example.tbcacademy

import android.util.Log.d
import androidx.core.app.NotificationCompat
import com.example.tbcacademy.App.Companion.FIREBASE_MESSAGING_TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)

        //update servers
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //respond to received messages
        d(FIREBASE_MESSAGING_TAG, "".plus(remoteMessage.data))
    }

    private fun showNotification(title: String, content: String){
        var builder = NotificationCompat.Builder(this, applicationContext.getString(R.string.app_name))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(content)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}
package com.example.tbcacademy

import android.app.Application
import android.util.Log.d
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application(){
    override fun onCreate() {
        super.onCreate()
        setUpMessaging()
    }

    private fun setUpMessaging(){
        //initializes FCM and gets token
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if(!task.isSuccessful){
                    d(FIREBASE_MESSAGING_TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                d(FIREBASE_MESSAGING_TAG, "".plus(token))
            })
    }

    companion object{
        const val FIREBASE_MESSAGING_TAG = "FIREBASE MESSAGING TAG"
    }
}
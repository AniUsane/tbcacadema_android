package com.example.tbcacademy

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tbcacademy.App.Companion.FIREBASE_MESSAGING_TAG
import com.example.tbcacademy.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askNotificationPermission()

        val title = intent.extras?.getString("title") ?: "No Title"
        val desc = intent.extras?.getString("desc") ?: "No Description"
        val customKey = intent.extras?.getString("customKey") ?: "No Custom Key"

        d(FIREBASE_MESSAGING_TAG, "Notification Data - Title: $title, Desc: $desc, CustomKey: $customKey")
    }

    //handles permission results
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            d(FIREBASE_MESSAGING_TAG, "Notification permission granted")
        } else {
            d(FIREBASE_MESSAGING_TAG, "Notification permission denied")
        }
    }

    //requests notification permission
    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    d(FIREBASE_MESSAGING_TAG, "Notification permission already granted")
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    d(FIREBASE_MESSAGING_TAG, "Showing rationale for notification permission")
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)}
                else -> {
                    d(FIREBASE_MESSAGING_TAG, "Requesting notification permission")
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }
}
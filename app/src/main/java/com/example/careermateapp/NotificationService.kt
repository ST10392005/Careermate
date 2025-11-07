package com.example.careermateapp


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "NotificationService"
        const val CHANNEL_ID = "careermate_notifications"
        const val CHANNEL_NAME = "CareerMate Notifications"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed FCM token: $token")

        // Send token to your server if needed
        CoroutineScope(Dispatchers.IO).launch {
            sendTokenToServer(token)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a notification payload
        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Message Notification Body: ${notification.body}")
            sendNotification(
                title = notification.title ?: getString(R.string.app_name),
                message = notification.body ?: "New notification"
            )
        }

        // Handle data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataMessage(remoteMessage.data)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "CareerMate app notifications"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun handleDataMessage(data: Map<String, String>) {
        // Handle different types of data messages
        when (data["type"]) {
            "job_alert" -> handleJobAlert(data)
            "resume_analysis" -> handleResumeAnalysis(data)
            "interview_tip" -> handleInterviewTip(data)
            else -> Log.d(TAG, "Unknown message type: ${data["type"]}")
        }
    }

    private fun handleJobAlert(data: Map<String, String>) {
        val jobTitle = data["job_title"] ?: "New Job Alert"
        val company = data["company"] ?: "Company"
        val message = "New job matching your profile: $jobTitle at $company"

        sendNotification("Job Alert", message)
    }

    private fun handleResumeAnalysis(data: Map<String, String>) {
        val score = data["score"] ?: "0"
        val message = "Your resume analysis is complete! Score: $score%"

        sendNotification("Resume Analysis Complete", message)
    }

    private fun handleInterviewTip(data: Map<String, String>) {
        val tip = data["tip"] ?: "New interview tip available"

        sendNotification("Interview Tip", tip)
    }

    private fun sendTokenToServer(token: String) {
        // Implement token sending to your backend server
        Log.d(TAG, "Sending token to server: $token")
        // Example: Retrofit call to your API
    }
}
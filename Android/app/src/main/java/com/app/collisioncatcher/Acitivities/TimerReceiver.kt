package com.app.collisioncatcher.Acitivities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import android.R

class TimerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "timer_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Timer", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        // Cancel action intent
        val cancelIntent = Intent(context, CancelReceiver::class.java)
        val cancelPendingIntent = PendingIntent.getBroadcast(
            context, 1, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Timer Expired")
            .setContentText("Click cancel to stop further actions.")
            .setSmallIcon(R.drawable.ic_dialog_alert)
            .addAction(R.drawable.ic_menu_close_clear_cancel, "Cancel", cancelPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(100, notification)
    }
}

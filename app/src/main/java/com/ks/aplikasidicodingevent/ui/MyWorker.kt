package com.ks.aplikasidicodingevent.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ks.aplikasidicodingevent.R
import com.ks.aplikasidicodingevent.data.remote.response.EventResponse
import com.ks.aplikasidicodingevent.data.remote.retrofit.ApiConfig
import com.loopj.android.http.SyncHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    companion object {
        const val EXTRA_EVENT = "event"
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }


    override fun doWork(): Result {
        val dataEvent = inputData.getString(EXTRA_EVENT)
        getUpcomingEvent(dataEvent)
        return Result.success()
    }

    private fun getUpcomingEvent(dataEvent: String?){
        Looper.prepare()
        SyncHttpClient()
        ApiConfig.getApiService().newEvent(-1, 1).enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    val name = eventResponse?.listEvents?.get(0)?.name ?: "Unknown error"
                    val time = eventResponse?.listEvents?.get(0)?.beginTime ?: "Unknown error"
                    showNotification(name, time)
                } else {
                    showNotification("Get Upcoming Event Failed", "Failed to retrieve event")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showNotification("Get Upcoming Event Failed", t.message ?: "Unknown error")
            }
        })
    }

    private fun showNotification(name: String, time: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(name)
            .setContentText(time)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
}
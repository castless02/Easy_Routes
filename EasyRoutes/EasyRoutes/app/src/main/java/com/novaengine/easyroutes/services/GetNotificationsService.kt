package com.novaengine.easyroutes.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.gson.JsonArray
import com.novaengine.easyroutes.R
import com.novaengine.easyroutes.serversupport.DBQueriesService
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Calendar

class GetNotificationsService : Service() {

    private val getNotificationsScope = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null
    private val CHANNEL_ID = "EasyRoutesNotificationChannel"
    private val NOTIFICATION_ID = 1


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        job = getNotificationsScope.launch {
            performBackgroundTask()
            stopSelf()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    // Perform background task
    suspend fun performBackgroundTask() {
        // Load the shared preferences
        val sp = getSharedPreferences(getString(R.string.er_preferences_file), Context.MODE_PRIVATE)
        val Id = sp.getString("id", null).toString()

        getBookingStatusNotification(Id)
    }

    private fun getBookingStatusNotification(Id: String) {
        DBQueriesService().getBookingStatusNotification(Id) { result ->

            if (result != null && result.has("queryset")) {
                val jsonArray = result.get("queryset") as JsonArray

                var areThereIncomingBookings = false
                var incomingBookings = 0
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val currentDate = dateFormat.format(Calendar.getInstance().time)

                Log.v("GetNotificationsService", "Current date is: ${currentDate}.")

                for (i in 0 until jsonArray.size()) {

                    val jsonObject = jsonArray.get(i).asJsonObject
                    val bookingId = jsonObject.get("Id_Viaggio").asString
                    val bookingDate = jsonObject.get("Data_andata").asString

                    val dateComparison1 = dateFormat.parse(currentDate)
                    val dateComparison2 = dateFormat.parse(bookingDate)

                    Log.v("GetNotificationsService", "Booking date for booking id ${bookingId} is: ${dateComparison1}")

                    val calendarComparison1 = Calendar.getInstance()
                    val calendarComparison2 = Calendar.getInstance()

                    calendarComparison1.time = dateComparison1
                    calendarComparison2.time = dateComparison2

                    val diffInDays = (calendarComparison2.timeInMillis - calendarComparison1.timeInMillis) / (24 * 60 * 60 * 1000)
                    Log.v("GetNotificationsService", "Difference in days: $diffInDays")

                    // Check difference in days
                    if (diffInDays > 0 && diffInDays < 2) {
                        if (!areThereIncomingBookings) {
                            areThereIncomingBookings = true
                        }
                        incomingBookings += 1
                    }

                }

                Log.i("GetNotificationsService", "NOTIFICATION: There are ${incomingBookings} new incoming bookings.")

                if(areThereIncomingBookings) {
                    // Create a notification channel
                    createNotificationChannel()

                    // Show heads up notification
                    showHeadsUpNotification(incomingBookings)
                }


            }
            else {
                Log.e("BooksFragment", "An error occured while getting incoming bookins...")
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Easy Routes Notification Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
            channel.enableLights(true)
            channel.lightColor = Color.RED
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showHeadsUpNotification(incomingBookings: Int) {
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Notifica")
            .setContentText("Ci sono ${incomingBookings} nuove prenotazioni imminenti. Controlla la schermata delle prenotazioni.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setFullScreenIntent(null, true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

}
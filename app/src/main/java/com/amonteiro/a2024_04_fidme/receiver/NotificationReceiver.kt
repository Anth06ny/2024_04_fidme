package com.amonteiro.a2024_04_fidme.receiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //On récupère la notification reçu
        val notification = intent.getParcelableExtra<Notification>("MaCle")

        //Est ce qu'on à la permission de poster ?
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED && notification != null) {
            //on poste
            val ncm = NotificationManagerCompat.from(context);
            ncm.notify(29, notification)
        }
    }
}
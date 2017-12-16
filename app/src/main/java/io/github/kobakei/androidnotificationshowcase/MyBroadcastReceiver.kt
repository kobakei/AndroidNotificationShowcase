package io.github.kobakei.androidnotificationshowcase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.RemoteInput
import android.widget.Toast

/**
 * Created by keisuke on 2017/12/16.
 */
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            val input = remoteInput.getString(NotificationUtility.KEY_REMOTE_INPUT)
            Toast.makeText(context, input, Toast.LENGTH_SHORT).show()

            NotificationUtility.showRepliedNotification(context)
        }
    }
}
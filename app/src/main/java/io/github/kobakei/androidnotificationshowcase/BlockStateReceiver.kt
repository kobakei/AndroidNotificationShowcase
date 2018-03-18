package io.github.kobakei.androidnotificationshowcase

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BlockStateReceiver : BroadcastReceiver() {

    @SuppressLint("InlinedApi")
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED) {
            Toast.makeText(context, "Action: $action, blocked: ${intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, true)}", Toast.LENGTH_SHORT).show()
        }

        if (action == NotificationManager.ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED) {
            val blocked = intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, true)
            val id = intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID)
            Toast.makeText(context, "Action: $action, blocked: $blocked, ID: $id", Toast.LENGTH_SHORT).show()
        }

        if (action == NotificationManager.ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED) {
            val blocked = intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, true)
            val id = intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_GROUP_ID)
            Toast.makeText(context, "Action: $action, blocked: $blocked, ID: $id", Toast.LENGTH_SHORT).show()
        }
    }

}
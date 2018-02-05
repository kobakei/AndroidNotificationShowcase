package io.github.kobakei.androidnotificationshowcase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.RemoteInput
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        NotificationUtility.setUpNotificationChannel(applicationContext)

        buttonBasic.setOnClickListener {
            NotificationUtility.showNotification(applicationContext)
        }
        buttonAction.setOnClickListener {
            NotificationUtility.showNotificationWithActions(applicationContext)
        }
        buttonBigPicture.setOnClickListener {
            NotificationUtility.showBigPictureNotification(applicationContext)
        }
        buttonBigText.setOnClickListener {
            NotificationUtility.showBigTextNotification(applicationContext)
        }
        buttonInbox.setOnClickListener {
            NotificationUtility.showInboxNotification(applicationContext)
        }
        buttonMessaging.setOnClickListener {
            NotificationUtility.showMessagingNotification(applicationContext)
        }
        buttonCustomView.setOnClickListener {
            NotificationUtility.showCustomViewNotification(applicationContext)
        }
        buttonCustomView2.setOnClickListener {
            NotificationUtility.showCustomViewNotification2(applicationContext)
        }
        buttonDecoratedCustomView.setOnClickListener {
            NotificationUtility.showDecoratedCustomViewNotification(applicationContext)
        }
        buttonReply.setOnClickListener {
            NotificationUtility.showReplyNotification(applicationContext)
        }
        buttonHeadUp.setOnClickListener {
            NotificationUtility.showHeadUpNotification(applicationContext)
        }
        buttonLockScreen.setOnClickListener {
            Handler().postDelayed({
                NotificationUtility.showLockScreenNotification(applicationContext)
            }, 5000L)
        }
        buttonBundled.setOnClickListener {
            NotificationUtility.showBundledNotification(applicationContext)
        }
        buttonColorized.setOnClickListener {
            startService(SampleForegroundService.createIntent(applicationContext))
        }
        buttonColorized2.setOnClickListener {
            startService(SampleForegroundService2.createIntent(applicationContext))
        }
    }
}

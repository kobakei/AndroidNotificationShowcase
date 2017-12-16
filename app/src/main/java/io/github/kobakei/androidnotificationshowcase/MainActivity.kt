package io.github.kobakei.androidnotificationshowcase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        buttonDecoratedCustomView.setOnClickListener {
            NotificationUtility.showDecoratedCustomViewNotification(applicationContext)
        }
        buttonHeadUp.setOnClickListener {
            NotificationUtility.showHeadUpNotification(applicationContext)
        }
        buttonLockScreen.setOnClickListener {
            NotificationUtility.showLockScreenNotification(applicationContext)
        }
        buttonBundled.setOnClickListener {
            NotificationUtility.showBundledNotification(applicationContext)
        }
        buttonColorized.setOnClickListener {
            //NotificationUtility.showColorizedNotification(applicationContext)
            startService(SampleForegroundService.createIntent(applicationContext))
        }
    }

}

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
            NotificationUtility.showNotification(applicationContext, "channel",
                    "This is title", "This is message")
        }
        buttonAction.setOnClickListener {
            NotificationUtility.showNotificationWithActions(applicationContext, "channel",
                    "This is title", "This is message")
        }
        buttonBigPicture.setOnClickListener {
            NotificationUtility.showBigPictureNotification(applicationContext, "channel",
                    "This is title", "This is message")
        }
        buttonBigText.setOnClickListener {
            NotificationUtility.showBigTextNotification(applicationContext, "channel",
                    "This is title", "This is message")
        }
        buttonInbox.setOnClickListener {
            NotificationUtility.showInboxNotification(applicationContext, "channel",
                    "This is title", "This is message")
        }
    }

}

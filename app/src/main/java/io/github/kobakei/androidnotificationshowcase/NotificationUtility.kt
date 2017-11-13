package io.github.kobakei.androidnotificationshowcase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat

/**
 * 通知関連のユーティリティ
 * Created by keisukekobayashi on 2017/11/09.
 */
class NotificationUtility {
    companion object {

        /**
         * 通知チャンネルを作成する
         */
        fun setUpNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel("channel", "チャンネル1", NotificationManager.IMPORTANCE_DEFAULT)
                val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nm.createNotificationChannel(channel)
            }
        }

        /**
         * 通知を表示する
         */
        fun showNotification(context: Context, channel: String, title: String, message: String) {
            val notification = NotificationCompat.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setTicker(title) // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * BigPictureスタイルの通知を表示する
         */
        fun showBigPictureNotification(context: Context, channel: String, title: String, message: String) {
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dummy)
            val style = NotificationCompat.BigPictureStyle()
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Summary text")
                    .bigPicture(bitmap)
            val notification = NotificationCompat.Builder(context, channel)
                    .setStyle(style)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setTicker(title) // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * BigTextスタイルの通知を表示する
         */
        fun showBigTextNotification(context: Context, channel: String, title: String, message: String) {
            val style = NotificationCompat.BigTextStyle()
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Big text summary")
                    .bigText("This is long text. This is long text. This is long text. This is long text. This is long text.")
            val notification = NotificationCompat.Builder(context, channel)
                    .setStyle(style)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setTicker(title) // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * Inboxスタイルの通知を表示する
         */
        fun showInboxNotification(context: Context, channel: String, title: String, message: String) {
            val style = NotificationCompat.InboxStyle()
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Big text summary")
                    .addLine("This is line 1")
                    .addLine("This is line 2")
                    .addLine("This is line 3")
            val notification = NotificationCompat.Builder(context, channel)
                    .setStyle(style)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setTicker(title) // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }
    }
}
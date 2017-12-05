package io.github.kobakei.androidnotificationshowcase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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

        const val CHANNEL_ID_NORMAL = "channel_01"
        const val CHANNEL_ID_IMPORTANT = "channel_02"

        const val GROUP_KEY = "my_group"

        /**
         * 通知チャンネルを作成する
         */
        fun setUpNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel1 = NotificationChannel(CHANNEL_ID_NORMAL, "普通のチャンネル", NotificationManager.IMPORTANCE_DEFAULT)
                val channel2 = NotificationChannel(CHANNEL_ID_IMPORTANT, "重要なチャンネル", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    importance = NotificationManager.IMPORTANCE_HIGH
                }
                val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nm.createNotificationChannel(channel1)
                nm.createNotificationChannel(channel2)
            }
        }

        /**
         * 通知を表示する
         */
        fun showNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * 通知を表示する（アクションあり）
         */
        fun showNotificationWithActions(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val doneIntent = Intent(context, MainActivity::class.java)
            val donePendingIntent = PendingIntent.getActivity(context, 1, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val closeIntent = Intent(context, MainActivity::class.java)
            val closePendingIntent = PendingIntent.getActivity(context, 2, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", donePendingIntent)
                    .addAction(R.drawable.ic_action_close, "Close", closePendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * BigPictureスタイルの通知を表示する
         */
        fun showBigPictureNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.dummy)
            val style = NotificationCompat.BigPictureStyle()
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Summary text")
                    .bigPicture(bitmap)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setStyle(style)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * BigTextスタイルの通知を表示する
         */
        fun showBigTextNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val style = NotificationCompat.BigTextStyle()
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Big text summary")
                    .bigText("This is long text. This is long text. This is long text. This is long text. This is long text.")

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setStyle(style)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * Inboxスタイルの通知を表示する
         */
        fun showInboxNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val style = NotificationCompat.InboxStyle()
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Big text summary")
                    .addLine("This is line 1")
                    .addLine("This is line 2")
                    .addLine("This is line 3")

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setStyle(style)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * Messagingスタイルの通知を表示する
         */
        fun showMessagingNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val style = NotificationCompat.MessagingStyle("User Name")

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setStyle(style)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * DecoratedCustomViewスタイルの通知を表示する
         */
        fun showDecoratedCustomViewNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val style = NotificationCompat.DecoratedCustomViewStyle()

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setStyle(style)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * ヘッドアップ通知を表示する
         */
        fun showHeadUpNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_IMPORTANT) // Oreo: チャンネルの重要度
                    .setContentTitle("Head up title")
                    .setContentText("Head up message")
                    .setTicker("Head up ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH) // Pre-Oreo: 優先度
                    .setContentIntent(pendingIntent)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * ロックスクリーン通知を表示する
         */
        fun showLockScreenNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * バンドル通知を表示する
         */
        fun showBundledNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setGroupSummary(true)
                    .setGroup(GROUP_KEY)
                    .setContentTitle("Head up title")
                    .setContentText("Head up message")
                    .setTicker("Head up ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true) // 重要。各通知がすべて消えた時に、サマリーも自動で消える
                    .build()

            val notification1 = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setGroup(GROUP_KEY)
                    .setContentTitle("This is title 1")
                    .setContentText("This is message 1")
                    .setTicker("This is ticker 1") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

            val notification2 = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setGroup(GROUP_KEY)
                    .setContentTitle("This is title 2")
                    .setContentText("This is message 2")
                    .setTicker("This is ticker 2") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

            val notification3 = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setGroup(GROUP_KEY)
                    .setContentTitle("This is title 3")
                    .setContentText("This is message 3")
                    .setTicker("This is ticker 3") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, summaryNotification)
            nm.notify(2, notification1)
            nm.notify(3, notification2)
            nm.notify(4, notification3)
        }
    }
}
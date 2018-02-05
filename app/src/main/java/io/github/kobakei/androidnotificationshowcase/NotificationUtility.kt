package io.github.kobakei.androidnotificationshowcase

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.RemoteInput
import android.support.v4.content.ContextCompat
import android.widget.RemoteViews

/**
 * 通知関連のユーティリティ
 * Created by keisukekobayashi on 2017/11/09.
 */
class NotificationUtility {
    companion object {

        private const val CHANNEL_ID_NORMAL = "channel_01"
        private const val CHANNEL_ID_IMPORTANT = "channel_02"

        private const val GROUP_KEY = "my_group"

        const val KEY_REMOTE_INPUT = "remote_input"

        /**
         * 通知チャンネルを作成する
         */
        fun setUpNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel1 = NotificationChannel(CHANNEL_ID_NORMAL, "普通のチャンネル", NotificationManager.IMPORTANCE_DEFAULT)
                val channel2 = NotificationChannel(CHANNEL_ID_IMPORTANT, "重要なチャンネル", NotificationManager.IMPORTANCE_HIGH)
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
                    .setAutoCancel(true)
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
                    .setAutoCancel(true)
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
                    .setAutoCancel(true)
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
                    .setAutoCancel(true)
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

            val builder = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message") // ICSではこっちが表示される
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            val style = NotificationCompat.InboxStyle(builder)
                    .setBigContentTitle("Big content title")
                    .setSummaryText("Big text summary")
                    .addLine("This is line 1")
                    .addLine("This is line 2")
                    .addLine("This is line 3")
                    .setSummaryText("This is summary text")

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, style.build())
        }

        /**
         * Messagingスタイルの通知を表示する
         */
        fun showMessagingNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val style = NotificationCompat.MessagingStyle("User Name")
            style.addMessage("Message 1", 1L, "Sender 1")
            style.addMessage("Message 2", 1L, "Sender 2")
            style.addMessage("Message 3", 1L, "Sender 3")

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
                    .setAutoCancel(true)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * Custom viewの通知を表示する
         */
        fun showCustomViewNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val customView = RemoteViews(context.packageName, R.layout.custom_layout)

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setContent(customView)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notificationBuilder.build())
        }

        /**
         * Custom viewの通知を表示する (API 24~)
         */
        fun showCustomViewNotification2(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val customView = RemoteViews(context.packageName, R.layout.custom_layout)

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setCustomContentView(customView)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notificationBuilder.build())
        }

        /**
         * DecoratedCustomViewスタイルの通知を表示する
         */
        fun showDecoratedCustomViewNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val customView = RemoteViews(context.packageName, R.layout.custom_layout)

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setCustomContentView(customView)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            val style = NotificationCompat.DecoratedCustomViewStyle()
            style.setBuilder(notificationBuilder)
            val notification = style.build()

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * ダイレクトリプライの通知を表示する
         */
        fun showReplyNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            // API 24以降のみなので、分岐する
            val action = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val remoteInput = RemoteInput.Builder(KEY_REMOTE_INPUT)
                        .setLabel("Reply Label")
                        .build()

                val replyIntent = Intent(context, MyBroadcastReceiver::class.java)
                val replyPendingIntent = PendingIntent.getBroadcast(context, 1001, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                NotificationCompat.Action.Builder(R.drawable.ic_action_reply, "Reply", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build()
            } else {
                NotificationCompat.Action.Builder(R.drawable.ic_action_reply, "Reply", pendingIntent)
                        .build()
            }

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .addAction(action)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        fun showRepliedNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("Replied title")
                    .setContentText("Replied message")
                    .setTicker("Replied ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
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
                    .setPriority(NotificationCompat.PRIORITY_HIGH) // Pre-Oreo: 優先度を設定しないとHead upにならない
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * ロックスクリーン通知を表示する
         * 注意: Visibilityは通知チャンネルが優先される
         */
        fun showLockScreenNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val publicNotification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is public title")
                    .setContentText("This is public message")
                    .setTicker("This is public ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .build()

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is private title")
                    .setContentText("This is private message")
                    .setTicker("This is private ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PRIVATE) // private: ロック画面に表示するが、表示するのはpubicVersion
                    .setPublicVersion(publicNotification) // ロック画面で表示する通知
                    .setAutoCancel(true)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * バンドル通知を表示する
         * API 24から使える機能。それ以前は個別に通知を出す。
         */
        fun showBundledNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // サマリーは24以上でしか出さなくていい
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                        .setGroupSummary(true)
                        .setGroup(GROUP_KEY)
                        .setContentTitle("Summary title")
                        .setContentText("Summary message")
                        .setTicker("Summary ticker") // for legacy Android
                        .setSmallIcon(R.drawable.ic_notification)
                        .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                        .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true) // 重要。各通知がすべて消えた時に、サマリーも自動で消える
                        .build()
                nm.notify(1, summaryNotification)
            }

            // 各通知は前バージョン共通で出す
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

            nm.notify(2, notification1)
            nm.notify(3, notification2)
            nm.notify(4, notification3)
        }

        /**
         * 色付きの通知を作成する
         */
        fun createColorizedNotification(service: Service): Notification {
            val context = service.applicationContext
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            return NotificationCompat.Builder(context, CHANNEL_ID_NORMAL)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setColor(Color.DKGRAY)
                    .setColorized(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
        }
    }
}
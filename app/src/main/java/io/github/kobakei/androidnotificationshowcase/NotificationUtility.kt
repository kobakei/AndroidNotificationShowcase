package io.github.kobakei.androidnotificationshowcase

import android.annotation.SuppressLint
import android.app.*
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.RemoteInput
import android.support.v4.content.ContextCompat
import android.widget.RemoteViews
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * 通知関連のユーティリティ
 * Created by keisukekobayashi on 2017/11/09.
 */
class NotificationUtility {
    companion object {

        private const val GROUP_ID_1 = "group_1"
        private const val GROUP_ID_2 = "group_2"

        private const val CHANNEL_ID_1_1 = "channel_1_1"
        private const val CHANNEL_ID_1_2 = "channel_1_2"
        private const val CHANNEL_ID_2_1 = "channel_2_1"
        private const val CHANNEL_ID_2_2 = "channel_2_2"

        private const val GROUP_KEY = "my_group"

        const val KEY_REMOTE_INPUT = "remote_input"

        /**
         * 通知チャンネルを作成する
         */
        fun setUpNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val group1 = NotificationChannelGroup(GROUP_ID_1, "グループ1")
                val group2 = NotificationChannelGroup(GROUP_ID_2, "グループ2")

                val channel11 = NotificationChannel(CHANNEL_ID_1_1, "チャンネル1-1", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    group = GROUP_ID_1
                }
                val channel12 = NotificationChannel(CHANNEL_ID_1_2, "チャンネル1-2", NotificationManager.IMPORTANCE_HIGH).apply {
                    group = GROUP_ID_1
                }

                val channel21 = NotificationChannel(CHANNEL_ID_2_1, "チャンネル2-1", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    group = GROUP_ID_2
                }
                val channel22 = NotificationChannel(CHANNEL_ID_2_2, "チャンネル2-2", NotificationManager.IMPORTANCE_HIGH).apply {
                    group = GROUP_ID_2
                }

                val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                nm.createNotificationChannelGroups(listOf(group1, group2))
                nm.createNotificationChannels(listOf(channel11, channel12, channel21, channel22))
            }
        }

        /**
         * 通知を表示する
         */
        fun showNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                    //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_action_done))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build()
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notification)
        }

        /**
         * large iconつき通知を表示する
         * 本当はserviceとかで表示する
         */
        fun showLargeIconNotification(context: Context) {
            val largeIcon = downloadImage("https://secure.gravatar.com/avatar/cb416191cec5f85bd2ee7c56662f60e0")

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(largeIcon)
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    //.setContentTitle("This is title")
                    //.setContentText("This is message")
                    //.setTicker("This is ticker") // for legacy Android
                    .setStyle(style)
                    .setSmallIcon(R.drawable.ic_notification)
                    //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
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

            val builder = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setContentTitle("This is title")
                    .setContentText("This is message") // ICSではこっちが表示される
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setStyle(NotificationCompat.InboxStyle()
                            .setBigContentTitle("Big content title")
                            .setSummaryText("Big text summary")
                            .addLine("This is line 1")
                            .addLine("This is line 2")
                            .addLine("This is line 3")
                            .setSummaryText("This is summary text"))

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, builder.build())
        }

        /**
         * Mediaスタイルの通知を表示する
         */
        fun showMediaNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round)) // アートワーク
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentIntent(pendingIntent)
                    .setStyle(android.support.v4.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 2, 4)
                            .setShowCancelButton(true)
                            .setCancelButtonIntent(pendingIntent)
                            //.setMediaSession(session) // 本当はここでMediaSessionをセットする
                    )
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent) // アクションは最大5個まで

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, builder.build())
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, notificationBuilder.build())
        }

        /**
         * DecoratedMediaCustomViewスタイルの通知を表示する
         */
        fun showDecoratedMediaNotification(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val customView = RemoteViews(context.packageName, R.layout.custom_layout)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round)) // アートワーク
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentIntent(pendingIntent)
                    .setCustomContentView(customView)
                    .setStyle(android.support.v4.media.app.NotificationCompat.DecoratedMediaCustomViewStyle()
                            .setShowActionsInCompactView(0, 2, 4)
                            .setShowCancelButton(true)
                            .setCancelButtonIntent(pendingIntent)
                            //.setMediaSession(session) // 本当はここでMediaSessionをセットする
                    )
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent)
                    .addAction(R.drawable.ic_action_done, "Done", pendingIntent) // アクションは最大5個まで

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.notify(1, builder.build())
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
                    .setChoices(arrayOf("😀", "😎", "😇"))
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

        /**
         * ダイレクトリプライの通知を表示する(Android P)
         * memo: サポートライブラリがPersonなど色々対応してないので、普通のクラスを使う
         */
        @SuppressLint("NewApi")
        fun showReplyNotificationForP(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val remoteInput = android.app.RemoteInput.Builder(KEY_REMOTE_INPUT)
                    .setLabel("Reply Label")
                    .setChoices(arrayOf("Hello", "Bye", "Thanks"))
                    .build()

            val replyIntent = Intent(context, MyBroadcastReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(context, 1001, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val icon = Icon.createWithResource(context, R.drawable.ic_action_reply)
            val action = Notification.Action.Builder(icon, "Reply", replyPendingIntent)
                    .addRemoteInput(remoteInput)
                    .setSemanticAction(Notification.Action.SEMANTIC_ACTION_REPLY)
                    .build()

            val user1 = Notification.Person().apply {
                name = "Taro Yamada"
                setIcon(Icon.createWithContentUri(getImageUri(context)))
            }
            val user2 = Notification.Person().apply {
                name = "Jiro Yamada"
                setIcon(Icon.createWithContentUri(getImageUri(context)))
            }
            val user3 = Notification.Person().apply {
                name = "Saburo Yamada"
                setIcon(Icon.createWithContentUri(getImageUri(context)))
            }
            val style = Notification.MessagingStyle(user1)
            style.addMessage("Message 1", 1L, user1)
            style.addMessage("Message 2", 1L, user2)
            style.addMessage("Message 3", 1L, user3)
            style.isGroupConversation = true
            style.conversationTitle = "Conversation title"

            val notification = Notification.Builder(context, CHANNEL_ID_1_1)
                    .setStyle(style)
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_2) // Oreo: チャンネルの重要度
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

            val publicNotification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setContentTitle("This is public title")
                    .setContentText("This is public message")
                    .setTicker("This is public ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .build()

            val notification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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
                val summaryNotification = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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
            val notification1 = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notification2 = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            val notification3 = NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

            return NotificationCompat.Builder(context, CHANNEL_ID_1_1)
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

        /**
         * 色付きの通知を作成する
         */
        fun createColorizedNotification2(service: Service): Notification {
            val context = service.applicationContext
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            return NotificationCompat.Builder(context, CHANNEL_ID_1_1)
                    .setContentTitle("This is title")
                    .setContentText("This is message")
                    .setTicker("This is ticker") // for legacy Android
                    .setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher_round))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setColor(Color.CYAN)
                    .setColorized(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()
        }

        private fun downloadImage(address: String): Bitmap? {
            var bmp: Bitmap? = null
            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(address)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()
                val resp = urlConnection.responseCode
                when (resp) {
                    HttpURLConnection.HTTP_OK -> {
                        var inputStream: InputStream? = null
                        try {
                            inputStream = urlConnection.inputStream
                            bmp = BitmapFactory.decodeStream(inputStream)
                            inputStream!!.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } finally {
                            if (inputStream != null) {
                                inputStream.close()
                            }
                        }
                    }
                    HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    }
                    else -> {
                    }
                }
            } catch (e: Exception) {
                Log.d("debug", "downloadImage error")
                e.printStackTrace()
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
            }

            return bmp
        }

        private fun getImageUri(context: Context): Uri? {
            var uri: Uri? = null
            val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID))
                uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
            }
            cursor.close()
            return uri
        }
    }
}
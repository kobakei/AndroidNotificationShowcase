package io.github.kobakei.androidnotificationshowcase

import android.app.IntentService
import android.content.Context
import android.content.Intent

/**
 * フォアグラウンドサービスの例
 * 色付き通知を出すために必要
 *
 * Created by keisuke on 2017/12/16.
 */
class SampleForegroundService : IntentService("sample") {

    companion object {
        fun createIntent(context: Context): Intent =
                Intent(context, SampleForegroundService::class.java)
    }

    override fun onHandleIntent(p0: Intent?) {
        startForeground(1, NotificationUtility.createColorizedNotification(this))
        try {
            Thread.sleep(10 * 1000L)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }


}
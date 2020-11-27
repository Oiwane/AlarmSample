package io.github.oiwane.alarmsample.alarm

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.activity.MainActivity
import io.github.oiwane.alarmsample.activity.StopAlarmActivity
import io.github.oiwane.alarmsample.log.LogType
import io.github.oiwane.alarmsample.log.Logger
import java.util.*

/**
 * AlarmManagerからの通知を受け取る
 */
class AlarmBroadcastReceiver : BroadcastReceiver()
{
    var requestCode: String? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.write(LogType.INFO, "start")

        // リクエストIDを取得
        requestCode = intent!!.data.toString()
        Logger.write(LogType.INFO, "requestCode : $requestCode")
        if (requestCode == null)
            return

        // リクエストIDを設定
        val alarmIntent = createIntent(context)

        // 通知のタップ時の遷移先設定
        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(alarmIntent)
        val broadcast = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        // 通知の送信
        val pendingIntent = PendingIntent.getActivity(
            context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationBuilder = createNotifyBuilder(context!!, pendingIntent, broadcast) ?: return
        notify(context, notificationBuilder.build())

        Logger.write(LogType.INFO, "end")
    }

    /**
     * intentを作成
     * @param context コンテキスト
     * @return 通知に持たせるintent
     */
    private fun createIntent(context: Context?): Intent {
        val intent = Intent(context, StopAlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("ALARM_REQUEST_CODE", requestCode)
        return intent
    }

    /**
     * NotifyBuilderを作成
     * @param context コンテキスト
     * @param pendingIntent ペンディングインテント
     * @param broadcast 遷移先の設定をしたペンディングインテント
     */
    private fun createNotifyBuilder(
        context: Context, pendingIntent: PendingIntent?, broadcast: PendingIntent?
    ): NotificationCompat.Builder? {
        val calendar = Calendar.getInstance()
        val time = "%02d:%02d".format(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE))
        return NotificationCompat.Builder(context, AlarmConfigurator.CHANNEL_ID)
    //            .addAction(android.R.drawable.bottom_bar, "stop", broadcast)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Alarm")
            .setContentText(time)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)
            .setContentIntent(broadcast)
            .setAutoCancel(true)
    }

    /**
     * 通知する
     * @param context コンテキスト
     * @param notification 通知情報
     */
    private fun notify(context: Context, notification: Notification) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(createChannel(context));
        notificationManager.notify(R.string.app_name, notification)
    }

    /**
     * チャンネルを作成
     * @param context コンテキスト
     * @return チャンネル
     */
    private fun createChannel(context: Context): NotificationChannel {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channel = NotificationChannel(
            AlarmConfigurator.CHANNEL_ID,
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "test"
        channel.enableVibration(true)
        channel.canShowBadge()
        channel.enableLights(true)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        channel.setSound(defaultSoundUri, null)
        channel.setShowBadge(true)
        return channel
    }
}
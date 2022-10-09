package com.example.curriculum1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.curriculum1.ui.home.HomeFragment


class AlarmBroadcastReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "CLOCK_IN") {
            //获取状态通知栏管理
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel("NASDAQ", "日程提醒", IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
            val builder = NotificationCompat.Builder(context,"NASDAQ")
                .setContentTitle("上课提醒") //设置通知栏标题
                .setContentText("您有一门课程将要开始") //设置通知栏显示内容
                .setSmallIcon(R.drawable.curriculum)
                .setAutoCancel(true) //设置这个标志当用户单击面板就可以将通知取消
            val mIntent = Intent(context, HomeFragment::class.java) //绑定intent，点击图标能够进入某activity
            val mPendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(mPendingIntent)
            manager.notify(1, builder.build()) //绑定Notification，发送通知请求
        }
    }
}
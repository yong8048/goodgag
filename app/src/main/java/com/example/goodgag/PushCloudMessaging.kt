package com.example.goodgag

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import com.example.goodgag.activity.MainActivity
import com.example.goodgag.activity.SettingsActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.view.*

class PushCloudMessaging : FirebaseMessagingService() {

    private val TAG = "FirebaseService"


    override fun onNewToken(token: String) {
        Log.d(TAG, "new Token: $token")

        // 토큰 값을 따로 저장해둔다.
        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("token", token).apply()
        editor.commit()

        Log.i("로그: ", "성공적으로 토큰을 저장함")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if(View.inflate(this@PushCloudMessaging,R.layout.activity_settings, null).swPushSetting.isChecked){
            if(message.data.isNotEmpty()){
                Log.i("바디: ", message.data["body"].toString())
                Log.i("타이틀: ", message.data["title"].toString())
                SendNotification(message)
            }

            else {
                Log.i("수신에러: ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
                Log.i("data값: ", message.data.toString())
            }
        }
        else{
            Log.i("수신차단: ","Push 설정이 Off입니다.")
        }
    }
    private fun SendNotification(message : RemoteMessage){
        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        // 일회용 PendingIntent
        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임한다.
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남긴다. A-B-C-D-B => A-B
        val pendingIntent = PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)

        // 알림 채널 이름
        val channelId = getString(R.string.app_name)

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.image_splash_foreground) // 아이콘 설정
            .setContentTitle("고급유머 구경해") // 제목
            .setContentText("ㅁㄴㅇㅁㄴㅇ") // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri) // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요하다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
    }
}
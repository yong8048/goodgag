package com.example.goodgag.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import com.example.goodgag.adapter.MainListAdapter
import com.example.goodgag.Post
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.time.LocalDate
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {

    var postNum : Int = 1
    var date : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        throw Exception("SDK 버전이 낮습니다ㅋㅋ \n조선폰ㅋㅋ")
    }
    var postList = arrayListOf<Post>(
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "오근혁", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "오근혁", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "오근혁", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "오근혁", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "조명철", date),
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "이병욱", date),
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "이병욱", date),
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "이병욱", date),
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "이병욱", date),
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "이병욱", date),
        Post(postNum++.toString(), "이승용", date),
        Post(postNum++.toString(), "오근혁", date),
        Post(postNum++.toString(), "조명철", date),
//        Post("4", "오근혁", "2022./23"),
//        Post("5", "조명철", "2022.123"),
//        Post("6", "오근혁", "2022./23"),
//        Post("7", "조명철", "2022.123"),
//        Post("8", "오근혁", "2022./23"),
//        Post("9", "조명철", "2022.123"),
//        Post("10", "조명철", "2022./23"),
//        Post("11", "조명철", "2022.123"),
//        Post("12", "조명철", "2022./23"),
//        Post("13", "이승용", "2022.123"),
//        Post("14", "이병욱", "2022./23"),
//        Post("15", "이승용", "2022.123"),
//        Post("16", "이병욱", "2022./23"),
//        Post("17", "이승용", "2022.123"),
//        Post("18", "이병욱", "2022./23"),
//        Post("19", "이승용", "2022.123"),
//        Post("20", "이병욱", "2022./23"),
//        Post("21", "이승용", "2022.123"),
//        Post("22", "이병욱", "2022./23")
    )
    var listNumber : Int = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //토큰값 가져오기 -> 새기기 or 앱삭제 재설치 or 앱 데이터 소거시 토큰 초기화
        GetToken()

        lv_main.adapter = MainListAdapter(this, postList)
        //f
        ListViewHeightSize()

        btnListNext.setSingleLine()
        //////////////////////////////////////// 상단 새로고침
        tv_Main.setOnClickListener { Click_Refresh(it) }


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////하단바 클릭 이벤트
        //////////////////////////////////////// btnSettings 클릭
        btnSettings.setOnClickListener { Click_btnSettings(it) }

        //////////////////////////////////////// btnShare 클릭
        btnShare.setOnClickListener { Click_btnShare(it)  }

        btnBack.setOnClickListener{ }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////하단바 클릭 이벤트

        btnBefore.setOnClickListener{
            //20220623 LBW SingleTone Test
            Log.e("SEX","${UserManager.getinstance(this).Email.toString()}")
            Log.e("SEX","${UserManager.getinstance(this).Name.toString()}")
            Log.e("SEX","${UserManager.getinstance(this).Nickname.toString()}")
            Log.e("SEX","${UserManager.getinstance(this).Phonenumber.toString()}")
            Log.e("SEX","${UserManager.getinstance(this).Birthday.toString()}")
        }
        btnListNext.setOnClickListener{
            var post : Post = lv_main.adapter.getItem(2) as Post
            tvTest1.text = "넘버 : ${post.number}\n헤더 : ${post.header}\n날짜 : ${post.date}"
            llImage.visibility = GONE
        }

        lv_main.setOnItemClickListener { parent, view, position, id ->
            llImage.visibility = VISIBLE
            imgPost.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    private fun Click_btnSettings(view: View){
        var menuOption = PopupMenu(applicationContext, view)
        menuInflater?.inflate(R.menu.menu_option, menuOption.menu)
        menuOption.show()

        //////////////////////////////////////// menuOption 클릭
        menuOption.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener Click_MenuOption(it)
        }
    }
    private fun Click_MenuOption(menuItem: MenuItem) : Boolean{
        when (menuItem.itemId) {
            R.id.option_Search -> {
                return true
            }
            R.id.option_Download -> {
                return true
            }
            R.id.option_History -> {
                return true
            }
            R.id.option_Bookmark -> {
                return true
            }
            R.id.option_Mycomment -> {
                return true
            }
            R.id.option_Setting -> {
                startActivity(Intent(this,SettingsActivity::class.java))
                return true
            }
            else -> {
                return false
            }
        }
    }
    private fun Click_btnShare(view: View){
        var menuShare = PopupMenu(applicationContext, view)
        menuInflater?.inflate(R.menu.menu_share, menuShare.menu)
        menuShare.show()
    }

    private fun GetToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener {
                if (!it.isSuccessful) {
                    Log.d("FCM Log", "getInstanceId failed", it.exception)
                    return@OnCompleteListener
                }
                val token : String = it.result
                Log.d("FCM Log", "FCM 토큰: $token")
//                Toast.makeText(this@MainActivity, token, Toast.LENGTH_SHORT).show()
            })
    }

    private fun Click_Refresh(view : View){

    }

    private fun ListViewHeightSize(){
        var listAdapter : ListAdapter = lv_main.adapter
        var totalHeight: Int = 0

        for (i in 0 until listAdapter.count) {
            var listItem: View = listAdapter.getView(i, null, lv_main);
            listItem.measure(0,0)
            totalHeight += listItem.measuredHeight + 4
        }
        var params :ViewGroup.LayoutParams = lv_main.layoutParams

        params.height = totalHeight + 4
        lv_main.layoutParams = params

        lv_main.requestLayout()
    }
}

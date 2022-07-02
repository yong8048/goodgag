package com.example.goodgag.activity

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.example.goodgag.adapter.MainListAdapter
import com.example.goodgag.Post
import com.example.goodgag.R
import com.example.goodgag.user.UserManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.main_lv_item.*
import java.io.ByteArrayInputStream
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {

    var postNum : Int = 1
    //시간 담아두는 방식 - 날짜 저장 / datetime은 시간까지
    var date : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        throw Exception("SDK 버전이 낮습니다ㅋㅋ \n조선폰ㅋㅋ")
    }
    var postList = arrayListOf<Post>(
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
        Post(date, "이승용", postNum++.toString(), "z","z"),
    )
    var listNumber : Int = 0

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //토큰값 가져오기 -> 새기기 or 앱삭제 재설치 or 앱 데이터 소거시 토큰 초기화
        GetToken()

        lv_main.adapter = MainListAdapter(this, postList)

        ListViewHeightSize()

        //////////////////////////////////////// 상단 새로고침
        tv_Main.setOnClickListener { Click_Refresh(it) }


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////하단바 클릭 이벤트
        //////////////////////////////////////// 버튼 클릭 뒤로가기
        btnBack.setOnClickListener{ Click_btnBack(it)  }
        //////////////////////////////////////// 버튼 클릭이전 게시물로 이동
        btnBefore.setOnClickListener { Click_btnBefore(it)  }
        //////////////////////////////////////// 버튼 클릭 다음 게시물로 이동
        btnNext.setOnClickListener { Click_btnNext(it)  }
        //////////////////////////////////////// 버튼 클릭 작성한 댓글 보여줌
        btnComment.setOnClickListener { Click_btnComment(it)  }
        //////////////////////////////////////// 버튼 클릭 다음 게시물로 이동
        btnRefresh.setOnClickListener { Click_btnRefresh(it)  }
        //////////////////////////////////////// 버튼 클릭 공유
        btnShare.setOnClickListener { Click_btnShare(it)    }
        //////////////////////////////////////// 버튼 클릭 랜덤 게시물
        btnRandom.setOnClickListener { Click_btnRandom(it)  }
        //////////////////////////////////////// 버튼 클릭 설정
        btnSettings.setOnClickListener { Click_btnSettings(it) }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////하단바 클릭 이벤트


        btnListNext.setOnClickListener{
            var post : Post = lv_main.adapter.getItem(2) as Post
            tvTest1.text = "넘버 : ${post.number}\n헤더 : ${post.header}\n날짜 : ${post.date}"
            llImage.visibility = GONE
        }


        lv_main.setOnItemClickListener { parent, view, position, id ->
            llImage.visibility = VISIBLE
            val clickedListNum : Int = postList[position].getNumber().toInt()
            GetImagePost(clickedListNum)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun Click_btnBack(view : View){
        if(imgPost != null){

        }
        else{

        }
    }

    private fun Click_btnBefore(view: View){
        if(llImage.visibility != GONE){
            Toast.makeText(this@MainActivity,"게시물 있음", Toast.LENGTH_SHORT).show()
        }
        else{

        }
    }

    private fun Click_btnNext(view: View){

    }

    private fun Click_btnComment(view: View){

    }

    private fun Click_btnRefresh(view: View){

    }

    private fun Click_btnShare(view: View){
        var menuShare = PopupMenu(applicationContext, view)
        menuInflater?.inflate(R.menu.menu_share, menuShare.menu)
        menuShare.show()
    }

    private fun Click_btnRandom(view: View){

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
        imgPost.setImageResource(0)
        llImage.visibility = GONE
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

    private fun GetImagePost(num : Int) {
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storagePath: StorageReference = storage.reference.child("$num.jpg")
        if (storagePath == null)
            Toast.makeText(this, "데이터 없음", Toast.LENGTH_SHORT).show()
        else {
            storagePath.downloadUrl.addOnCompleteListener(OnCompleteListener {
                if(it.isSuccessful) {
                    Log.i("downLoad", "${it.result}")
                    Glide.with(this).load(it.result).into(imgPost)
                }
                val httpsReference = storage.getReferenceFromUrl(storagePath.toString())
            })
        }
    }


//    private  fun GetPostInfo(){
//        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
//        val myRef : DatabaseReference = database.getReference("POST")
//        myRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//
//    }

}

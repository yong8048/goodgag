package com.example.goodgag.activity

import android.content.Intent
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.example.goodgag.adapter.MainListAdapter
import com.example.goodgag.Post
import com.example.goodgag.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    var postNum : Int = 1
    //시간 담아두는 방식 - 날짜 저장 / datetime은 시간까지
    var
            date : String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toString()
    } else {
        throw Exception("SDK 버전이 낮습니다ㅋㅋ \n조선폰ㅋㅋ")
    }
    var postList = Array<Post?>(15) { null }
    var listNumber : Int = 0

    var storage : FirebaseStorage = FirebaseStorage.getInstance()
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val intent : Intent = getIntent()
//        var PostClass = intent.getParcelableArrayExtra("Post")

        CoroutineScope(Dispatchers.Main).launch {
            asasdd()
        }

//        GetPostArrayInfo()
        //토큰값 가져오기 -> 새기기 or 앱삭제 재설치 or 앱 데이터 소거시 토큰 초기화
        GetToken()
        //상단고정
        tv_Main.requestFocus()





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
            val clickedListNum : Int = postList[position]!!.number!!.toInt()
            GetPostInfo(clickedListNum)
            GetImagePost(clickedListNum)

        }
    }

    private fun Init(){

    }


    override fun onDestroy() {
        super.onDestroy()
    }

    private fun GetPostArrayInfo(){
        val database : DatabaseReference = Firebase.database.reference
        val databaseRef = database.child("POSTNUMBER")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val postNumebr: Int = snapshot.value.toString().toInt()
//                for(postNumber until postNumber-1)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun Click_btnBack(view : View){
//        if(imgPost != null){
//
//        }
//        else{
//
//        }
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
//        imgPost.setImageResource(0)
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
        llImageView.removeAllViews()
        val storagePath : StorageReference = storage.reference.child("$num")

        // Storage에서 게시물 가져오기
        storagePath.listAll().addOnSuccessListener {
            if(it.items.isNotEmpty()) {
                llImage.visibility = VISIBLE
                for (data in it.items) {
                    // Video인지 Image 인지 메타데이터 가져오기
                    data.metadata.addOnSuccessListener { metadata ->
                        Log.i("DownLoad", "${metadata.contentType}")
                        if (metadata.contentType!!.contains("image")) {
                            val imageview = ImageView(this)
                            imageview.scaleType = ImageView.ScaleType.FIT_CENTER
                            llImageView.addView(imageview)
                            data.downloadUrl.addOnCompleteListener(OnCompleteListener {
                                if (it.isSuccessful) {
                                    Glide.with(this).load(it.result).into(imageview)
                                }
                            })
                        } else {
                            val videoView = VideoView(this@MainActivity)
                            var Layout = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                1000
                            )
                            Layout.gravity = Gravity.CENTER
                            videoView.layoutParams = Layout
                            llImageView.addView(videoView)
                            data.downloadUrl.addOnCompleteListener(OnCompleteListener {
                                if (it.isSuccessful) {
                                    videoView.visibility = VISIBLE
                                    videoView.setVideoURI(it.result)
                                    videoView.setMediaController(MediaController(this))
                                    videoView.start()
                                }
                            })
                        }
                    }
                }
            }
            else{
                llImage.visibility = GONE
                Toast.makeText(this,"게시물 없음",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun  GetPostInfo(num : Int){
        val database : DatabaseReference = Firebase.database.reference
        val databaseRef = database.child("POST").child("POST_$num")
        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val postData = Array<String>(5) { "" }
                var n : Int = 0
                for(i in snapshot.children){
                    postData[n++] = i.value.toString()
                }
                val post: Post = Post(
                    postData[UploadPostActivity.POSTINFO.DATE.num],
                    postData[UploadPostActivity.POSTINFO.HEADER.num],
                    postData[UploadPostActivity.POSTINFO.NUMBER.num],
                    postData[UploadPostActivity.POSTINFO.SOURCE.num],
                    postData[UploadPostActivity.POSTINFO.UPLOADER.num]
                )
                llImage_header.text = post.header
                llImage_data.text = "작성자 " + post.uploader + " | No." + post.number + " | " + post.date
                llImage_source.text = "출처 또는 퍼온 곳 : + " + post.source
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun asd(){
        val database : DatabaseReference = Firebase.database.reference
        val databaseRef = database.child("POSTNUMBER")
        var postIndex: Int = 0

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastPostNum: Int = snapshot.value.toString().toInt()
                var res: Int = 0
                for (_lastPostNum in lastPostNum downTo lastPostNum - 14) {
                    val postref = database.child("POST").child("POST_$_lastPostNum")
                    postref.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            var index: Int = 0
                            var _data = Array<String>(5) { "" }
                            for (data in snapshot.children) {
                                _data[index++] = data.value.toString()
                            }
                            val member = Post(_data[0], _data[1], _data[2], _data[3], _data[4])
                            postList[postIndex++] = member

                        }

                        override fun onCancelled(error: DatabaseError) {
                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun a2(){
        lv_main.adapter = MainListAdapter(this@MainActivity, postList)
    }
    private fun a3(){
        ListViewHeightSize()
    }

    suspend fun asasdd() = coroutineScope {
        val a = async {
            asd()
        }.await()
        val b = async {
            a2()
        }.await()
        val c = async {
            a3()
        }.await()

    }

}

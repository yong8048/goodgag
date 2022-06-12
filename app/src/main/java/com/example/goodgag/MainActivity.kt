package com.example.goodgag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {

//    var postList = arrayListOf<Post>()
    var postList = arrayListOf<Post>(
    Post("1","이승용","2022.123"),
    Post("2","이병욱","2022./23")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postAdapter = MainListAdapter(this, postList)
        lv_main.adapter = postAdapter
    }
}
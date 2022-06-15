package com.example.goodgag.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
import android.widget.Toast
import com.example.goodgag.adapter.MainListAdapter
import com.example.goodgag.Post
import com.example.goodgag.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*


class MainActivity : AppCompatActivity() {

    var postList = arrayListOf<Post>(
        Post("1", "이승용", "2022.123"),
        Post("2", "오근혁", "2022./23"),
        Post("3", "조명철", "2022.123"),
        Post("4", "오근혁", "2022./23"),
        Post("5", "조명철", "2022.123"),
        Post("6", "오근혁", "2022./23"),
        Post("7", "조명철", "2022.123"),
        Post("8", "오근혁", "2022./23"),
        Post("9", "조명철", "2022.123"),
        Post("10", "조명철", "2022./23"),
        Post("11", "조명철", "2022.123"),
        Post("12", "조명철", "2022./23"),
        Post("13", "이승용", "2022.123"),
        Post("14", "이병욱", "2022./23"),
        Post("15", "이승용", "2022.123"),
        Post("16", "이병욱", "2022./23"),
        Post("17", "이승용", "2022.123"),
        Post("18", "이병욱", "2022./23"),
        Post("19", "이승용", "2022.123"),
        Post("20", "이병욱", "2022./23"),
        Post("21", "이승용", "2022.123"),
        Post("22", "이병욱", "2022./23"),
    )
    var b : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("sylee", "메인액티비티 동작")
        lv_main.adapter = MainListAdapter(this, postList)

        btnListNext.setSingleLine()

        var menuOption = PopupMenu(applicationContext, btnSettings)
        menuInflater?.inflate(R.menu.menu_option, menuOption.menu)

        btnSettings.setOnClickListener {
            menuOption.show()
        }
        menuOption.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_Setting -> {
                    startActivity(Intent(this,OptionActivity::class.java))
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }
        }
        btnShare.setOnClickListener {
            var menuShare = PopupMenu(applicationContext, it)
            menuInflater?.inflate(R.menu.menu_share, menuShare.menu)
            menuShare.show()
        }
        tvkidding.setOnClickListener {
            tvkidding.text = "ㅋㅋ이승용 볍신ㅋㅋ"
            Toast.makeText(this@MainActivity,"asd",Toast.LENGTH_SHORT).show()
        }
    }
    private fun Click_MenuItem(){

    }
}

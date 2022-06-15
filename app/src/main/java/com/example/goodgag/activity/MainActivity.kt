package com.example.goodgag.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.PopupMenu
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

        lv_main.adapter = MainListAdapter(this, postList)

        lv_main.onItemClickListener = SimpleListListener()

        btnListNext.setSingleLine()

        var menuOption = PopupMenu(applicationContext, btnSettings)
        menuInflater?.inflate(R.menu.menu_option, menuOption.menu)

        btnSettings.setOnClickListener {
            menuOption.show()
        }
        menuOption.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.option_Setting -> {
                    setContentView(R.layout.activity_settings)
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
    }

    inner class SimpleListListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if(b){
                tv_Main.text = "조명철볍신ㅋㅋ"
            }
            else{
                tv_Main.text = "ㅋㅋ볍신조명철"
            }
            b = !b
        }

    }
}
package com.example.goodgag.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
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

        lv_main.adapter = MainListAdapter(this, postList)

        btnListNext.setSingleLine()

        //////////////////////////////////////// btnSettings 클릭
        btnSettings.setOnClickListener {
            CLick_btnSettings(it)
        }

        //////////////////////////////////////// btnShare 클릭
        btnShare.setOnClickListener {
            Click_btnShare(it)
        }
    }
    private fun CLick_btnSettings(view: View){
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
                startActivity(Intent(this,OptionActivity::class.java))
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
}

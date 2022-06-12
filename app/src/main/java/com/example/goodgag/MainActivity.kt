package com.example.goodgag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {

    var postList = arrayListOf<Post>(
    Post("1","이승용","2022.123"),
    Post("2","이병욱","2022./23")
    )
    var b : Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postAdapter = MainListAdapter(this, postList)
        lv_main.adapter = postAdapter

        var btnBackListener = SimpleListListener()
        lv_main.onItemClickListener = btnBackListener

        btnBack.setOnClickListener{
            btnBefore.setEnabled(false)
        }
    }

    inner class SimpleListListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if(b == true){
                tv_Main.text = "이승용볍신ㅋㅋ"
            }
            else{
                tv_Main.text = "ㅋㅋ볍신이승용"
            }
            b = !b
        }

    }
}
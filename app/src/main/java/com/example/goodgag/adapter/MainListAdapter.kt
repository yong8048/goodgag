package com.example.goodgag.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.goodgag.Post
import com.example.goodgag.R
import kotlinx.android.synthetic.main.main_lv_item.view.*

class MainListAdapter(val context: Context, val postList: Array<Post?>) : BaseAdapter() {
    override fun getCount(): Int {
        return 15
    }

    override fun getItem(position: Int): Post? {
        return postList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.main_lv_item, null)

        view.tv_number.text = postList[position]?.number
        view.tv_header.text = postList[position]?.header
        view.tv_date.text = postList[position]?.date

        return view
    }

}
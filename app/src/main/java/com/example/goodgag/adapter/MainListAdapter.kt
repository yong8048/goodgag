package com.example.goodgag.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.goodgag.Post
import com.example.goodgag.R

class MainListAdapter (val context: Context, val postList: ArrayList<Post>) : BaseAdapter() {
    override fun getCount(): Int {
        return 15
    }

    override fun getItem(position: Int): Any {
        return postList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view : View = LayoutInflater.from(context).inflate(R.layout.main_lv_item, null)

        val postNumber = view.findViewById<TextView>(R.id.tv_number)
        val postHeader = view.findViewById<TextView>(R.id.tv_header)
        val postDate = view.findViewById<TextView>(R.id.tv_date)



        val post = postList[position]
        postNumber.text = post.number
        postHeader.text = post.header
        postDate.text = post.date

        return view
    }

}
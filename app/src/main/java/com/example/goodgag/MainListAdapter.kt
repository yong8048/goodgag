package com.example.goodgag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MainListAdapter (val context: Context, val postList: ArrayList<Post>) : BaseAdapter() {
    override fun getCount(): Int {
        TODO("Not yet implemented")
        return postList.size
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
        return postList[position]
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")

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
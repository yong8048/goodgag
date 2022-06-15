package com.example.goodgag.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.goodgag.R

class SignUpSpinnerAdapter(val context: Context, val EmailList: ArrayList<String>) : BaseAdapter() {
    override fun getCount(): Int {
        return EmailList.size
    }

    override fun getItem(position: Int): Any {
        return EmailList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view : View = LayoutInflater.from(context).inflate(R.layout.activity_signup, null)

        return view
    }


}
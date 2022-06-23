package com.example.goodgag.user

import android.content.Context
import android.util.Log


class UserManager private constructor(context : Context){

    private var Name : String = ""
    private var Nickname : String = ""
    private var Email : String = ""
    private var Phonenumber : String = ""
    private var Birthday : String = ""

    companion object{
        @Volatile private var instance : UserManager? = null

        fun getinstance(context: Context) : UserManager =
            instance ?: synchronized(this){
                instance ?: UserManager(context).also {
                    instance = it
                }
            }
    }

    fun registUser(_birthday : String, _email : String, _name : String, _nickname : String, _phonnumber : String){
        Name = _name
        Nickname = _nickname
        Email = _email
        Phonenumber = _phonnumber
        Birthday = _birthday
    }
}
package com.example.goodgag.user

import android.content.Context


class UserManager private constructor(context : Context){

    var Name : String = ""
    var Nickname : String = ""
    var Email : String = ""
    var Phonenumber : String = ""
    var Birthday : String = ""

    companion object{
        @Volatile private var instance : UserManager? = null

        fun getinstance(context: Context) : UserManager =
            instance ?: synchronized(this){
                instance ?: UserManager(context).also {
                    instance = it
                }
            }
        fun ExistInstance() : Boolean{
            return instance != null
        }
    }


    fun registUser(Birthday : String, Email : String, Name : String, Nickname : String, Phonenumber : String){
        this.Name = Name
        this.Nickname = Nickname
        this.Email = Email
        this.Phonenumber = Phonenumber
        this.Birthday = Birthday
    }
    fun clearUser(){
        this.Name = ""
        this.Nickname = ""
        this.Email = ""
        this.Phonenumber = ""
        this.Birthday = ""

        instance = null
    }
}
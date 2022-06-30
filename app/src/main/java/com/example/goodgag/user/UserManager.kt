package com.example.goodgag.user

import android.content.Context
import java.security.Permission


class UserManager private constructor(context : Context){

    var Birthday : String? = null
    var Name : String? = null
    var Nickname : String? = null
    var Email : String? = null
    var Phonenumber : String? = null
    var Permission : Boolean? = null

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


    fun registUser(Birthday : String, Email : String, Name : String, Nickname : String, Phonenumber : String, Permission : Boolean = false){
        this.Birthday = Birthday
        this.Email = Email
        this.Name = Name
        this.Nickname = Nickname
        this.Phonenumber = Phonenumber
        this.Permission = Permission
    }
    fun clearUser(){
        this.Birthday = null
        this.Email = null
        this.Name = null
        this.Nickname = null
        this.Phonenumber = null
        this.Permission = null

        instance = null
    }
}
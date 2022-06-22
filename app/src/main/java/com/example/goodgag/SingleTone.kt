package com.example.goodgag

import com.example.goodgag.data.UserInfo

class Singleton private constructor(val userInfo : UserInfo) {

    companion object {
        @Volatile private var INSTANCE : Singleton? = null

        fun getInstance(userInfo: UserInfo) : Singleton =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Singleton(userInfo = UserInfo()).also { INSTANCE = it }
            }
    }

}
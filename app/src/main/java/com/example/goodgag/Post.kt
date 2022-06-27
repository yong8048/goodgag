package com.example.goodgag

class Post (val number: String, val header: String, val date: String){

    @JvmName("getNumber1")
    fun getNumber() : String{
        return number
    }
    @JvmName("getHeader1")
    fun getHeader() : String{
        return header
    }
    @JvmName("getDate1")
    fun getDate() : String{
        return date
    }

}
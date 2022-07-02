package com.example.goodgag

class Post (val date: String, val header: String, val number: String, val source: String, val uploader: String){

    @JvmName("getDate1")
    fun getDate() : String{
        return date
    }
    @JvmName("getHeader1")
    fun getHeader() : String{
        return header
    }
    @JvmName("getNumber1")
    fun getNumber() : String{
        return number
    }
    @JvmName("getSource1")
    fun getSource() : String{
        return source
    }
    @JvmName("getUploader1")
    fun getUploader() : String{
        return uploader
    }
}
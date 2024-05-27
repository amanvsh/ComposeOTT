package com.example.ott.repository

import com.example.ott.model.ContentList
import com.example.ott.utils.Constant
import com.google.gson.Gson
import javax.inject.Inject


class ContentListRepository @Inject constructor() {

     fun getContentList(page: Int): ContentList {

         val gson = Gson()
         val pageNum: String = when (page) {
             1 -> Constant.PAGE_ONE
             2 -> Constant.PAGE_TWO
             else -> {
                 Constant.PAGE_THREE
             }
         }
         return gson.fromJson(pageNum, ContentList::class.java)

     }

}
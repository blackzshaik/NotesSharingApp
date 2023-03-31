package com.notessharingapp.model

import java.util.*


data class Notes (var notesId:String = "",
                  var authorNickName:String = "",
                  var authorId:String = "",
                  var title: String = "",
                  var note: String = "",
                  var bgColor: String = "#F44336",
                  var public: Boolean =false,
                  var archive: Boolean =false,
                  var createdAt: Date = Date()
    //canbe enabled in the future
//                  val tags:List<String> = emptyList(),
//                  val labels : List<String> = emptyList(),
                  )
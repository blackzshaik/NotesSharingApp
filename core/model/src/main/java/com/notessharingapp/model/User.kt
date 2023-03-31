package com.notessharingapp.model

data class User (val uid:String = "",
                 var fullName:String = "",
                 var nickName:String = "",
                 var email:String = "",
                 var notes:List<String> = emptyList(),
                 var sharedNotesCount:Int = 0
)
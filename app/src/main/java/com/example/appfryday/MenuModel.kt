package com.example.appfryday

data class MenuModel(
    val Menu: String = "",
    val Price: Int = 0,
    val CategoryID: String = "",
    val Details: String = "",
    val imgURL: String = "",
    val stockQuantity: Int = 0,
    val recommend: Boolean = false
)

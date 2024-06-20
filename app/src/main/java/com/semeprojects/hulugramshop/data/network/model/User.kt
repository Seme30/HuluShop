package com.semeprojects.hulugramshop.data.network.model

data class User(
    val uid: String,
    val email: String,
    val displayName: String? = null
)
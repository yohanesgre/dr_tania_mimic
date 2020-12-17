package com.neurafarm.drtaniamimic.data.models

data class AccountModel(
    val email: String,
    val username: String,
    val name: String,
    var password: String,
    var city: String,
    val occupation: String,
    val phoneNumber: String,
)
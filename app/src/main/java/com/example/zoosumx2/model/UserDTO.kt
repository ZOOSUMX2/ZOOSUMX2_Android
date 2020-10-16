package com.example.zoosumx2.model

import java.sql.Timestamp

data class UserDTO(
    val addressCity: String,
    val addressRegion: String,
    val creationTimestamp: String,
    val exp: Number,
    val islandName: String,
    val lastSignInTimestamp: Timestamp,
    val level: Number,
    val nickname: String,
    val profilePhoto: String,
    val registeredBy: String,
    val rewardPoint: String,
    val uid: String,
    val withdraw: String
)
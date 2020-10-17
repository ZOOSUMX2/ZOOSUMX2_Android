package com.example.zoosumx2.model

import java.sql.Timestamp

data class UserDTO(
    val addressCity: String? = null,
    var addressRegion: String? = null,
    val creationTimestamp: Timestamp? = Timestamp(0),
    var exp: Number? = 0,
    var islandName: String? = null,
    val lastSignInTimestamp: Timestamp? = Timestamp(0),
    val level: Number? = 0,
    var nickname: String? = null,
    val profilePhoto: String? = null,
    val registeredBy: String? = null,
    val rewardPoint: String? = null,
    var uid: String? = null,
    val withdraw: String? = null) {

}
package com.example.zoosumx2.model

import java.sql.Timestamp

data class UserDTO(
    var addressCity: String? = null,
    var addressRegion: String? = null,
    var creationTimestamp: Timestamp? = Timestamp(0),
    var exp: Number? = 0,
    var islandName: String? = null,
    var lastSignInTimestamp: Timestamp? = Timestamp(0),
    var level: Number? = 0,
    var nickname: String? = null,
    var profilePhoto: String? = null,
    var registeredBy: String? = null,
    var rewardPoint: Number? = 0,
    var uid: String? = null,
    var withdraw: String? = null
) {

}
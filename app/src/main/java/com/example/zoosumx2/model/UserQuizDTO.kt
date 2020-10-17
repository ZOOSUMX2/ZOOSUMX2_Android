package com.example.zoosumx2.model

import java.sql.Timestamp

data class UserQuizDTO(
    var answer: Boolean? = true,
    var creationTimestamp: Timestamp? = Timestamp(0),
    var creator: String? = null,
    var option1: String? = null,
    var option2: String? = null,
    var title: String? = null,
) {

}
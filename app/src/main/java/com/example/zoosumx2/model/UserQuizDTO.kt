package com.example.zoosumx2.model

import java.sql.Timestamp

data class UserQuizDTO(
    var creationTimestamp: Timestamp? = Timestamp(0),
    var creator: String? = null,
    var correctAns: String? = null,
    var creatorId: String? = null,
    var wrongAns: String? = null,
    var title: String? = null,
    var adopt: Boolean? = false
) {

}
package com.example.zoosumx2.model

import java.sql.Timestamp

data class MailboxDTO(
    var addressCity: String? = null,
    var addressRegion: String? = null,
    var contents: String? = null,
    //var issueDate: Timestamp? = Timestamp(0),
    var createdBy: String? = null,
    var title: String? = null,
    var summary: String? = null
) {

}
package com.zoosumzoosum.zoosumx2.model

data class MailboxDTO(
    var addressCity: String? = null,
    var addressRegion: String? = null,
    var contents: String? = null,
    var issueDate: String? = null,
    var createdBy: String? = null,
    var title: String? = null,
    var summary: String? = null
) {

}
package com.example.zoosumx2.model

data class NewsDTO(
    var addressCity: String? = null,
    var addressRegion: String? = null,
    var contents: String? = null,
    var createdBy: String? = null,
    var issueDate: Long? = null,
    var title: String? = null
)
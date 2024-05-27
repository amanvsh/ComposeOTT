package com.example.ott.model

data class Page(
    val contentItems: ContentItems,
    val pageNum: String,
    val pageSize: String,
    val nextPage: String,
    val title: String,
    val totalContentItems: String
)
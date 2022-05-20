package com.example.machinetests.model

import com.google.gson.annotations.SerializedName


data class ArticlesItem(
        @SerializedName("author") var author: String,
        @SerializedName("description") var description: String,
        @SerializedName("title") var title: String,
        @SerializedName("header") var header: Boolean
)
package com.example.tmobileproject.data.remote.model


import com.google.gson.annotations.SerializedName

data class HomeFeedPage(
    @SerializedName("cards")
    val cards: List<Card>
)
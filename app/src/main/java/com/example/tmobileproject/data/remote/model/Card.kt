package com.example.tmobileproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class Card(
    val card: CardX,
    @SerializedName("card_type")
    val cardType: String
)
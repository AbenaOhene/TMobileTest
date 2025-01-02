package com.example.tmobileproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class Attributes(
    val font: Font,
    @SerializedName("text_color")
    val textColor: String
)
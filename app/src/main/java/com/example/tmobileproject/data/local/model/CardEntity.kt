package com.example.tmobileproject.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tmobileproject.data.remote.model.Card
import com.example.tmobileproject.data.remote.model.CardX
import com.google.gson.annotations.SerializedName
@Entity
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val card: CardX,
    @SerializedName("card_type")
    val cardType: String
)

fun CardEntity.toCard(): Card {
    return Card(
        card = this.card,
        cardType = this.cardType,
    )
}

fun Card.toCardDto(): CardEntity {
    return CardEntity(
        id = 0,
        card = this.card,
        cardType = this.cardType
    )
}

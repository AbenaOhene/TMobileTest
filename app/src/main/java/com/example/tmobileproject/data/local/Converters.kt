package com.example.tmobileproject.data.local

import androidx.room.TypeConverter
import com.example.tmobileproject.data.remote.model.CardX
import com.example.tmobileproject.data.remote.model.Description
import com.example.tmobileproject.data.remote.model.Image
import com.example.tmobileproject.data.remote.model.Size
import com.example.tmobileproject.data.remote.model.Title
import com.example.tmobileproject.data.remote.model.Attributes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.ByteBuffer

// TypeConverter to convert nested data classes to a format that can be stored (e.g., JSON)
class Converters {

    private val gson = Gson()

    // CardX
    @TypeConverter
    fun fromCardX(cardX: CardX?): String? {
        return cardX?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toCardX(cardXJson: String?): CardX? {
        return cardXJson?.let {
            val type = object : TypeToken<CardX>() {}.type
            gson.fromJson(it, type)
        }
    }

    // Description
    @TypeConverter
    fun fromDescription(description: Description?): String? {
        return description?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toDescription(descriptionJson: String?): Description? {
        return descriptionJson?.let {
            val type = object : TypeToken<Description>() {}.type
            gson.fromJson(it, type)
        }
    }

    // Image
    @TypeConverter
    fun fromImage(image: Image?): String? {
        return image?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toImage(imageJson: String?): Image? {
        return imageJson?.let {
            val type = object : TypeToken<Image>() {}.type
            gson.fromJson(it, type)
        }
    }

    // Size
    @TypeConverter
    fun fromSize(size: Size?): String? {
        return size?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toSize(sizeJson: String?): Size? {
        return sizeJson?.let {
            val type = object : TypeToken<Size>() {}.type
            gson.fromJson(it, type)
        }
    }

    // Title
    @TypeConverter
    fun fromTitle(title: Title?): String? {
        return title?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toTitle(titleJson: String?): Title? {
        return titleJson?.let {
            val type = object : TypeToken<Title>() {}.type
            gson.fromJson(it, type)
        }
    }

    // Attributes
    @TypeConverter
    fun fromAttributes(attributes: Attributes?): String? {
        return attributes?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAttributes(attributesJson: String?): Attributes? {
        return attributesJson?.let {
            val type = object : TypeToken<Attributes>() {}.type
            gson.fromJson(it, type)
        }
    }

}

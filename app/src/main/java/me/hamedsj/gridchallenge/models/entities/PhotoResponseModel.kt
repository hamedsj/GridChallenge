package me.hamedsj.gridchallenge.models.entities


import com.google.gson.annotations.SerializedName

data class PhotoResponseModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("urls")
    val urls: UrlsResponseModel,
    @SerializedName("color")
    val color: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)
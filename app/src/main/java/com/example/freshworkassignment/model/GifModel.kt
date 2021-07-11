package com.example.freshworkassignment.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GifModel (
    @SerializedName("data") var gifData : ArrayList<GifData>) : Serializable

data class GifData (
    @SerializedName("type") var type : String,
    @SerializedName("id") var gifId : String,
    @SerializedName("title") var title : String,
    @SerializedName("images") var images : ImageVariants,
    var isFavourite : Boolean = false) : Serializable

data class ImageVariants (
    @SerializedName("original") var original : Variant) : Serializable

data class Variant (
    @SerializedName("height") var height : String,
    @SerializedName("width") var width : String,
    @SerializedName("url") var gifUrl : String) : Serializable

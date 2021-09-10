package com.android.flowerart.logic.bean

import com.google.gson.annotations.SerializedName

data class PlantInfo(
    val statusCode: String,
    val desc: String,
    val result: InfoResult
)

data class InfoResult(
    val alias: String,
    val binomiaNomenclature: String,
    val botanicalName: String,
    val careKnowledge: String,
    @SerializedName("class") val className: String,
    val des: String,
    val engName: String,
    val family: String,
    val feature: String,
    val genus: String,
    val imageURL: List<String>,
    val kingdom: String,
    val latinName: String,
    val name: String,
    val order: String,
    val phylum: String,
    val plantID: Int,
    val species: String,
)

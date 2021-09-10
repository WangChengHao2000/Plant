package com.android.flowerart.logic.bean

data class PlantType(
    val statusCode: String,
    val desc: String,
    val result: TypeResult
)

data class TypeResult(
    val plantList: List<PlantList>,
    val totalCount: Int
)

data class PlantList(
    val area: String,
    val coverURL: String,
    val engName: String,
    val name: String,
    val plantID: Int
)

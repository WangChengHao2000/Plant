package com.android.flowerart.logic.bean

data class Results(val result: List<Flower>, val log_id: Double)

data class Flower(val score: Double, val name: String)
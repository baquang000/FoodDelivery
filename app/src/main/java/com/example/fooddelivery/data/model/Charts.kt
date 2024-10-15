package com.example.fooddelivery.data.model

data class ChartCount(
    val labels: List<String>,
    val series: List<Int>
)

data class ChartOrder(
    val labels: List<Int>,
    val series: List<Int>
)


package com.example.guessthenumber.model

data class RequestParam(
    val apiKey: String,
    val n: Int,
    val min: Int,
    val max: Int,
    val replacement: Boolean
)
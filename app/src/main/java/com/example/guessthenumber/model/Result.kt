package com.example.guessthenumber.model

import java.time.LocalDateTime

data class Result(
    val random: RandomInt,
    val bitsUsed: Int,
    val bitsLeft: Int,
    val requestsLeft: Int,
    val advisoryDelay: Int
)
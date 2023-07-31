package com.example.guessthenumber.model

data class RandomOrgRequest(
    val jsonrpc: String,
    val method: String,
    val params: RequestParam,
    val id: Int,
)
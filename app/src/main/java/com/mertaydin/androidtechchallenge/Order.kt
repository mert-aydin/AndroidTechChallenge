package com.mertaydin.androidtechchallenge

data class Order(
    val date: String,
    val month: String,
    val marketName: String,
    val orderName: String,
    val productPrice: Double,
    /*val productStateIcon: Drawable,*/
    val productState: String,
    val productDetail: ProductDetail
)
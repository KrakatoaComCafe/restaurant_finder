package com.krakatoa.restaurantfinder.domain

data class Restaurant(
    val name: String,
    val customerRating: Int,
    val distance: Int,
    val price: Int,
    val cuisine: String
)

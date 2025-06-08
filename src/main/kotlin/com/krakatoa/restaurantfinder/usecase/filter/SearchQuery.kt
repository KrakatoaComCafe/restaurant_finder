package com.krakatoa.restaurantfinder.usecase.filter

data class SearchQuery(
    val name: String? = null,
    val rating: Int? = null,
    val distance: Int? = null,
    val price: Int? = null,
    val cuisine: String? = null,
)

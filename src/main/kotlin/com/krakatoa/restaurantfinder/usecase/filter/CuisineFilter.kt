package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant

class CuisineFilter : RestaurantFilter {
    override fun execute(
        restaurantList: List<Restaurant>,
        query: SearchQuery
    ): List<Restaurant> {
        if (query.cuisine.isNullOrBlank()) return restaurantList
        return restaurantList.filter { it.cuisine.contains(query.cuisine, true) }
    }
}
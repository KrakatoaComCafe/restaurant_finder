package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant

class NameFilter : RestaurantFilter {
    override fun execute(
        restaurantList: List<Restaurant>,
        query: SearchQuery
    ): List<Restaurant> {
        if (query.name.isNullOrBlank()) return restaurantList
        return restaurantList.filter { it.name.contains(query.name, true) }
    }
}
package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant

class DistanceFilter : RestaurantFilter {
    override fun execute(
        restaurantList: List<Restaurant>,
        query: SearchQuery
    ): List<Restaurant> {
        if (null == query.distance) return restaurantList
        return restaurantList.filter { it.distance <= query.distance }
    }
}
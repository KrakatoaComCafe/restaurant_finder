package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant

class PriceFilter : RestaurantFilter {
    override fun execute(
        restaurantList: List<Restaurant>,
        query: SearchQuery
    ): List<Restaurant> {
        if (null == query.price) return restaurantList
        return restaurantList.filter { it.price <= query.price }
    }
}
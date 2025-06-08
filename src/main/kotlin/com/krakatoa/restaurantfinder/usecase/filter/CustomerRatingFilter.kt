package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant

class CustomerRatingFilter : RestaurantFilter {
    override fun execute(
        restaurantList: List<Restaurant>,
        query: SearchQuery
    ): List<Restaurant> {
        if (null == query.rating) return restaurantList
        return restaurantList.filter { it.customerRating >= query.rating }
    }
}
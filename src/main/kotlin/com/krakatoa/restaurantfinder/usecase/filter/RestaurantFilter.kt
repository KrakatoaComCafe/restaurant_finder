package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant

interface RestaurantFilter {
    fun execute(restaurantList: List<Restaurant>, query: SearchQuery): List<Restaurant>
}
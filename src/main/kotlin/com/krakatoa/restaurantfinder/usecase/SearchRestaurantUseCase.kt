package com.krakatoa.restaurantfinder.usecase

import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway

class SearchRestaurantUseCase(
    private val restaurantGateway: RestaurantGateway
) {
    fun invoke(): List<Restaurant> {
        return restaurantGateway.getAll()
    }
}
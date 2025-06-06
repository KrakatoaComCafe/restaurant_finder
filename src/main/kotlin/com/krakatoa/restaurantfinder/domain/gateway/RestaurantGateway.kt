package com.krakatoa.restaurantfinder.domain.gateway

import com.krakatoa.restaurantfinder.domain.Restaurant

interface RestaurantGateway {
    fun getAll(): List<Restaurant>
}
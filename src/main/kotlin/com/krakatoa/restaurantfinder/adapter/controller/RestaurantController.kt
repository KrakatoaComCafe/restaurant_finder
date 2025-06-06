package com.krakatoa.restaurantfinder.adapter.controller

import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantGateway: RestaurantGateway,
) {
    @GetMapping()
    fun getAll(): List<Restaurant> {
        return restaurantGateway.getAll()
    }
}
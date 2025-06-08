package com.krakatoa.restaurantfinder.adapter.controller

import com.krakatoa.restaurantfinder.adapter.validate.ValidateRestaurantInput
import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import com.krakatoa.restaurantfinder.usecase.SearchRestaurantUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantGateway: RestaurantGateway,
    private val searchRestaurantUseCase: SearchRestaurantUseCase,
    private val validateInput: ValidateRestaurantInput
) {
    @GetMapping
    fun getAll(): List<Restaurant> {
        return restaurantGateway.getAll()
    }

    @GetMapping("/search")
    fun searchRestaurant(
        @RequestParam name: String?,
        @RequestParam customerRating: String?,
        @RequestParam distance: String?,
        @RequestParam price: String?,
        @RequestParam cuisine: String?
    ): ResponseEntity<Any> {
        val result = validateInput.execute(customerRating, distance, price)
        if (result.isFailure) {
            val errorMessage = ErrorMessage(result.exceptionOrNull()!!.message!!)
            return ResponseEntity.badRequest().body(errorMessage)
        }

        val restaurantsFound =  searchRestaurantUseCase.invoke(
            name = name,
            customerRating = customerRating?.trim()?.toInt(),
            distance = distance?.trim()?.toInt(),
            price = price?.trim()?.toInt(),
            cuisine = cuisine
        )

        if (restaurantsFound.isEmpty()) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(restaurantsFound)
    }
}

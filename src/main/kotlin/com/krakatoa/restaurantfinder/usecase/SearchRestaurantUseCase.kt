package com.krakatoa.restaurantfinder.usecase

import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import com.krakatoa.restaurantfinder.usecase.filter.RestaurantFilter
import com.krakatoa.restaurantfinder.usecase.filter.SearchQuery

class SearchRestaurantUseCase(
    private val restaurantGateway: RestaurantGateway,
    private val filters: List<RestaurantFilter>
) {
    fun invoke(
        name: String? = null,
        customerRating: Int? = null,
        distance: Int? = null,
        price: Int? = null,
        cuisine: String? = null
    ): List<Restaurant> {
        val query = SearchQuery(name, customerRating, distance, price, cuisine)
        return restaurantGateway.getAll()
            .applyFilters(query)
            .sortedWith(
                compareBy<Restaurant> { it.distance }
                    .thenByDescending { it.customerRating }
                    .thenBy { it.price }
            )
            .take(RESULT_LIMIT)
    }

    private fun List<Restaurant>.applyFilters(
        query: SearchQuery
    ): List<Restaurant> {
        var restaurants = this
        for (filter in filters) {
            restaurants = filter.execute(restaurants, query)
        }
        return restaurants
    }

    companion object {
        private const val RESULT_LIMIT = 5
    }
}
package com.krakatoa.restaurantfinder.infra.repository

import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.io.File

@Component
class RestaurantRepositoryMemoryImpl: RestaurantGateway {
    private val restaurantList = mutableListOf<Restaurant>()

    override fun getAll(): List<Restaurant> {
        return restaurantList
    }

    @PostConstruct
    fun loadFromCsv() {
        val cuisinesList = loadCuisines("src/main/resources/csv/cuisines.csv")
        val lineList = File("src/main/resources/csv/restaurants.csv")
            .readLines()
            .drop(HEADER_POSITION)

        for (line in lineList) {
            val parts = line.split(CSV_DELIMITER)
            val cuisineId = parts[CUISINE_POS].toInt()
            val cuisine = cuisinesList[cuisineId] ?: "Not Found"
            Restaurant(
                name = parts[NAME_POS],
                customerRating = parts[CUSTOMER_RATING_POS].toInt(),
                distance = parts[DISTANCE_POS].toInt(),
                price = parts[PRICE_POS].toInt(),
                cuisine = cuisine
            ).let { restaurantList.add(it) }
        }
    }

    private fun loadCuisines(path: String): Map<Int, String> {
        return File(path)
            .readLines()
            .drop(HEADER_POSITION)
            .associate {
                val parts = it.split(CSV_DELIMITER)
                parts[CUISINE_ID].toInt() to parts[CUISINE_NAME]
            }
    }

    companion object {
        private const val HEADER_POSITION = 1
        private const val CSV_DELIMITER = ","
        private const val CUISINE_ID = 0
        private const val CUISINE_NAME = 1

        private const val NAME_POS = 0
        private const val CUSTOMER_RATING_POS = 1
        private const val DISTANCE_POS = 2
        private const val PRICE_POS = 3
        private const val CUISINE_POS = 4
    }
}
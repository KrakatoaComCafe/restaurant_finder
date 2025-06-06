package com.krakatoa.restaurantfinder.infra.repository

import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import com.krakatoa.restaurantfinder.infra.file.FileReader
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class RestaurantRepositoryMemoryImpl(
    private val fileReader: FileReader
) : RestaurantGateway {
    private val restaurantList = mutableListOf<Restaurant>()

    override fun getAll(): List<Restaurant> {
        return restaurantList
    }

    @PostConstruct
    fun loadFromCsv() {
        val cuisinesList = fileReader.loadCsvAsMap("csv/cuisines.csv")
        val lineList = fileReader.loadCsv("csv/restaurants.csv")

        for (line in lineList) {
            val cuisineId = line[CUISINE_POS].toInt()
            val cuisine = cuisinesList[cuisineId] ?: "Not Found"
            Restaurant(
                name = line[NAME_POS],
                customerRating = line[CUSTOMER_RATING_POS].toInt(),
                distance = line[DISTANCE_POS].toInt(),
                price = line[PRICE_POS].toInt(),
                cuisine = cuisine
            ).let { restaurantList.add(it) }
        }
    }

    companion object {
        private const val NAME_POS = 0
        private const val CUSTOMER_RATING_POS = 1
        private const val DISTANCE_POS = 2
        private const val PRICE_POS = 3
        private const val CUISINE_POS = 4
    }
}
package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource

class CuisineFilterTest {

    private val filter = CuisineFilter()

    @Nested
    inner class SkipFilterTest {
        @ParameterizedTest
        @NullAndEmptySource
        fun `should return unchanged list When cuisine input is not present and list is empty`(cuisineInput: String?) {
            val query = SearchQuery(
                cuisine = cuisineInput
            )
            val restaurants = emptyList<Restaurant>()

            val result = filter.execute(restaurants, query)

            assertEquals(emptyList<Restaurant>(), result)
        }

        @ParameterizedTest
        @NullAndEmptySource
        fun `should return unchanged list When cuisine input is not present and list has elements`(cuisineInput: String?) {
            val query = SearchQuery(
                cuisine = cuisineInput
            )
            val restaurants = listOf(
                Restaurant(
                    name = "name 1",
                    customerRating = 1,
                    distance = 2,
                    price = 3,
                    cuisine = "cuisine 1"
                ),
                Restaurant(
                    name = "name 2",
                    customerRating = 4,
                    distance = 5,
                    price = 7,
                    cuisine = "cuisine 2"
                ),
                Restaurant(
                    name = "name 3",
                    customerRating = 8,
                    distance = 9,
                    price = 91,
                    cuisine = "cuisine 3"
                )
            )
            val expectedList = restaurants.toList()

            val result = filter.execute(restaurants, query)

            assertEquals(expectedList, result)
        }
    }

    @Test
    fun `should return empty list - When cuisine is not found`() {
        val query = SearchQuery(
            cuisine = "test cuisine"
        )
        val restaurants = listOf(
            Restaurant(
                name = "name 1",
                customerRating = 1,
                distance = 2,
                price = 3,
                cuisine = "cuisine 1"
            ),
            Restaurant(
                name = "name 2",
                customerRating = 4,
                distance = 5,
                price = 7,
                cuisine = "cuisine 2"
            ),
            Restaurant(
                name = "name 3",
                customerRating = 8,
                distance = 9,
                price = 91,
                cuisine = "cuisine 3"
            )
        )

        val result = filter.execute(restaurants, query)

        assertEquals(emptyList<Restaurant>(), result)
    }

    @Test
    fun `should return 2 elements found - When cuisine exists in list`() {
        val query = SearchQuery(
            cuisine = "test cuisine"
        )
        val res1 = Restaurant(
            name = "name 1",
            customerRating = 1,
            distance = 2,
            price = 3,
            cuisine = "Test Cuisine 1"
        )
        val res2 = Restaurant(
            name = "name 2",
            customerRating = 4,
            distance = 5,
            price = 7,
            cuisine = "cuisine 2"
        )
        val res3 = Restaurant(
            name = "name 3",
            customerRating = 8,
            distance = 9,
            price = 91,
            cuisine = "tEsT cUiSiNe 3"
        )

        val restaurants = listOf(res1, res2, res3)
        val expectedList = listOf(res1, res3)

        val result = filter.execute(restaurants, query)

        assertEquals(expectedList, result)
    }
}
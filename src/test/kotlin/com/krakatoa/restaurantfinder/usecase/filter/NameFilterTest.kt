package com.krakatoa.restaurantfinder.usecase.filter

import com.krakatoa.restaurantfinder.domain.Restaurant
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource

class NameFilterTest {
    private val filter = NameFilter()

    @Nested
    inner class SkipFilterTest {
        @ParameterizedTest
        @NullAndEmptySource
        fun `should return unchanged list When name input is not present and list is empty`(nameInput: String?) {
            val query = SearchQuery(
                name = nameInput
            )
            val restaurants = emptyList<Restaurant>()

            val result = filter.execute(restaurants, query)

            assertEquals(emptyList<Restaurant>(), result)
        }

        @ParameterizedTest
        @NullAndEmptySource
        fun `should return unchanged list When name input is not present and list has elements`(nameInput: String?) {
            val query = SearchQuery(
                name = nameInput
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
    fun `should return empty list - When name input is not found`() {
        val query = SearchQuery(
            name = "test name"
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
    fun `should return 2 elements found - When name exists in list`() {
        val query = SearchQuery(
            name = "test name"
        )
        val res1 = Restaurant(
            name = "tEsT nAmE 1",
            customerRating = 1,
            distance = 2,
            price = 3,
            cuisine = "Test Cuisine 1"
        )
        val res2 = Restaurant(
            name = "Test Name 2",
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
        val expectedList = listOf(res1, res2)

        val result = filter.execute(restaurants, query)

        assertEquals(expectedList, result)
    }
}
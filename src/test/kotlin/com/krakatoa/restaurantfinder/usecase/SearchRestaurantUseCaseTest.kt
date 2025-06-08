package com.krakatoa.restaurantfinder.usecase

import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import com.krakatoa.restaurantfinder.usecase.filter.RestaurantFilter
import com.krakatoa.restaurantfinder.usecase.filter.SearchQuery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class SearchRestaurantUseCaseTest {
    private lateinit var restaurantGateway: RestaurantGateway
    private lateinit var filter1: RestaurantFilter
    private lateinit var filter2: RestaurantFilter
    private lateinit var filters: List<RestaurantFilter>
    private lateinit var useCase: SearchRestaurantUseCase

    @BeforeEach
    fun setup() {
        filter1 = mock()
        filter2 = mock()
        restaurantGateway = mock()
        filters = listOf(filter1, filter2)
        useCase = SearchRestaurantUseCase(restaurantGateway, filters)
    }

    @Test
    fun `should run all filters - When searching restaurant`() {
        val res1 = Restaurant("restaurant 1", 4, 1, 10, "cuisine 1")
        val res2 = Restaurant("restaurant 2", 3, 1, 8, "cuisine 2")
        val res3 = Restaurant("restaurant 3", 5, 2, 20, "cuisine 3")
        val res4 = Restaurant("restaurant 4", 4, 3, 10, "cuisine 4")
        val res5 = Restaurant("restaurant 5", 2, 1, 5, "cuisine 5")
        val res6 = Restaurant("restaurant 6", 5, 4, 15, "cuisine 6")
        val res7 = Restaurant("restaurant 7", 6, 5, 20, "cuisine 7")
        val res8 = Restaurant("restaurant 8", 7, 6, 25, "cuisine 8")
        val res9 = Restaurant("restaurant 9", 8, 7, 15, "cuisine 9")
        val res10 = Restaurant("restaurant 10", 9, 7, 15, "cuisine 10")
        val startingRestaurantList = listOf(
            res1,
            res2,
            res3,
            res4,
            res5,
            res6,
            res7,
            res8,
            res9,
            res10
        )
        val query = SearchQuery(price = 15, rating = 3)

        val restaurantAfterFilter1 = startingRestaurantList.filter { it.price <= 15 }
        val restaurantAfterFilter2 = restaurantAfterFilter1.filter { it.customerRating >= 3 }

        Mockito.`when`(restaurantGateway.getAll())
            .thenReturn(startingRestaurantList)
        Mockito.`when`(filter1.execute(startingRestaurantList, query))
            .thenReturn(restaurantAfterFilter1)
        Mockito.`when`(filter2.execute(restaurantAfterFilter1, query))
            .thenReturn(restaurantAfterFilter2)

        val result = useCase.invoke(price = 15, customerRating = 3)


        assertEquals(5, result.size)
        assertAll(
            { assertEquals(res1, result[0]) },
            { assertEquals(res2, result[1]) },
            { assertEquals(res4, result[2]) },
            { assertEquals(res6, result[3]) },
            { assertEquals(res10, result[4]) },
        )
    }

    @Test
    fun `should run all filters and return 3 elements - When searching restaurant and filtering almost all restaurants`() {
        val res1 = Restaurant("restaurant 1", 4, 1, 10, "cuisine 1")
        val res2 = Restaurant("restaurant 2", 3, 1, 8, "cuisine 2")
        val res3 = Restaurant("restaurant 3", 5, 2, 20, "cuisine 3")
        val res4 = Restaurant("restaurant 4", 4, 3, 10, "cuisine 4")
        val res5 = Restaurant("restaurant 5", 2, 1, 5, "cuisine 5")
        val res6 = Restaurant("restaurant 6", 5, 4, 15, "cuisine 6")
        val res7 = Restaurant("restaurant 7", 6, 5, 20, "cuisine 7")
        val res8 = Restaurant("restaurant 8", 7, 6, 25, "cuisine 8")
        val res9 = Restaurant("restaurant 9", 8, 7, 15, "cuisine 9")
        val res10 = Restaurant("restaurant 10", 9, 7, 15, "cuisine 10")
        val startingRestaurantList = listOf(
            res1,
            res2,
            res3,
            res4,
            res5,
            res6,
            res7,
            res8,
            res9,
            res10
        )
        val query = SearchQuery(rating = 6, price = 20)

        val restaurantAfterFilter1 = startingRestaurantList.filter { it.price <= 20 }
        val restaurantAfterFilter2 = restaurantAfterFilter1.filter { it.customerRating >= 6 }

        Mockito.`when`(restaurantGateway.getAll())
            .thenReturn(startingRestaurantList)
        Mockito.`when`(filter1.execute(startingRestaurantList, query))
            .thenReturn(restaurantAfterFilter1)
        Mockito.`when`(filter2.execute(restaurantAfterFilter1, query))
            .thenReturn(restaurantAfterFilter2)

        val result = useCase.invoke(price = 20, customerRating = 6)


        assertEquals(3, result.size)
        assertAll(
            { assertEquals(res7, result[0]) },
            { assertEquals(res10, result[1]) },
            { assertEquals(res9, result[2]) },
        )
    }
}
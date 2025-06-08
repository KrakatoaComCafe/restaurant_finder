package com.krakatoa.restaurantfinder.adapter.controller

import com.krakatoa.restaurantfinder.adapter.validate.ValidateRestaurantInput
import com.krakatoa.restaurantfinder.domain.Restaurant
import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import com.krakatoa.restaurantfinder.usecase.SearchRestaurantUseCase
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@WebMvcTest(RestaurantController::class)
class RestaurantControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var restaurantGateway: RestaurantGateway

    @MockitoBean
    lateinit var searchRestaurantUseCase: SearchRestaurantUseCase

    @MockitoBean
    lateinit var validateInput: ValidateRestaurantInput

    @Nested
    inner class SearchGet {
        @Test
        fun `should return empty - When no restaurant is found`() {
            `when`(validateInput.execute("5", null, "8"))
                .thenReturn(Result.success(Unit))
            `when`(searchRestaurantUseCase.invoke(price = 8, customerRating = 5))
                .thenReturn(emptyList())

            mockMvc.get("/restaurants/search") {
                param("price", "8")
                param("customerRating", "5")
            }.andExpect {
                status { isNotFound() }
            }
        }

        @Test
        fun `should return error - When no restaurant is found`() {
            `when`(validateInput.execute("5", null, "qwe"))
                .thenReturn(Result.failure(InvalidPropertiesFormatException("Price must be digit")))

            mockMvc.get("/restaurants/search") {
                param("price", "qwe")
                param("customerRating", "5")
            }.andExpect {
                status { isBadRequest() }
                jsonPath("$.message") { value("Price must be digit") }
                content { contentType("application/json") }
            }

            verify(searchRestaurantUseCase, never())
                .invoke(any(), any(), any(), any(), any())
        }

        @Test
        fun `should return restaurant list - When searching restaurant`() {
            val res1 = Restaurant("name 1", 5, 5, 3, "cuisine 1")
            val res2 = Restaurant("name 2", 5, 2, 2, "cuisine 4")
            val res3 = Restaurant("name 3", 5, 6, 1, "cuisine 2")
            val restaurants = listOf(res1, res2, res3)

            `when`(validateInput.execute("5", null, "3"))
                .thenReturn(Result.success(Unit))
            `when`(searchRestaurantUseCase.invoke(customerRating = 5, price = 3))
                .thenReturn(restaurants)

            mockMvc.get("/restaurants/search") {
                param("price", "3")
                param("customerRating", "5")
            }.andExpect {
                status { isOk() }
                jsonPath("$.[0]") { res1 }
                jsonPath("$.[1]") { res2 }
                jsonPath("$.[2]") { res3 }
                content { contentType("application/json") }
            }
        }
    }
}
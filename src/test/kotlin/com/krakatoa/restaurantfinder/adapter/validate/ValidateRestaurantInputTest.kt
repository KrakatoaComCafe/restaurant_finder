package com.krakatoa.restaurantfinder.adapter.validate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class ValidateRestaurantInputTest {

    val validate = ValidateRestaurantInput()

    @Nested
    inner class FailureTest {
        @Test
        fun `should return failure - When customer rating is not digit only`() {
            val customerRating = "qwe"
            val distance = "3"
            val price = "10"
            val expectedResult =
                Result.failure<Unit>(InvalidPropertiesFormatException("Customer Rating must contain only digits"))

            val result = validate.execute(customerRating, distance, price)

            assertEquals(expectedResult.exceptionOrNull()!!.message, result.exceptionOrNull()!!.message)
        }

        @Test
        fun `should return failure - When distance is not digit only`() {
            val customerRating = "3"
            val distance = "3a"
            val price = "10"
            val expectedResult =
                Result.failure<Unit>(InvalidPropertiesFormatException("Distance must contain only digits"))

            val result = validate.execute(customerRating, distance, price)

            assertEquals(expectedResult.exceptionOrNull()!!.message, result.exceptionOrNull()!!.message)
        }

        @Test
        fun `should return failure - When price is not digit only`() {
            val customerRating = "3"
            val distance = "3"
            val price = "qwe10"
            val expectedResult =
                Result.failure<Unit>(InvalidPropertiesFormatException("Price must contain only digits"))

            val result = validate.execute(customerRating, distance, price)

            assertEquals(expectedResult.exceptionOrNull()!!.message, result.exceptionOrNull()!!.message)
        }
    }

    @Test
    fun `should return success - When all is null`() {
        val customerRating = null
        val distance = null
        val price = null
        val expectedResult =
            Result.success(Unit)

        val result = validate.execute(customerRating, distance, price)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `should return success - When all is digit`() {
        val customerRating = "123"
        val distance = "345"
        val price = "567"
        val expectedResult =
            Result.success(Unit)

        val result = validate.execute(customerRating, distance, price)

        assertEquals(expectedResult, result)
    }
}
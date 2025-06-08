package com.krakatoa.restaurantfinder.adapter.validate

import org.springframework.stereotype.Component
import java.util.*

@Component
class ValidateRestaurantInput {
    fun execute(
        customerRating: String? = null,
        distance: String? = null,
        price: String? = null,
    ): Result<Unit> {
        if (null != customerRating) {
            val ratingIsDigitOnly = customerRating.all { it.isDigit() }

            if (!ratingIsDigitOnly) {
                return Result.failure(InvalidPropertiesFormatException("Customer Rating must contain only digits"))
            }
        }

        if (null != distance) {
            val distanceIsDigitOnly = distance.all { it.isDigit() }
            if (!distanceIsDigitOnly) {
                return Result.failure(InvalidPropertiesFormatException("Distance must contain only digits"))
            }
        }

        if (null != price) {
            val priceIsDigitOnly = price.all { it.isDigit() }
            if (!priceIsDigitOnly) {
                return Result.failure(InvalidPropertiesFormatException("Price must contain only digits"))
            }
        }

        return Result.success(Unit)
    }
}
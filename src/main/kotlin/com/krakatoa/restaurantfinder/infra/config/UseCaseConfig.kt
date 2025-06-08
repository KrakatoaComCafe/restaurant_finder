package com.krakatoa.restaurantfinder.infra.config

import com.krakatoa.restaurantfinder.domain.gateway.RestaurantGateway
import com.krakatoa.restaurantfinder.usecase.SearchRestaurantUseCase
import com.krakatoa.restaurantfinder.usecase.filter.CuisineFilter
import com.krakatoa.restaurantfinder.usecase.filter.CustomerRatingFilter
import com.krakatoa.restaurantfinder.usecase.filter.DistanceFilter
import com.krakatoa.restaurantfinder.usecase.filter.NameFilter
import com.krakatoa.restaurantfinder.usecase.filter.PriceFilter
import com.krakatoa.restaurantfinder.usecase.filter.RestaurantFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {
    @Bean
    fun nameFilter(): RestaurantFilter {
        return NameFilter()
    }

    @Bean
    fun customerRating(): RestaurantFilter {
        return CustomerRatingFilter()
    }


    @Bean
    fun distanceFilter(): RestaurantFilter {
        return DistanceFilter()
    }


    @Bean
    fun priceFilter(): RestaurantFilter {
        return PriceFilter()
    }


    @Bean
    fun cuisineFilter(): RestaurantFilter {
        return CuisineFilter()
    }


    @Bean
    fun searchRestaurantUseCase(repositoryGateway: RestaurantGateway, filters: List<RestaurantFilter>): SearchRestaurantUseCase {
        return SearchRestaurantUseCase(repositoryGateway, filters)
    }
}

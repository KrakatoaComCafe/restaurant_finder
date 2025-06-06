package com.krakatoa.restaurantfinder.infra.file

import org.springframework.stereotype.Component
import java.io.FileNotFoundException

@Component
class FileReader {
    fun loadCsv(path: String): List<List<String>> {
        val inputStream = javaClass.classLoader.getResourceAsStream(path)
            ?: throw FileNotFoundException("Path not found [path:$path]")

        return inputStream.bufferedReader()
            .readLines()
            .drop(HEADER_POSITION)
            .map { it.split(CSV_DELIMITER) }
    }

    fun loadCsvAsMap(path: String): Map<Int, String> {
        return loadCsv(path)
            .associate { it[KEY].toInt() to it[VALUE] }
    }

    companion object {
        private const val HEADER_POSITION = 1
        private const val CSV_DELIMITER = ","

        private const val KEY = 0
        private const val VALUE = 1
    }
}
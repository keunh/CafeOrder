package com.cafe.cafeorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CafeOrderApplication

fun main(args: Array<String>) {
    runApplication<CafeOrderApplication>(*args)
}

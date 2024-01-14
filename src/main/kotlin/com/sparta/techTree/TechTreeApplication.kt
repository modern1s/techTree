package com.sparta.techTree

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class TechTreeApplication

fun main(args: Array<String>) {
    runApplication<TechTreeApplication>(*args)
}

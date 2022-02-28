package com.github.hugovallada.kafka.producer.controller

import com.github.hugovallada.kafka.producer.producers.CarProducer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/cars")
class CarController(private val producer: CarProducer) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCar(@RequestBody carDTO: CarDTO): CarDTO{
        CarDTO(
            UUID.randomUUID().toString(),
            carDTO.model,
            carDTO.color
        ).run {
            producer.send(this)
            return this
        }
    }

}
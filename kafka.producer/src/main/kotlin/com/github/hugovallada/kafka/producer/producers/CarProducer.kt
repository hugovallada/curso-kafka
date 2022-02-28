package com.github.hugovallada.kafka.producer.producers

import com.github.hugovallada.kafka.producer.controller.CarDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CarProducer(
    val template: KafkaTemplate<String, CarDTO>
) {

    @Value("\${topic.name}")
    private lateinit var topic: String

    private val log: Logger = Logger.getLogger(javaClass.name)

    fun send(carDTO: CarDTO) {
        template.send(topic, carDTO).addCallback({
            log.info("Message Send!")
        }) {
            log.info(it.message)
        }

    }


}
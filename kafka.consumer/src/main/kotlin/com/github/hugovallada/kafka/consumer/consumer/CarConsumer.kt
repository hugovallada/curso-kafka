package com.github.hugovallada.kafka.consumer.consumer

import com.github.hugovallada.kafka.consumer.dto.CarDTO
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CarConsumer {
    private val logger = LoggerFactory.getLogger(CarConsumer::class.java)

    @KafkaListener(
        topics = ["\${topic.name}"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "carKafkaListenerContainerFactory"
    )
    fun listenTopicCar(record: ConsumerRecord<String, CarDTO>) {
        logger.info("Received Message Partition: ${record.partition()}")
        logger.info("Message: ${record.value()}")
        println("Success")
    }

}
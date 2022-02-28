package com.github.hugovallada.kafka.producer.config

import com.github.hugovallada.kafka.producer.controller.CarDTO
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.*

@Configuration
class KafkaConfig {

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapAddress: String

    @Value("\${topic.name}")
    private lateinit var topic: String

    @Bean
    fun createTopic() = NewTopic(topic, 3, 1)

    @Bean
    fun carProducerFactory(): ProducerFactory<String, CarDTO> {
        val configProps = hashMapOf<String, Any>().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer::class.java)
        }
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun carKafkaTemplate() = KafkaTemplate(carProducerFactory())

}
package com.github.hugovallada.kafka.consumer.config

import com.github.hugovallada.kafka.consumer.dto.CarDTO
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class ConsumerKafka {
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapAddress: String

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var groupId: String

    @Bean
    fun carConsumerFactory(): ConsumerFactory<String, CarDTO> {
        val props = hashMapOf<String, Any>().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress)
            put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer::class.java)
        }
        return DefaultKafkaConsumerFactory(props, StringDeserializer(), JsonDeserializer(CarDTO::class.java, false))
    }

    @Bean
    fun carKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, CarDTO> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, CarDTO>()
        factory.consumerFactory = carConsumerFactory()
        return factory
    }

}